package dev.teamcitrus.citruslib.internal.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import dev.teamcitrus.citruslib.team.CitrusTeam;
import dev.teamcitrus.citruslib.team.CitrusTeamManager;
import dev.teamcitrus.citruslib.util.CommandUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.UsernameCache;

import java.util.UUID;

public class JoinTeamCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("join")
                .then(Commands.argument("name", StringArgumentType.string())
                        .executes(ctx -> {
                            CitrusTeamManager teams = CitrusTeamManager.getTeamsFromContext(ctx);
                            CitrusTeam current = CitrusTeamManager.getTeamFromContext(ctx);
                            CommandSourceStack source = ctx.getSource();
                            UUID playerID = source.getPlayerOrException().getUUID();
                            String name = StringArgumentType.getString(ctx, "name");
                            CitrusTeam joining = teams.getTeamByName(name);
                            //If the player is in a team already, they cannot join one
                            if (!current.getID().equals(playerID)) {
                                source.sendFailure(Component.translatable("command.citruslib.team.join.must_leave",
                                        CommandUtils.withClickableCommand("/citruslib team leave", "command.citruslib.team.join.must_leave.tooltip")));
                                return 0;
                            }

                            //If the team doesn't exist
                            if (joining == null) {
                                source.sendFailure(Component.translatable("command.citruslib.team.join.not_exist", name));
                                return 0;
                            }

                            if (joining.getOwner() != null && !joining.isInvited(playerID)) {
                                source.sendFailure(Component.translatable("command.citruslib.team.join.not_invited", name, UsernameCache.getLastKnownUsername(joining.getOwner())));
                                return 0;
                            }

                            //If the owner is null or the player is invited they can join
                            if (joining.getOwner() == null) source.sendSuccess(() -> Component.translatable("command.citruslib.team.join.success.owner", name), false);
                            else source.sendSuccess(() -> Component.translatable("command.citruslib.team.join.success.member", name), false);
                            teams.changeTeam(ctx, joining.getID(), (pt) -> {});
                            return 1;
                        }));
    }
}
