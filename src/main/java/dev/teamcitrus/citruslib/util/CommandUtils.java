package dev.teamcitrus.citruslib.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;

public class CommandUtils {
    public static Component withClickableCommand(String command, String tooltip, Object... formatting) {
        return withClickableCommand(ChatFormatting.AQUA, command, tooltip, formatting);
    }

    public static Component withClickableCommand(ChatFormatting color, String command, String tooltip, Object... formatting) {
        return Component.translatable(command, formatting).withStyle((p_241055_1_) -> p_241055_1_.withColor(color)
                .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command + StringUtils.join(' ', formatting)))
                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable(tooltip))));
    }
}
