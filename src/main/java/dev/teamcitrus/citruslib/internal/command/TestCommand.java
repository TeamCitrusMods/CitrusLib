package dev.teamcitrus.citruslib.internal.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.teamcitrus.citruslib.team.CitrusTeamManager;
import dev.teamcitrus.citruslib.team.PermissionArgument;
import dev.teamcitrus.citruslib.team.PermissionManager;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class TestCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("test")
                .executes(
                        context -> {
                            /*
                            context.getSource().getPlayerOrException().sendSystemMessage(
                                    Component.literal(PermissionManager.ALL_PERMISSIONS.toString())
                            );
                             */

                            CitrusTeamManager manager = CitrusTeamManager.getTeamsFromContext(context);
                            var members = manager.getTeamMembers(CitrusTeamManager.getTeamUUIDForPlayer(context.getSource().getPlayerOrException()));

                            members.forEach((uuid, resourceLocations) -> {
                                try {
                                    context.getSource().getPlayerOrException().sendSystemMessage(
                                            Component.literal("UUID: " + uuid + ". Perms: " + resourceLocations)
                                    );
                                } catch (CommandSyntaxException e) {
                                    throw new RuntimeException(e);
                                }
                            });

                            return 1;
                        }
                );
    }
}
