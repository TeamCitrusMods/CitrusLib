package dev.teamcitrus.citruslib.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;

public class CommandUtil {
    public static Component withClickableCommand(String command, String tooltip, Object... formatting) {
        return withClickableCommand(ChatFormatting.AQUA, command, tooltip, formatting);
    }

    public static Component withClickableCommand(ChatFormatting color, String command, String tooltip, Object... formatting) {
        return Component.translatable(command, formatting).withStyle((style) -> style.withColor(color)
                .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command + StringUtil.join(' ', formatting)))
                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable(tooltip))));
    }
}
