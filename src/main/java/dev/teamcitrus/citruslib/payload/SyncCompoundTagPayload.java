package dev.teamcitrus.citruslib.payload;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public abstract class SyncCompoundTagPayload implements CustomPacketPayload {
    public CompoundTag tag;

    public SyncCompoundTagPayload(CompoundTag tag) {
        this.tag = tag;
    }

    public CompoundTag tag() {
        return tag;
    }
}
