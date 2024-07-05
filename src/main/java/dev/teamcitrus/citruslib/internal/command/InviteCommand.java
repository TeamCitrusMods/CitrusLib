package dev.teamcitrus.citruslib.internal.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import dev.teamcitrus.citruslib.team.CitrusTeam;
import dev.teamcitrus.citruslib.team.CitrusTeamManager;
import dev.teamcitrus.citruslib.util.CommandUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class InviteCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("invite")
                .then(Commands.argument("player", EntityArgument.player())
                        .executes(ctx -> {
                            CitrusTeam current = CitrusTeamManager.getTeamFromContext(ctx);
                            if (current.getID().equals(ctx.getSource().getPlayerOrException().getUUID())) {
                                ctx.getSource().sendFailure(Component.translatable("command.citruslib.team.invite.no_team"));
                                return 0;
                            }

                            ServerPlayer player = EntityArgument.getPlayer(ctx, "player");

                            if (player.equals(ctx.getSource().getPlayerOrException())) {
                                ctx.getSource().sendFailure(Component.translatable(
                                        "command.citruslib.team.invite.invite_self"
                                ).withStyle(ChatFormatting.RED));
                                return 0;
                            }

                            CitrusTeamManager.get(ctx.getSource().getLevel()).getTeam(ctx.getSource().getPlayerOrException().getUUID()).invite(ctx.getSource().getLevel(), player.getUUID());
                            ctx.getSource().sendSuccess(() -> Component.translatable("command.citruslib.team.invite.success", player.getName().getString()), false);
                            player.createCommandSourceStack().sendSuccess(() -> Component.translatable("command.citruslib.team.invite.message",
                                    current.getName(), CommandUtil.withClickableCommand(ChatFormatting.GREEN, "/citruslib team join " + current.getName(), "command.citruslib.team.invite.tooltip")), false);
                            return 1;
                        }));
    }
}
