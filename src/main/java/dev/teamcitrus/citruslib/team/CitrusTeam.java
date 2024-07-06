package dev.teamcitrus.citruslib.team;

import dev.teamcitrus.citruslib.event.TeamChangedOwnerEvent;
import dev.teamcitrus.citruslib.network.SyncTeamDataPayload;
import joptsimple.internal.Strings;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CitrusTeam implements INBTSerializable<CompoundTag> {
    private boolean isClient;
    private CompoundTag data;
    private Set<UUID> members;
    private Set<UUID> invited;
    private UUID teamUUID;
    private UUID owner;
    private String name = Strings.EMPTY;

    public CitrusTeam(UUID uuid) {
        this.teamUUID = uuid;
        this.members = new HashSet<>();
        this.invited = new HashSet<>();
        this.data = new CompoundTag();
    }

    public void setClient() {
        this.isClient = true;
    }

    public boolean isClient() {
        return isClient;
    }

    public void invite(ServerLevel level, UUID uuid) {
        invited.add(uuid);
    }

    public void clearInvite(UUID playerID) {
        invited.remove(playerID);
    }

    public boolean isInvited(UUID uuid) {
        return invited.contains(uuid);
    }

    public CitrusTeam onChanged(ServerLevel world) {
        if (!members.contains(owner)) {
            this.owner = members.stream().findFirst().orElse(null); //Grab a new one, it can be null
            NeoForge.EVENT_BUS.post(new TeamChangedOwnerEvent(teamUUID, owner));
        }

        syncToTeam(world);
        return this;
    }

    public CitrusTeam(CompoundTag data, HolderLookup.Provider provider) {
        this.deserializeNBT(provider, data);
    }

    public Set<UUID> members() {
        return members;
    }

    @Nullable
    public UUID getOwner() {
        return owner;
    }

    public UUID getID() {
        return teamUUID;
    }

    public CompoundTag getData() {
        return data;
    }

    public SyncTeamDataPayload getSyncPacket(Level level) {
        return new SyncTeamDataPayload(serializeNBT(level.registryAccess()));
    }

    public void syncToPlayer(ServerPlayer player) {
        if (player != null) {
            PacketDistributor.sendToPlayer(player, new SyncTeamDataPayload(serializeNBT(player.level().registryAccess())));
        }
    }

    public void syncToTeam(ServerLevel world) {
        CitrusTeam team = CitrusTeamManager.getTeamFromID(world, teamUUID);
        if (team != null) {
            team.members().stream()
                    .map(world::getPlayerByUUID)
                    .filter(player -> player instanceof ServerPlayer)
                    .map(player -> (ServerPlayer)player)
                    .forEach(player -> PacketDistributor.sendToPlayer(player, new SyncTeamDataPayload(serializeNBT(world.registryAccess()))));
        }
    }

    /*
    public void syncSubTag(String subTag, ServerLevel world) {
        PenguinNetwork.sendToTeam(world, teamUUID, new SyncTeamSubTagPacket(subTag, data.getCompound(subTag)));
    }
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag compound = new CompoundTag();
        compound.putString("UUID", teamUUID.toString());
        compound.putString("Name", name == null ? teamUUID.toString() : name);
        compound.put("Data", data);
        ListTag list = new ListTag();
        members.forEach(uuid -> list.add(StringTag.valueOf(uuid.toString())));
        compound.put("Members", list);

        ListTag invitedList = new ListTag();
        invited.forEach(uuid -> invitedList.add(StringTag.valueOf(uuid.toString())));
        compound.put("Invited", invitedList);
        if (owner != null) {
            compound.putString("Owner", owner.toString());
        }

        return compound;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compound) {
        teamUUID = UUID.fromString(compound.getString("UUID"));
        name = compound.contains("Name") ? compound.getString("Name") : teamUUID.toString();
        data = compound.getCompound("Data");
        members = new HashSet<>();
        invited = new HashSet<>();
        owner = compound.contains("Owner") ? UUID.fromString(compound.getString("Owner")) : null;
        ListTag list = compound.getList("Members", 8);
        for (int i = 0; i < list.size(); i++) {
            members.add(UUID.fromString(list.getString(i)));
        }

        compound.getList("Invited", 8)
                .forEach(nbt -> invited.add(UUID.fromString(nbt.getAsString())));

        if (owner == null) {
            owner = members.stream().findFirst().orElse(null);
            NeoForge.EVENT_BUS.post(new TeamChangedOwnerEvent(teamUUID, owner));
        }
    }
}
