package dev.teamcitrus.citruslib.internal.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import dev.teamcitrus.citruslib.team.CitrusTeam;
import dev.teamcitrus.citruslib.team.CitrusTeamManager;
import dev.teamcitrus.citruslib.util.CommandUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class ListInvitesCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("invite_list")
                .executes(ctx -> {
                    boolean success = false;
                    for (CitrusTeam team: CitrusTeamManager.get(ctx.getSource().getLevel()).teams()) {
                        if (team.isInvited(ctx.getSource().getPlayerOrException().getUUID())) {
                            ctx.getSource().getPlayerOrException().createCommandSourceStack().sendSuccess(() -> Component.translatable("command.citruslib.team.invite.message",
                                    team.getName(), CommandUtil.withClickableCommand(ChatFormatting.GREEN, "/citruslib team join %s", team.getName())), false);
                            success = true;
                        }
                    }

                    if (!success) {
                        ctx.getSource().sendFailure(Component.translatable("command.citruslib.team.invite.none"));
                    }

                    return success ? 1 : 0;
                });
    }
}
