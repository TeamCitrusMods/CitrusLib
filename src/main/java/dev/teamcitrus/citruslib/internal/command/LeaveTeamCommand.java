package dev.teamcitrus.citruslib.internal.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import dev.teamcitrus.citruslib.team.CitrusTeam;
import dev.teamcitrus.citruslib.team.CitrusTeamManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public class LeaveTeamCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("leave")
                .executes(ctx -> {
                    CitrusTeamManager teams = CitrusTeamManager.getTeamsFromContext(ctx);
                    CitrusTeam current = CitrusTeamManager.getTeamFromContext(ctx);
                    CommandSourceStack source = ctx.getSource();
                    UUID playerID = source.getPlayerOrException().getUUID();
                    //If this is a single player team you cannot leave
                    if (current.getID().equals(playerID)) {
                        source.sendFailure(Component.translatable("command.citruslib.team.leave.cannot"));
                        return 0;
                    }

                    teams.changeTeam(ctx, playerID, (pt) -> {});
                    source.sendSuccess(() -> Component.translatable("command.citruslib.team.leave.success"), false);
                    return 1;
                });
    }
}
