package dev.teamcitrus.citruslib.payload;

import dev.teamcitrus.citruslib.CitrusLib;
import dev.teamcitrus.citruslib.network.PayloadProvider;
import dev.teamcitrus.citruslib.team.CitrusTeamManagerClient;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.*;

public record SyncTeamMembersPayload(Map<UUID, UUID> memberOf) implements CustomPacketPayload {
    public static final Type<SyncTeamMembersPayload> TYPE = new Type<>(CitrusLib.modLoc("sync_team_members"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncTeamMembersPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(
                    HashMap::new,
                    UUIDUtil.STREAM_CODEC,
                    UUIDUtil.STREAM_CODEC),
            SyncTeamMembersPayload::memberOf,
            SyncTeamMembersPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return type();
    }

    public static class Provider implements PayloadProvider<SyncTeamMembersPayload> {

        @Override
        public Type<SyncTeamMembersPayload> getType() {
            return TYPE;
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, SyncTeamMembersPayload> getCodec() {
            return STREAM_CODEC;
        }

        @Override
        public void handle(SyncTeamMembersPayload msg, IPayloadContext ctx) {
            CitrusTeamManagerClient.setMembers(msg.memberOf);
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
