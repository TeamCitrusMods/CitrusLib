package dev.teamcitrus.citruslib.internal.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import dev.teamcitrus.citruslib.team.CitrusTeam;
import dev.teamcitrus.citruslib.team.CitrusTeamManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public class RejectTeamCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("reject")
                .then(Commands.argument("name", StringArgumentType.string())
                        .executes(ctx -> {
                            CitrusTeamManager teams = CitrusTeamManager.getTeamsFromContext(ctx);
                            CommandSourceStack source = ctx.getSource();
                            UUID playerID = source.getPlayerOrException().getUUID();
                            String name = StringArgumentType.getString(ctx, "name");
                            CitrusTeam joining = teams.getTeamByName(name);

                            //If the team doesn't exist
                            if (joining == null) {
                                source.sendFailure(Component.translatable("command.citruslib.team.reject.not_exist", name));
                                return 0;
                            }

                            if (!joining.isInvited(playerID)) {
                                source.sendFailure(Component.translatable("command.citruslib.team.reject.not_invited", name));
                                return 0;
                            }

                            source.sendSuccess(() -> Component.translatable("command.citruslib.team.reject.success", name), false);
                            joining.clearInvite(playerID);
                            return 1;
                        }));
    }
}
