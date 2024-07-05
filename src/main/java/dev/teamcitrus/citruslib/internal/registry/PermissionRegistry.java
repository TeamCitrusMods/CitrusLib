package dev.teamcitrus.citruslib.internal.registry;

import dev.teamcitrus.citruslib.CitrusLib;
import dev.teamcitrus.citruslib.team.PermissionManager;

public class PermissionRegistry {
    public static void init() {
        PermissionManager.register(CitrusLib.modLoc("allow_petting"));
    }
}
