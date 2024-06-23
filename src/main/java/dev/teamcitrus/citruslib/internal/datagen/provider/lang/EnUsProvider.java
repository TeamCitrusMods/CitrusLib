package dev.teamcitrus.citruslib.internal.datagen.provider.lang;

import dev.teamcitrus.citruslib.CitrusLib;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class EnUsProvider extends LanguageProvider {
    public EnUsProvider(PackOutput output) {
        super(output, CitrusLib.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("tooltip.citruslib.wip", "Item is W.I.P");
        add("command.citruslib.team.create.must_leave", "YOU ARE ALREADY IN A TEAM!\nYou must leave to create a new one!\n%s");
        add("command.citruslib.team.create.must_leave.tooltip", "Click to leave team");
        add("command.citruslib.team.create.name_in_use", "You cannot use %s for your new team as it is already in use.");
        add("command.citruslib.team.create.success", "You created a new team with the name: %s");

        add("command.citruslib.team.leave.cannot", "You cannot leave your own personal team!");
        add("command.citruslib.team.leave.name_in_use", "You cannot use %s for your new team as it is already in use.");
        add("command.citruslib.team.leave.success", "You have left your team!");

        add("command.citruslib.team.join.must_leave", "YOU ARE ALREADY IN A TEAM!\nYou must leave it in order to join a new one!\n%s");
        add("command.citruslib.team.join.must_leave.tooltip", "Click to leave team");
        add("command.citruslib.team.join.not_exist", "No team with the name %s can be found!");
        add("command.citruslib.team.join.success.owner", "The team %s had no owner. You have now taken control!");
        add("command.citruslib.team.join.success.member", "You are now a member of the team %s, Welcome!");
        add("command.citruslib.team.join.success.not_invited", "You have not been invited to the %s team. Ask %s for an invite.");

        add("command.citruslib.team.invite.success", "You have invited %s to join your team.");
        add("command.citruslib.team.invite.failure", "No player by the name %s could be found.");
        add("command.citruslib.team.invite.message", "You have been invited to join the %s team. To join -> %s");
        add("command.citruslib.team.invite.tooltip", "Click to join the team");
        add("command.citruslib.team.invite.none", "You have no team invites pending.");
        add("command.citruslib.team.invite.invite_self", "You cannot invite yourself to a team!");
        add("command.citruslib.team.invite.no_team", "You can't invite someone when you're not in a team!");

        add("command.citruslib.team.reject.not_exist", "No team with the name %s can be found!");
        add("command.citruslib.team.reject.not_invited", "You have not been invited to join %s!");
        add("command.citruslib.team.reject.success", "You have rejected the invite to join %s.");

        add("command.citruslib.team.rename.not_owner", "You are not the owner of the team!");
        add("command.citruslib.team.rename.name_in_use", "Another team already has this name!");
        add("command.citruslib.team.rename.success", "Team successfully renamed to %s!");
    }
}
