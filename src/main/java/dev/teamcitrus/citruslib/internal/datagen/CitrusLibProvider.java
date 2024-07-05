package dev.teamcitrus.citruslib.internal.datagen;

import dev.teamcitrus.citruslib.CitrusLib;
import dev.teamcitrus.citruslib.internal.datagen.provider.lang.EnUsProvider;
import dev.teamcitrus.citruslib.util.DatagenUtil;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod.EventBusSubscriber(modid = CitrusLib.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CitrusLibProvider {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();

        gen.addProvider(event.includeClient(), new EnUsProvider(output));
        gen.addProvider(true, DatagenUtil.makeMetadataFile(output, CitrusLib.MODID));
    }
}
