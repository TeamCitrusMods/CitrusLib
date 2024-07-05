package dev.teamcitrus.citruslib.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class CompoundTagUtil {
    public static void putResourceLocation(CompoundTag tag, String id, ResourceLocation resourceLocation) {
        tag.putString(id, resourceLocation.toString());
    }

    public static ResourceLocation getResourceLocation(CompoundTag tag, String id) {
        return ResourceLocation.tryParse(tag.getString(id));
    }
}
