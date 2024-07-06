package dev.teamcitrus.citruslib.network;

import dev.teamcitrus.citruslib.CitrusLib;
import dev.teamcitrus.citruslib.team.CitrusTeamManagerClient;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record ChangeTeamPayload(UUID player, UUID oldTeam, UUID newTeam) implements CustomPacketPayload {
    public static final Type<ChangeTeamPayload> TYPE = new Type<>(CitrusLib.modLoc("change_team"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ChangeTeamPayload> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            ChangeTeamPayload::player,
            UUIDUtil.STREAM_CODEC,
            ChangeTeamPayload::oldTeam,
            UUIDUtil.STREAM_CODEC,
            ChangeTeamPayload::newTeam,
            ChangeTeamPayload::new
    );

    public UUID player() {
        return player;
    }

    public UUID oldTeam() {
        return oldTeam;
    }

    public UUID newTeam() {
        return newTeam;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Provider implements PayloadProvider<ChangeTeamPayload> {
        @Override
        public Type<ChangeTeamPayload> getType() {
            return TYPE;
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, ChangeTeamPayload> getCodec() {
            return STREAM_CODEC;
        }

        @Override
        public void handle(ChangeTeamPayload msg, IPayloadContext ctx) {
            CitrusTeamManagerClient.changeTeam(msg.player, msg.oldTeam, msg.newTeam);
        }

        @Override
        public List<ConnectionProtocol> getSupportedProtocols() {
            return List.of(ConnectionProtocol.PLAY);
        }

        @Override
        public Optional<PacketFlow> getFlow() {
            return Optional.of(PacketFlow.CLIENTBOUND);
        }

        @Override
        public String getVersion() {
            return "1";
        }
    }
}
