package dev.teamcitrus.citruslib.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import dev.teamcitrus.citruslib.team.CitrusTeam;
import dev.teamcitrus.citruslib.team.CitrusTeamManager;
import dev.teamcitrus.citruslib.util.CommandUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class CreateTeamCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("create")
                .then(Commands.argument("name", StringArgumentType.string())
                        .executes(context -> {
                            CitrusTeamManager teamManager = CitrusTeamManager.getAllTeamsFromContext(context);
                            CitrusTeam playerTeam = CitrusTeamManager.getTeamFromContext(context);

                            if (!playerTeam.getTeamUUID().equals(context.getSource().getPlayerOrException().getUUID())) {
                                context.getSource().sendFailure(
                                        Component.translatable("command.citruslib.team.create.must_leave",
                                                CommandUtils.withClickableCommand("/penguin team leave", "command.citruslib.team.leave.tooltip")
                                        )
                                );
                                return 0;
                            }
                            return 1;
                        }));
    }
}
