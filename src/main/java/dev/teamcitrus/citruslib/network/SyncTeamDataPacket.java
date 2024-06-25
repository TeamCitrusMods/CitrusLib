package dev.teamcitrus.citruslib.network;

import dev.teamcitrus.citruslib.CitrusLib;
import dev.teamcitrus.citruslib.team.CitrusTeamManagerClient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;
import java.util.Optional;

public class SyncTeamDataPacket extends SyncCompoundTagPacket {
    public static final Type<SyncTeamDataPacket> TYPE = new Type<>(CitrusLib.modLoc("sync_team_data"));

    public SyncTeamDataPacket(CompoundTag tag) {
        super(tag);
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncTeamDataPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.COMPOUND_TAG,
            SyncTeamDataPacket::tag,
            SyncTeamDataPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Provider implements PayloadProvider<SyncTeamDataPacket, IPayloadContext> {
        @Override
        public Type<SyncTeamDataPacket> type() {
            return TYPE;
        }

        @Override
        public StreamCodec<?, ?> codec() {
            return STREAM_CODEC;
        }

        @Override
        public void handle(SyncTeamDataPacket msg, IPayloadContext ctx) {
            PayloadHelper.handle(() -> CitrusTeamManagerClient.setInstance(msg.tag), ctx);
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
