package dev.teamcitrus.citruslib.network;

import dev.teamcitrus.citruslib.CitrusLib;
import dev.teamcitrus.citruslib.team.CitrusTeamManagerClient;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ChangeTeamPacket implements CustomPacketPayload {
    public static final ResourceLocation ID = CitrusLib.modLoc("change_team");
    private final UUID player;
    private final UUID oldTeam;
    private final UUID newTeam;

    public ChangeTeamPacket(UUID player, UUID oldTeam, UUID newTeam) {
        this.player = player;
        this.oldTeam = oldTeam;
        this.newTeam = newTeam;
    }

    public ChangeTeamPacket(FriendlyByteBuf buf) {
        this(buf.readUUID(), buf.readUUID(), buf.readUUID());
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeUUID(player);
        friendlyByteBuf.writeUUID(oldTeam);
        friendlyByteBuf.writeUUID(newTeam);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public static class Provider implements PayloadProvider<ChangeTeamPacket, PlayPayloadContext> {
        @Override
        public ResourceLocation id() {
            return ID;
        }

        @Override
        public ChangeTeamPacket read(FriendlyByteBuf buf) {
            return new ChangeTeamPacket(buf);
        }

        @Override
        public void handle(ChangeTeamPacket msg, PlayPayloadContext ctx) {
            PayloadHelper.handle(() -> CitrusTeamManagerClient.changeTeam(msg.player, msg.oldTeam, msg.newTeam), ctx);
        }

        @Override
        public List<ConnectionProtocol> getSupportedProtocols() {
            return List.of(ConnectionProtocol.PLAY);
        }

        @Override
        public Optional<PacketFlow> getFlow() {
            return Optional.of(PacketFlow.CLIENTBOUND);
        }
    }
}
