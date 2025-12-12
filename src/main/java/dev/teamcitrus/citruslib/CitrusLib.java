package dev.teamcitrus.citruslib;

import dev.teamcitrus.citruslib.network.PayloadHelper;
import dev.teamcitrus.citruslib.tab.TabFillingRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;

@Mod(CitrusLib.MODID)
public class CitrusLib {
    public static final String MODID = "citruslib";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final MutableComponent FAKE_CHAT_MESSAGE = Component.translatable("message.citruslib.fake_chat");

    public CitrusLib(IEventBus bus) {
        bus.register(new PayloadHelper());
        bus.addListener(TabFillingRegistry::fillTabs);
    }

    @ApiStatus.Internal
    public static Identifier modLoc(String id) {
        return Identifier.fromNamespaceAndPath(MODID, id);
    }
}
