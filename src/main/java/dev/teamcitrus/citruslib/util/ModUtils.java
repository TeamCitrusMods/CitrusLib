package dev.teamcitrus.citruslib.util;

import net.neoforged.fml.loading.FMLLoader;

public class ModUtils {
    public static boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }
}
