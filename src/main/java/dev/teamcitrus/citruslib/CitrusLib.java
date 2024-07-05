package dev.teamcitrus.citruslib;

import dev.teamcitrus.citruslib.internal.registry.PermissionRegistry;
import dev.teamcitrus.citruslib.network.ChangeTeamPacket;
import dev.teamcitrus.citruslib.network.PayloadHelper;
import dev.teamcitrus.citruslib.network.SyncTeamDataPacket;
import dev.teamcitrus.citruslib.network.SyncTeamMembersPacket;
import dev.teamcitrus.citruslib.reload.ReloadListenerPackets;
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
        PermissionRegistry.init();
    }

    @SubscribeEvent
    private void commonSetup(final FMLCommonSetupEvent event) {
        PayloadHelper.registerPayload(new ReloadListenerPackets.Start.Provider());
        PayloadHelper.registerPayload(new ReloadListenerPackets.Content.Provider<>());
        PayloadHelper.registerPayload(new ReloadListenerPackets.End.Provider());
        PayloadHelper.registerPayload(new ChangeTeamPacket.Provider());
        PayloadHelper.registerPayload(new SyncTeamDataPacket.Provider());
        PayloadHelper.registerPayload(new SyncTeamMembersPacket.Provider());
    }

    public static ResourceLocation modLoc(String id) {
        return new ResourceLocation(MODID, id);
    }
}
