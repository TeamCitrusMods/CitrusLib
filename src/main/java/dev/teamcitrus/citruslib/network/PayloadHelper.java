package dev.teamcitrus.citruslib.network;

import com.google.common.base.Preconditions;
import dev.teamcitrus.citruslib.CitrusLib;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Taken from Placebo with consent of Shadows
 * @author Shadows of Fire
 * @link <a href="https://github.com/Shadows-of-Fire/Placebo/tree/1.20.4">...</a>
 */
public class PayloadHelper {
    private static final Map<ResourceLocation, PayloadProvider<?, ?>> ALL_PROVIDERS = new HashMap<>();
    private static boolean locked = false;

    public static <T extends CustomPacketPayload, C extends IPayloadContext> void registerPayload(PayloadProvider<T, C> prov) {
        Preconditions.checkNotNull(prov);
        synchronized (ALL_PROVIDERS) {
            if (locked) throw new UnsupportedOperationException("Attempted to register a payload provider after registration has finished.");
            if (ALL_PROVIDERS.containsKey(prov.type().id())) throw new UnsupportedOperationException("Attempted to register payload provider with duplicate ID: " + prov.type().id());
            ALL_PROVIDERS.put(prov.type().id(), prov);
        }
    }

    public static void handle(Runnable r, IPayloadContext ctx) {
        ctx.enqueueWork(r);
    }

    @SubscribeEvent
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void registerProviders(RegisterPayloadHandlersEvent event) {
        synchronized (ALL_PROVIDERS) {
            for (PayloadProvider prov : ALL_PROVIDERS.values()) {
                PayloadRegistrar reg = event.registrar(prov.type().id().getNamespace());

                if (prov.isOptional()) {
                    reg = reg.optional();
                }

                reg = reg.versioned(prov.getVersion()); // Using a rawtype also rawtypes the Optional

                //reg.common(prov.id(), prov::read, new PayloadHandler(prov));
                reg.commonBidirectional(prov.type(), prov.codec(), new PayloadHandler(prov));
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
            Preconditions.checkArgument(!this.protocols.isEmpty(), "The payload registration for " + provider.type().id() + " must specify at least one valid protocol.");
        }

        @Override
        @SuppressWarnings("unchecked")
        public void handle(T payload, IPayloadContext context) {
            if (this.flow.isPresent() && this.flow.get() != context.flow()) {
                CitrusLib.LOGGER.error("Received a payload {} on the incorrect side.", payload.type().id());
                return;
            }

            if (!this.protocols.contains(context.protocol())) {
                CitrusLib.LOGGER.error("Received a payload {} on the incorrect protocol.", payload.type().id());
                return;
            }

            this.provider.handle(payload, (C) context);
        }
    }
}
