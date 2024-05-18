package dev.teamcitrus.citruslib.network;

import com.google.common.base.Preconditions;
import dev.teamcitrus.citruslib.CitrusLib;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Taken from Placebo with consent of Shadows
 * @author Shadows of Fire
 * @link https://github.com/Shadows-of-Fire/Placebo/tree/1.20.4
 */
public class PayloadHelper {
    private static final Map<ResourceLocation, PayloadProvider<?, ?>> ALL_PROVIDERS = new HashMap<>();
    private static boolean locked = false;

    @SuppressWarnings("unchecked")
    public static <T extends CustomPacketPayload, C extends IPayloadContext> void registerPayload(PayloadProvider<T, C> prov) {
        Preconditions.checkNotNull(prov);
        synchronized (ALL_PROVIDERS) {
            if (locked) throw new UnsupportedOperationException("Attempted to register a payload provider after registration has finished.");
            if (ALL_PROVIDERS.containsKey(prov.id())) throw new UnsupportedOperationException("Attempted to register payload provider with duplicate ID: " + prov.id());
            ALL_PROVIDERS.put(prov.id(), prov);
        }
    }

    public static void handle(Runnable r, IPayloadContext ctx) {
        ctx.workHandler().execute(r);
    }

    @SubscribeEvent
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void registerProviders(RegisterPayloadHandlerEvent event) {
        synchronized (ALL_PROVIDERS) {
            for (PayloadProvider prov : ALL_PROVIDERS.values()) {
                IPayloadRegistrar reg = event.registrar(prov.id().getNamespace());

                if (prov.isOptional()) {
                    reg = reg.optional();
                }

                reg = reg.versioned(prov.getVersion()); // Using a rawtype also rawtypes the Optional

                reg.common(prov.id(), prov::read, new PayloadHandler(prov));
            }
            locked = true;
        }
    }

    private static class PayloadHandler<T extends CustomPacketPayload, C extends IPayloadContext> implements IPayloadHandler<T> {
        private PayloadProvider<T, C> provider;
        private Optional<PacketFlow> flow;
        private List<ConnectionProtocol> protocols;

        private PayloadHandler(PayloadProvider<T, C> provider) {
            this.provider = provider;
            this.flow = provider.getFlow();
            this.protocols = provider.getSupportedProtocols();
            Preconditions.checkArgument(!this.protocols.isEmpty(), "The payload registration for " + provider.id() + " must specify at least one valid protocol.");
        }

        @Override
        @SuppressWarnings("unchecked")
        public void handle(T payload, IPayloadContext context) {
            if (this.flow.isPresent() && this.flow.get() != context.flow()) {
                CitrusLib.LOGGER.error("Received a payload {} on the incorrect side.", payload.id());
                return;
            }

            if (!this.protocols.contains(context.protocol())) {
                CitrusLib.LOGGER.error("Received a payload {} on the incorrect protocol.", payload.id());
                return;
            }

            this.provider.handle(payload, (C) context);
        }
    }
}
