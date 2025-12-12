package dev.teamcitrus.citruslib;

import dev.teamcitrus.citruslib.brewing.BrewingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@EventBusSubscriber(modid = CitrusLib.MODID)
public class CitrusLibRegistries {
    public static final ResourceKey<Registry<BrewingRecipe>> BREWING_RECIPE = ResourceKey.createRegistryKey(CitrusLib.modLoc("brewing_recipe"));

    @SubscribeEvent
    public static void registerPacks(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(BREWING_RECIPE, BrewingRecipe.CODEC, BrewingRecipe.CODEC);
    }
}
