package dev.teamcitrus.citruslib;

import dev.teamcitrus.citruslib.event.NewDayEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = CitrusLib.MODID)
public class CitrusLibTester {
    @SubscribeEvent
    public static void test(NewDayEvent event) {
        CitrusLib.LOGGER.debug("New Day - Test Success");
    }
}
