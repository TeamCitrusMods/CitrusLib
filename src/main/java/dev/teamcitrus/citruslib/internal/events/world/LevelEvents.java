package dev.teamcitrus.citruslib.internal.events.world;

import dev.teamcitrus.citruslib.CitrusLib;
import dev.teamcitrus.citruslib.event.NewDayEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = CitrusLib.MODID)
public class LevelEvents {
    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if(!event.getLevel().isClientSide() && event.getLevel().getDayTime() % 24000L == 1) {
            NeoForge.EVENT_BUS.post(new NewDayEvent(event.getLevel()));
        }
    }
}
