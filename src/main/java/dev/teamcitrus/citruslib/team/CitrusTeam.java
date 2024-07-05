package dev.teamcitrus.citruslib.team;

import dev.teamcitrus.citruslib.event.TeamChangedOwnerEvent;
import dev.teamcitrus.citruslib.network.SyncTeamDataPacket;
import joptsimple.internal.Strings;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.*;

public class CitrusTeam implements INBTSerializable<CompoundTag> {
    private boolean isClient;
    private CompoundTag data = new CompoundTag();
    private Map<UUID, List<ResourceLocation>> members = new HashMap<>(); // Member UUID -> List of Permissions
    private Set<UUID> invited = new HashSet<>();
    private UUID teamUUID;
    private UUID owner;
    private String name = Strings.EMPTY;

    public CitrusTeam(UUID uuid) {
        this.teamUUID = uuid;
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
        if (!members.containsKey(owner)) {
            this.owner = members.keySet().stream().findFirst().orElse(null); //Grab a new one, it can be null
            NeoForge.EVENT_BUS.post(new TeamChangedOwnerEvent(teamUUID, owner));
        }

        syncToTeam(world);
        return this;
    }

    public CitrusTeam(CompoundTag data) {
        this.deserializeNBT(data);
    }

    public Map<UUID, List<ResourceLocation>> members() {
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

    public SyncTeamDataPacket getSyncPacket() {
        return new SyncTeamDataPacket(serializeNBT());
    }

    public void syncToPlayer(ServerPlayer player) {
        if (player != null) {
            PacketDistributor.PLAYER.with(player).send(new SyncTeamDataPacket(serializeNBT()));
        }
    }

    public void syncToTeam(ServerLevel world) {
        CitrusTeam team = CitrusTeamManager.getTeamFromID(world, teamUUID);
        if (team != null) {
            team.members().keySet().stream()
                    .map(world::getPlayerByUUID)
                    .filter(player -> player instanceof ServerPlayer)
                    .map(player -> (ServerPlayer)player)
                    .forEach(player -> PacketDistributor.PLAYER.with(player).send(new SyncTeamDataPacket(serializeNBT())));
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
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        compound.putString("UUID", teamUUID.toString());
        compound.putString("Name", name == null ? teamUUID.toString() : name);
        compound.put("Data", data);

        // New members syste,
        CompoundTag tag = new CompoundTag();
        members.forEach(((uuid, resourceLocations) -> {
            ListTag list = new ListTag();
            resourceLocations.forEach(resourceLocation -> {
                list.add(StringTag.valueOf(resourceLocation.toString()));
            });
            tag.put(uuid.toString(), list);
        }));
        compound.put("Members", tag);

        // Old members system
        //ListTag list = new ListTag();
        //members.forEach(uuid -> list.add(StringTag.valueOf(uuid.toString())));
        //compound.put("Members", list);

        ListTag invitedList = new ListTag();
        invited.forEach(uuid -> invitedList.add(StringTag.valueOf(uuid.toString())));
        compound.put("Invited", invitedList);
        if (owner != null) {
            compound.putString("Owner", owner.toString());
        }

        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag compound) {
        teamUUID = UUID.fromString(compound.getString("UUID"));
        name = compound.contains("Name") ? compound.getString("Name") : teamUUID.toString();
        data = compound.getCompound("Data");
        members = new HashMap<>();
        invited = new HashSet<>();
        owner = compound.contains("Owner") ? UUID.fromString(compound.getString("Owner")) : null;

        /*
        ListTag list = compound.getList("Members", Tag.TAG_STRING);
        for (int i = 0; i < list.size(); i++) {
            members.add(UUID.fromString(list.getString(i)));
        }
         */

        ListTag list = compound.getList("Members", Tag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            CompoundTag tag = list.getCompound(i);
            tag.getAllKeys().forEach(s -> {
                ListTag permissionTag = tag.getList(s, Tag.TAG_STRING);
                List<ResourceLocation> permissions = new ArrayList<>();
                for (int j = 0; j < permissionTag.size(); j++) {
                    permissions.add(ResourceLocation.tryParse(permissionTag.getString(j)));
                }
                members.put(UUID.fromString(s), permissions);
            });
        }

        compound.getList("Invited", 8)
                .forEach(nbt -> invited.add(UUID.fromString(nbt.getAsString())));

        if (owner == null) {
            owner = members.keySet().stream().findFirst().orElse(null);
            NeoForge.EVENT_BUS.post(new TeamChangedOwnerEvent(teamUUID, owner));
        }
    }
}
