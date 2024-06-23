package dev.teamcitrus.citruslib.item;

import dev.teamcitrus.citruslib.CitrusLib;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagRegistry;

public class CitrusFeatureFlags {
    private static final FeatureFlagRegistry.Builder BUILDER = new FeatureFlagRegistry.Builder(CitrusLib.MODID);
    public static final FeatureFlag DEV = BUILDER.create(CitrusLib.modLoc("development"));
}
