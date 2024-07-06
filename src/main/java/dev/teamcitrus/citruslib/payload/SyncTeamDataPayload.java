package dev.teamcitrus.citruslib.payload;

import dev.teamcitrus.citruslib.CitrusLib;
import dev.teamcitrus.citruslib.network.PayloadProvider;
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

public class SyncTeamDataPayload extends SyncCompoundTagPayload {
    public static final Type<SyncTeamDataPayload> TYPE = new Type<>(CitrusLib.modLoc("sync_team_data"));

    public SyncTeamDataPayload(CompoundTag tag) {
        super(tag);
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncTeamDataPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.COMPOUND_TAG,
            SyncTeamDataPayload::tag,
            SyncTeamDataPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Provider implements PayloadProvider<SyncTeamDataPayload> {
        @Override
        public Type<SyncTeamDataPayload> getType() {
            return TYPE;
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, SyncTeamDataPayload> getCodec() {
            return STREAM_CODEC;
        }

        @Override
        public void handle(SyncTeamDataPayload msg, IPayloadContext ctx) {
            CitrusTeamManagerClient.setInstance(msg.tag, ctx.player().level());
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
