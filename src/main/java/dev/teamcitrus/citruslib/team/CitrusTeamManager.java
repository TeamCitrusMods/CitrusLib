package dev.teamcitrus.citruslib.team;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.teamcitrus.citruslib.CitrusLib;
import dev.teamcitrus.citruslib.event.TeamChangedEvent;
import dev.teamcitrus.citruslib.network.ChangeTeamPayload;
import dev.teamcitrus.citruslib.network.SyncTeamMembersPayload;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.UsernameCache;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@EventBusSubscriber(modid = CitrusLib.MODID)
public class CitrusTeamManager extends SavedData {
    private static final String DATA_NAME = "citrus_teams";
    private final Map<UUID, UUID> memberOf = new HashMap<>(); //Player ID > Team ID
    private final Map<UUID, CitrusTeam> teams = new HashMap<>(); // Team ID > Data
    private final Map<String, CitrusTeam> teamsByName = new HashMap<>(); //TeamName > Team (not saved)

    public static CitrusTeamManager get(ServerLevel world) {
        return world.getServer().overworld().getDataStorage().computeIfAbsent(new SavedData.Factory<>(CitrusTeamManager::new, CitrusTeamManager::load), DATA_NAME);
    }

    public boolean nameExists(String name) {
        return teamsByName.containsKey(name);
    }

    public Collection<CitrusTeam> teams() {
        return teams.values();
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            CitrusTeamManager teams = get((ServerLevel) player.level());
            PacketDistributor.sendToPlayer(player, getTeamForPlayer(player).getSyncPacket(event.getEntity().level()), new SyncTeamMembersPayload(teams.memberOf));
        }
    }

    public int getMemberCount(UUID owner_id) {
        UUID team = memberOf.get(owner_id);
        return teams.get(team).members().size();
    }

    public void changeTeam(CommandContext<CommandSourceStack> ctx, UUID newTeam, Consumer<CitrusTeam> consumer) throws CommandSyntaxException {
        changeTeam(ctx.getSource().getLevel(), ctx.getSource().getPlayerOrException().getUUID(), newTeam, consumer);
    }

    public void changeTeam(ServerLevel world, UUID player, UUID newUUID) {
        changeTeam(world, player, newUUID, (pt) -> {});
    }

    public void changeTeam(ServerLevel world, UUID player, UUID newUUID, Consumer<CitrusTeam> function) {
        UUID oldUUID = memberOf.getOrDefault(player, player);
        memberOf.put(player, newUUID);
        CitrusTeam oldTeam = teams.get(oldUUID);
        if (oldTeam != null) {
            oldTeam.members().remove(player);
            oldTeam.onChanged(world);
        }

        if (!teams.containsKey(newUUID)) {
            teams.put(newUUID, new CitrusTeam(newUUID));
        }

        CitrusTeam newTeam = teams.get(newUUID);
        newTeam.members().add(player);
        teamsByName.remove(newTeam.getName(), newTeam); //Remove the old name
        if (player.equals(newUUID))
            newTeam.setName(UsernameCache.getLastKnownUsername(player));
        function.accept(newTeam);
        teamsByName.put(newTeam.getName(), newTeam); //Add the new name
        newTeam.onChanged(world);
        NeoForge.EVENT_BUS.post(new TeamChangedEvent(world, player, oldUUID, newUUID));
        PacketDistributor.sendToAllPlayers(new ChangeTeamPayload(player, oldUUID, newUUID));
        setDirty();
    }

    public CitrusTeam getTeam(UUID team) {
        return teams.get(team);
    }

    public CompoundTag getTeamData(UUID team) {
        return teams.get(team).getData();
    }

    public Collection<UUID> getTeamMembers(UUID team) {
        return teams.get(team).members();
    }

    public static CitrusTeam getTeamFromID(ServerLevel world, UUID team) {
        return get(world).teams.get(team);
    }

    public static CitrusTeam getTeamForPlayer(ServerLevel world, UUID uuid) {
        CitrusTeamManager data = get(world); //Load the serverdata
        if (!data.memberOf.containsKey(uuid)) {
            data.changeTeam(world, uuid, uuid);
            data.setDirty();
        }

        return data.teams.get(data.memberOf.get(uuid));
    }

    public static CitrusTeam getTeamForPlayer(Player player) {
        if (player.level().isClientSide) return CitrusTeamManagerClient.getInstance(); //Client data
        return getTeamForPlayer((ServerLevel) player.level(), player.getUUID());
    }

    public static UUID getTeamUUIDForPlayer(Player player) {
        return getTeamForPlayer(player).getID();
    }

    public static CitrusTeam getTeamFromContext(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        CitrusTeamManager teams = getTeamsFromContext(ctx);
        return teams.getTeam(teams.memberOf.get(ctx.getSource().getPlayerOrException().getUUID()));
    }

    public static CitrusTeamManager getTeamsFromContext(CommandContext<CommandSourceStack> ctx) {
        return get(ctx.getSource().getLevel());
    }

    //Used in Shopaholic
    @SuppressWarnings("unused")
    public static CompoundTag getPenguinStatuses(Player player) {
        CompoundTag data = getTeamForPlayer(player).getData();
        if (!data.contains("PenguinStatuses"))
            data.put("PenguinStatuses", new CompoundTag());
        return data.getCompound("PenguinStatuses");
    }

    public static CitrusTeamManager load(@Nonnull CompoundTag nbt, HolderLookup.Provider provider) {
        CitrusTeamManager teamData = new CitrusTeamManager();
        ListTag data = nbt.getList("Teams", 10);
        for (int i = 0; i < data.size(); i++) {
            CompoundTag tag = data.getCompound(i);
            CitrusTeam team = new CitrusTeam(tag, provider);
            teamData.teams.put(team.getID(), team);
            teamData.teamsByName.put(team.getName(), team);
            team.members().forEach(member -> teamData.memberOf.put(member, team.getID())); //Add the quick reference for members
        }
        return teamData;
    }

    @Nonnull
    @Override
    public CompoundTag save(@NotNull CompoundTag compound, HolderLookup.Provider provider) {
        ListTag data = new ListTag();
        for (Map.Entry<UUID, CitrusTeam> entry : teams.entrySet()) {
            data.add(entry.getValue().serializeNBT(provider));
        }

        compound.put("Teams", data);

        return compound;
    }

    public CitrusTeam getTeamByName(String name) {
        return teamsByName.get(name);
    }
}
