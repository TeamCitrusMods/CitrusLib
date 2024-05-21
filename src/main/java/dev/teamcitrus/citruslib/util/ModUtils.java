package dev.teamcitrus.citruslib.util;

import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

public class ModUtils {
    public static boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    public static boolean isModInstalled(String modID) {
        return ModList.get().isLoaded(modID);
    }
}
