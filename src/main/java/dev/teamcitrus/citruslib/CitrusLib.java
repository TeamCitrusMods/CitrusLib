package dev.teamcitrus.citruslib;

import dev.teamcitrus.citruslib.network.PayloadHelper;
import dev.teamcitrus.citruslib.reload.ReloadListenerPayloads;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CitrusLib.MODID)
public class CitrusLib {
    public static final String MODID = "citruslib";
    public static final Logger LOGGER = LogManager.getLogger();

    public CitrusLib(IEventBus bus) {
        bus.register(this);
        bus.register(new PayloadHelper());
    }

    @SubscribeEvent
    private void commonSetup(final FMLCommonSetupEvent event) {
        PayloadHelper.registerPayload(new ReloadListenerPayloads.Start.Provider());
        PayloadHelper.registerPayload(new ReloadListenerPayloads.Content.Provider<>());
        PayloadHelper.registerPayload(new ReloadListenerPayloads.End.Provider());
    }

    public static ResourceLocation modLoc(String id) {
        return ResourceLocation.fromNamespaceAndPath(MODID, id);
    }
}
