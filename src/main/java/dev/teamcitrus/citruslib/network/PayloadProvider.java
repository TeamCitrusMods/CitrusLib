package dev.teamcitrus.citruslib.network;

import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;
import java.util.Optional;

/**
 * Taken from Placebo with consent of Shadows
 * @author Shadows of Fire
 * @link <a href="https://github.com/Shadows-of-Fire/Placebo/tree/1.20.4">...</a>
 */
public interface PayloadProvider<T extends CustomPacketPayload, C extends IPayloadContext> {
    ResourceLocation id();

    T read(FriendlyByteBuf buf);

    void handle(T msg, C ctx);

    List<ConnectionProtocol> getSupportedProtocols();

    Optional<PacketFlow> getFlow();

    default String getVersion() {
        return "1";
    }

    default boolean isOptional() {
        return true;
    }

}
