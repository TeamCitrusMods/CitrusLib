package dev.teamcitrus.citruslib.network;

import dev.teamcitrus.citruslib.CitrusLib;
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

public record SyncTeamMembersPacket(HashMap<UUID, UUID> memberOf) implements CustomPacketPayload {
    public static final Type<SyncTeamMembersPacket> TYPE = new Type<>(CitrusLib.modLoc("sync_team_members"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncTeamMembersPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(
                    HashMap::new,
                    UUIDUtil.STREAM_CODEC,
                    UUIDUtil.STREAM_CODEC),
            SyncTeamMembersPacket::memberOf,
            SyncTeamMembersPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return type();
    }

    public static class Provider implements PayloadProvider<SyncTeamMembersPacket, IPayloadContext> {
        @Override
        public Type<?> type() {
            return TYPE;
        }

        @Override
        public StreamCodec<?, ?> codec() {
            return STREAM_CODEC;
        }

        @Override
        public void handle(SyncTeamMembersPacket msg, IPayloadContext ctx) {
            PayloadHelper.handle(() -> CitrusTeamManagerClient.setMembers(msg.memberOf), ctx);
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
