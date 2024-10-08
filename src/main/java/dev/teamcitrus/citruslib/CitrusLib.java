package dev.teamcitrus.citruslib;

import dev.teamcitrus.citruslib.internal.registry.BlockEntityRegistry;
import dev.teamcitrus.citruslib.network.PayloadHelper;
import dev.teamcitrus.citruslib.reload.ReloadListenerPayloads;
import dev.teamcitrus.citruslib.tab.TabFillingRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;

@Mod(CitrusLib.MODID)
public class CitrusLib {
    public static final String MODID = "citruslib";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final MutableComponent FAKE_CHAT_MESSAGE = Component.translatable("message.citruslib.fake_chat");

    public CitrusLib(IEventBus bus) {
        bus.register(this);
        bus.register(new PayloadHelper());
        bus.addListener(TabFillingRegistry::fillTabs);
        BlockEntityRegistry.BLOCK_ENTITIES.register(bus);
    }

    @SubscribeEvent
    private void commonSetup(final FMLCommonSetupEvent event) {
        PayloadHelper.registerPayload(new ReloadListenerPayloads.Start.Provider());
        PayloadHelper.registerPayload(new ReloadListenerPayloads.Content.Provider<>());
        PayloadHelper.registerPayload(new ReloadListenerPayloads.End.Provider());
    }

    @ApiStatus.Internal
    public static ResourceLocation modLoc(String id) {
        return ResourceLocation.fromNamespaceAndPath(MODID, id);
    }
}
