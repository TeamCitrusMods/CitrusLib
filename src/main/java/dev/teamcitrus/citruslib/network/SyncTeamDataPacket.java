package dev.teamcitrus.citruslib.network;

import dev.teamcitrus.citruslib.CitrusLib;
import dev.teamcitrus.citruslib.team.CitrusTeamManagerClient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
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

    public SyncTeamDataPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Provider implements PayloadProvider<SyncTeamDataPacket, IPayloadContext> {

        @Override
        public Type<SyncTeamDataPacket> id() {
            return TYPE;
        }

        @Override
        public SyncTeamDataPacket read(FriendlyByteBuf buf) {
            return new SyncTeamDataPacket(buf);
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
