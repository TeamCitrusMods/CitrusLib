package dev.teamcitrus.citruslib.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public abstract class SyncCompoundTagPacket implements CustomPacketPayload {
    public CompoundTag tag;

    public SyncCompoundTagPacket(CompoundTag tag) {
        this.tag = tag;
    }

    public SyncCompoundTagPacket(final FriendlyByteBuf buf) {
        this.tag = buf.readNbt();
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeNbt(tag);
    }
}
