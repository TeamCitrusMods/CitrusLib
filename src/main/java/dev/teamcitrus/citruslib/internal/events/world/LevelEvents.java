package dev.teamcitrus.citruslib.internal.events.world;

import dev.teamcitrus.citruslib.CitrusLib;
import dev.teamcitrus.citruslib.event.NewDayEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.TickEvent;

@Mod.EventBusSubscriber(modid = CitrusLib.MODID)
public class LevelEvents {
    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if(!event.level.isClientSide() && event.phase == TickEvent.Phase.END && event.level.getDayTime() % 24000L == 1) {
            NeoForge.EVENT_BUS.post(new NewDayEvent(event.level));
        }
    }
}
