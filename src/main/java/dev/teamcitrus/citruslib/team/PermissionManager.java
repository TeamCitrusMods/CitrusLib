package dev.teamcitrus.citruslib.team;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class PermissionManager {
    public static final List<ResourceLocation> ALL_PERMISSIONS = new ArrayList<>();

    public static void register(ResourceLocation id) {
        if (ALL_PERMISSIONS.contains(id)) throw new UnsupportedOperationException("ID already exists: " + id);
        ALL_PERMISSIONS.add(id);
    }
}
