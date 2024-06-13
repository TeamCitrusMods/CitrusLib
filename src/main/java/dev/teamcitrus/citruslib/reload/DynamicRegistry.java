package dev.teamcitrus.citruslib.reload;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.teamcitrus.citruslib.CitrusLib;
import dev.teamcitrus.citruslib.codec.CodecMap;
import dev.teamcitrus.citruslib.codec.CodecProvider;
import dev.teamcitrus.citruslib.util.JsonUtils;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * Taken from Placebo with consent of Shadows
 * @author Shadows of Fire
 * @link <a href="https://github.com/Shadows-of-Fire/Placebo/tree/1.20.4">...</a>
 */
public abstract class DynamicRegistry<R extends CodecProvider<? super R>> extends SimpleJsonResourceReloadListener {
    protected final Logger logger;
    protected final String path;
    protected final boolean synced;
    protected final boolean subtypes;
    protected final CodecMap<R> codecs;
    protected final Codec<DynamicHolder<R>> holderCodec;

    protected BiMap<ResourceLocation, R> registry = ImmutableBiMap.of();
    private final Map<ResourceLocation, R> staged = new HashMap<>();
    private final Map<ResourceLocation, DynamicHolder<? extends R>> holders = new ConcurrentHashMap<>();
    private final Set<RegistryCallback<R>> callbacks = new HashSet<>();

    public DynamicRegistry(Logger logger, String path, boolean synced, boolean subtypes) {
        super(new GsonBuilder().setLenient().create(), path);
        this.logger = logger;
        this.path = path;
        this.synced = synced;
        this.subtypes = subtypes;
        this.codecs = new CodecMap<>(path);
        this.registerBuiltinCodecs();
        if (this.codecs.isEmpty()) throw new RuntimeException("Attempted to create a dynamic registry for " + path + " with no built-in codecs!");
        this.holderCodec = ResourceLocation.CODEC.xmap(this::holder, DynamicHolder::getId);
    }

    @Override
    protected final void apply(Map<ResourceLocation, JsonElement> objects, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        this.beginReload();
        var ops = ConditionalOps.create(RegistryOps.create(JsonOps.INSTANCE, this.registryAccess), this.conditionContext);
        objects.forEach((key, ele) -> {
            try {
                if (JsonUtils.checkAndLogEmpty(ele, key, this.path, this.logger) && JsonUtils.checkConditions(ele, key, this.path, this.logger, ops)) {
                    JsonObject obj = ele.getAsJsonObject();
                    R deserialized = this.codecs.decode(JsonOps.INSTANCE, obj).getOrThrow(false, this::logCodecError).getFirst();
                    Preconditions.checkNotNull(deserialized.getCodec(), "A " + this.path + " with id " + key + " is not declaring a codec.");
                    Preconditions.checkNotNull(this.codecs.getKey(deserialized.getCodec()), "A " + this.path + " with id " + key + " is declaring an unregistered codec.");
                    this.register(key, deserialized);
                }
            }
            catch (Exception e) {
                this.logger.error("Failed parsing {} file {}.", this.path, key);
                this.logger.error("Underlying Exception: ", e);
            }
        });
        this.onReload();
    }

    protected abstract void registerBuiltinCodecs();

    protected void beginReload() {
        this.callbacks.forEach(l -> l.beginReload(this));
        this.registry = HashBiMap.create();
        this.holders.values().forEach(DynamicHolder::unbind);
    }

    protected void onReload() {
        this.registry = ImmutableBiMap.copyOf(this.registry);
        this.logger.info("Registered {} {}.", this.registry.size(), this.path);
        this.callbacks.forEach(l -> l.onReload(this));
        this.holders.values().forEach(DynamicHolder::bind);
    }

    public Set<ResourceLocation> getKeys() {
        return this.registry.keySet();
    }

    public Collection<R> getValues() {
        return this.registry.values();
    }

    @Nullable
    public R getValue(ResourceLocation key) {
        return this.registry.get(key);
    }

    @Nullable
    public ResourceLocation getKey(R value) {
        return this.registry.inverse().get(value);
    }

    public R getOrDefault(ResourceLocation key, R defValue) {
        return this.registry.getOrDefault(key, defValue);
    }

    public void registerToBus() {
        if (this.synced) SyncManagement.registerForSync(this);
        NeoForge.EVENT_BUS.addListener(this::addReloader);
    }

    @SuppressWarnings("unchecked")
    public <T extends R> DynamicHolder<T> holder(ResourceLocation id) {
        return (DynamicHolder<T>) this.holders.computeIfAbsent(id, k -> new DynamicHolder<>(this, k));
    }

    public <T extends R> DynamicHolder<T> holder(T t) {
        ResourceLocation key = this.getKey(t);
        return this.holder(key == null ? DynamicHolder.EMPTY : key);
    }

    public DynamicHolder<R> emptyHolder() {
        return this.holder(DynamicHolder.EMPTY);
    }

    public Codec<DynamicHolder<R>> holderCodec() {
        return this.holderCodec;
    }

    public final void registerCodec(ResourceLocation key, Codec<? extends R> codec) {
        if (!this.subtypes) throw new UnsupportedOperationException("Attempted to call registerCodec on a registry which does not support subtypes.");
        this.codecs.register(key, codec);
    }

    protected final void registerDefaultCodec(ResourceLocation key, Codec<? extends R> codec) {
        if (this.codecs.getDefaultCodec() != null) throw new UnsupportedOperationException("Attempted to register a second " + this.path + " default codec with key " + key + " but subtypes are not supported!");
        this.codecs.register(key, codec);
        this.codecs.setDefaultCodec(codec);
    }

    public final boolean addCallback(RegistryCallback<R> callback) {
        return this.callbacks.add(callback);
    }

    public final boolean removeCallback(RegistryCallback<R> callback) {
        return this.callbacks.remove(callback);
    }

    public final String getPath() {
        return this.path;
    }

    protected final void register(ResourceLocation key, R value) {
        if (this.registry.containsKey(key)) throw new UnsupportedOperationException("Attempted to register a " + this.path + " with a duplicate registry ID! Key: " + key);
        this.validateItem(key, value);
        this.registry.put(key, value);
        this.holders.computeIfAbsent(key, k -> new DynamicHolder<>(this, k));
    }

    protected void validateItem(ResourceLocation key, R value) {}

    private void addReloader(AddReloadListenerEvent e) {
        e.addListener(this);
    }

    private void pushStagedToLive() {
        this.beginReload();
        this.staged.forEach(this::register);
        this.onReload();
    }

    private void logCodecError(String msg) {
        CitrusLib.LOGGER.error("Codec failure for type {}, message: {}", this.path, msg);
    }

    private void sync(OnDatapackSyncEvent e) {
        ServerPlayer player = e.getPlayer();
        PacketDistributor.PacketTarget target = player == null ? PacketDistributor.ALL.noArg() : PacketDistributor.PLAYER.with(player);

        target.send(new ReloadListenerPackets.Start(this.path));
        this.registry.forEach((k, v) -> {
            target.send(new ReloadListenerPackets.Content<>(this.path, k, v));
        });
        target.send(new ReloadListenerPackets.End(this.path));
    }

    @ApiStatus.Internal
    static class SyncManagement {

        private static final Map<String, DynamicRegistry<?>> SYNC_REGISTRY = new LinkedHashMap<>();

        static void registerForSync(DynamicRegistry<?> listener) {
            if (!listener.synced) throw new UnsupportedOperationException("Attempted to register the non-synced JSON Reload Listener " + listener.path + " as a synced listener!");
            synchronized (SYNC_REGISTRY) {
                if (SYNC_REGISTRY.containsKey(listener.path)) throw new UnsupportedOperationException("Attempted to register the JSON Reload Listener for syncing " + listener.path + " but one already exists!");
                if (SYNC_REGISTRY.isEmpty()) NeoForge.EVENT_BUS.addListener(SyncManagement::syncAll);
                SYNC_REGISTRY.put(listener.path, listener);
            }
        }

        static void initSync(String path) {
            ifPresent(path, registry -> registry.staged.clear());
            CitrusLib.LOGGER.info("Starting sync for {}", path);
        }

        @SuppressWarnings("unchecked")
        static <V extends CodecProvider<? super V>> void writeItem(String path, V value, FriendlyByteBuf buf) {
            ifPresent(path, registry -> {
                Codec<V> c = (Codec<V>) registry.codecs;
                buf.writeNbt(c.encodeStart(NbtOps.INSTANCE, value).getOrThrow(false, registry::logCodecError));
            });
        }

        @SuppressWarnings("unchecked")
        static <V> V readItem(String path, FriendlyByteBuf buf) {
            var registry = SYNC_REGISTRY.get(path);
            if (registry == null) throw new RuntimeException("Received sync packet for unknown registry!");
            Codec<V> c = (Codec<V>) registry.codecs;
            return c.decode(NbtOps.INSTANCE, buf.readNbt()).getOrThrow(false, registry::logCodecError).getFirst();
        }

        @SuppressWarnings("unchecked")
        static <V> void acceptItem(String path, ResourceLocation key, V value) {
            ifPresent(path, registry -> ((Map<ResourceLocation, V>) registry.staged).put(key, value));
        }

        static void endSync(String path) {
            if (ServerLifecycleHooks.getCurrentServer() != null) return; // Do not propgate received changed on the host of a singleplayer world, as they may not be the full data.
            ifPresent(path, DynamicRegistry::pushStagedToLive);
        }

        private static void ifPresent(String path, Consumer<DynamicRegistry<?>> consumer) {
            DynamicRegistry<?> value = SYNC_REGISTRY.get(path);
            if (value != null) consumer.accept(value);
        }

        private static void syncAll(OnDatapackSyncEvent e) {
            SYNC_REGISTRY.values().forEach(r -> r.sync(e));
        }
    }

}
