package dev.teamcitrus.citruslib.team;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

import java.util.*;

public class CitrusTeam implements INBTSerializable<CompoundTag> {
    private String teamName = "";
    private UUID teamUUID;
    private UUID owner;
    private final Map<UUID, Integer> members = new HashMap<>();
    private CompoundTag membersData;

    public CitrusTeam(UUID uuid) {
        this.teamUUID = uuid;
    }

    public CitrusTeam(CompoundTag tag) {
        this.deserializeNBT(tag);
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public UUID getTeamUUID() {
        return teamUUID;
    }

    public UUID getOwner() {
        return owner;
    }

    public Map<UUID, Integer> getMembers() {
        return members;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putUUID("TeamUUID", getTeamUUID());
        tag.putUUID("OwnerUUID", getOwner());
        CompoundTag membersTag = new CompoundTag();
        members.forEach((uuid, level) -> membersTag.putInt(uuid.toString(), level));
        tag.put("Members", membersTag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        teamUUID = compoundTag.getUUID("TeamUUID");
        owner = compoundTag.getUUID("OwnerUUID");
        CompoundTag memberTag = (CompoundTag) compoundTag.get("Members");
        if (!memberTag.isEmpty()) {
            Set<String> memberIDs = memberTag.getAllKeys();
            memberIDs.forEach(s -> members.put(UUID.fromString(s), memberTag.getInt(s)));
        }
    }

    public enum Permission {
        STANDARD(0), TRUSTED(1), OWNER(2);

        final int level;

        Permission(int level) {
            this.level = level;
        }

        public int getLevel() {
            return this.level;
        }
    }
}
