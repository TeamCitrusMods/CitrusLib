package dev.teamcitrus.citruslib.internal.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import dev.teamcitrus.citruslib.team.CitrusTeam;
import dev.teamcitrus.citruslib.team.CitrusTeamManager;
import dev.teamcitrus.citruslib.util.CommandUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public class CreateTeamCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("create")
                .then(Commands.argument("name", StringArgumentType.string())
                        .executes(ctx -> {
                            CitrusTeamManager teams = CitrusTeamManager.getTeamsFromContext(ctx);
                            CitrusTeam current = CitrusTeamManager.getTeamFromContext(ctx);
                            CommandSourceStack source = ctx.getSource();
                            UUID playerID = source.getPlayerOrException().getUUID();

                            if (!current.getID().equals(playerID)) {
                                source.sendFailure(
                                        Component.translatable("command.citruslib.team.create.must_leave",
                                                CommandUtil.withClickableCommand("/citruslib team leave", "command.citruslib.team.create.must_leave.tooltip")
                                        )
                                );
                                return 0;
                            }

                            String name = StringArgumentType.getString(ctx, "name");
                            if (teams.nameExists(name)) {
                                source.sendFailure(Component.translatable("command.citruslib.team.create.name_in_use", name));
                                return 0;
                            }

                            teams.changeTeam(ctx, UUID.randomUUID(), (pt) -> pt.setName(name));
                            source.sendSuccess(() -> Component.translatable("command.citruslib.team.create.success", name), false);
                            return 1;
                        }));
    }
}
