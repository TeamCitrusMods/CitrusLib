package dev.teamcitrus.citruslib.internal.registry;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.teamcitrus.citruslib.CitrusLib;
import dev.teamcitrus.citruslib.internal.command.*;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod.EventBusSubscriber(modid = CitrusLib.MODID)
public class CitrusLibCommands {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                LiteralArgumentBuilder.<CommandSourceStack>literal("citruslib")
                        .then(Commands.literal("team")
                                .then(CreateTeamCommand.register())
                                .then(InviteCommand.register())
                                .then(ListInvitesCommand.register())
                                .then(JoinTeamCommand.register())
                                .then(LeaveTeamCommand.register())
                                .then(RenameTeamCommand.register())
                                .then(RejectTeamCommand.register())
                        )
        );
    }
}
