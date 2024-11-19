package dev.teamcitrus.citruslib.internal.events;

import dev.teamcitrus.citruslib.CitrusLib;
import dev.teamcitrus.citruslib.CitrusLibRegistries;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

@EventBusSubscriber(modid = CitrusLib.MODID)
public class MiscEvents {
    @SubscribeEvent
    public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();
        RegistryAccess access = event.getRegistryAccess();

        access.registryOrThrow(CitrusLibRegistries.BREWING_RECIPE).stream().forEach(potion -> {
            builder.addRecipe(potion.base(), potion.reagent(), potion.output());
        });
    }
}
