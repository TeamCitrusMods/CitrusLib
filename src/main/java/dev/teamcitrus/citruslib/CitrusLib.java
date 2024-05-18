package dev.teamcitrus.citruslib;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CitrusLib.MODID)
public class CitrusLib {
    public static final String MODID = "citruslib";
    private static final Logger LOGGER = LogManager.getLogger();

    public CitrusLib(IEventBus bus) {
        bus.register(this);
    }

    @SubscribeEvent
    private void commonSetup(final FMLCommonSetupEvent event) {
    }
}
