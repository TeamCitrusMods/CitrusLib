package dev.teamcitrus.citruslib.internal.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import dev.teamcitrus.citruslib.team.CitrusTeam;
import dev.teamcitrus.citruslib.team.CitrusTeamManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public class RenameTeamCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("rename")
                .then(Commands.argument("name", StringArgumentType.string())
                        .executes(ctx -> {
                            CitrusTeamManager teams = CitrusTeamManager.getTeamsFromContext(ctx);
                            CitrusTeam current = CitrusTeamManager.getTeamFromContext(ctx);
                            CommandSourceStack source = ctx.getSource();
                            UUID playerID = source.getPlayerOrException().getUUID();

                            if (!playerID.equals(current.getOwner())) {
                                source.sendFailure(Component.translatable("command.citruslib.team.rename.not_owner"));
                                return 0;
                            }

                            //If the team name is already in use then error
                            String name = StringArgumentType.getString(ctx, "name");
                            if (teams.nameExists(name)) {
                                source.sendFailure(Component.translatable("command.citruslib.team.rename.name_in_use"));
                                return 0;
                            }

                            teams.changeTeam(ctx, current.getID(), (pt) -> pt.setName(name));
                            source.sendSuccess(() -> Component.translatable("command.citruslib.team.rename.success"), false);
                            return 1;
                        }));
    }
}
