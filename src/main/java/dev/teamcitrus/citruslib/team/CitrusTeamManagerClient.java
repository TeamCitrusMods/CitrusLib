package dev.teamcitrus.citruslib.team;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class CitrusTeamManagerClient {
    private static final Multimap<UUID, UUID> teamMembers = HashMultimap.create();
    private static CitrusTeam INSTANCE;

    public static CitrusTeam getInstance() {
        return INSTANCE;
    }

    public static void setInstance(CompoundTag data) {
        INSTANCE = new CitrusTeam(data);
        INSTANCE.setClient();
    }

    public static void setMembers(Map<UUID, UUID> memberOf) {
        memberOf.forEach((key, value) -> teamMembers.get(value).add(key));
    }

    public static void changeTeam(UUID player, UUID oldTeam, UUID newTeam) {
        teamMembers.get(oldTeam).remove(player);
        teamMembers.get(newTeam).add(player);
    }

    public static Collection<UUID> members(UUID teamID) {
        return teamMembers.get(teamID);
    }

    public static void setTag(String tagName, CompoundTag tag) {
        INSTANCE.getData().put(tagName, tag);
    }
}
