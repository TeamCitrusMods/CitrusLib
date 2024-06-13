package dev.teamcitrus.citruslib.team;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CitrusTeamManager extends SavedData {
    private final Map<UUID, UUID> memberOf = new HashMap<>();
    private final Map<UUID, CitrusTeam> teams = new HashMap<>();
    private final Map<String, CitrusTeam> teamsByName = new HashMap<>();

    public CitrusTeamManager() {
    }

    public static CitrusTeamManager get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(new Factory<>(CitrusTeamManager::new, CitrusTeamManager::load), "citrus_teams");
    }

    public boolean nameExists(String name) {
        return teamsByName.containsKey(name);
    }

    public Collection<CitrusTeam> teams() {
        return teams.values();
    }

    public CitrusTeam getTeam(UUID team) {
        return teams.get(team);
    }

    public static CitrusTeam getTeamFromContext(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        CitrusTeamManager teams = getAllTeamsFromContext(ctx);
        return teams.getTeam(teams.memberOf.get(ctx.getSource().getPlayerOrException().getUUID()));
    }

    public static CitrusTeamManager getAllTeamsFromContext(CommandContext<CommandSourceStack> ctx) {
        return get(ctx.getSource().getLevel());
    }

    public static CitrusTeamManager load(CompoundTag tag) {
        CitrusTeamManager teamManager = new CitrusTeamManager();
        ListTag teams = tag.getList("Teams", Tag.TAG_COMPOUND);
        for (int i = 0; i < teams.size(); i++) {
            CompoundTag nbt = teams.getCompound(i);
            CitrusTeam team = new CitrusTeam(nbt);
            teamManager.teams.put(team.getTeamUUID(), team);
            teamManager.teamsByName.put(team.getTeamName(), team);
            team.getMembers().forEach((uuid, integer) -> teamManager.memberOf.put(uuid, team.getTeamUUID()));
        }
        return teamManager;
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        ListTag data = new ListTag();
        for (Map.Entry<UUID, CitrusTeam> entry : teams.entrySet()) {
            data.add(entry.getValue().serializeNBT());
        }
        compoundTag.put("Teams", data);
        return compoundTag;
    }
}
