package dev.teamcitrus.citruslib.internal.events.item;

import dev.teamcitrus.citruslib.CitrusLib;
import dev.teamcitrus.citruslib.item.CitrusItem;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@Mod.EventBusSubscriber(modid = CitrusLib.MODID)
public class ItemEvents {
    @SubscribeEvent
    public static void onTooltipEvent(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() instanceof CitrusItem item) {
            if (item.isWIP()) {
                event.getToolTip().add(Component.translatable("tooltip.citruslib.wip"));
            }
        }
    }
}
