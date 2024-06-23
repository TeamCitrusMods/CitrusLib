package dev.teamcitrus.citruslib.network;

import com.google.common.collect.Maps;
import dev.teamcitrus.citruslib.CitrusLib;
import dev.teamcitrus.citruslib.team.CitrusTeamManagerClient;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

public class SyncTeamMembersPacket implements CustomPacketPayload {
    public static final ResourceLocation ID = CitrusLib.modLoc("sync_team_members");
    public final Map<UUID, UUID> memberOf;

    public SyncTeamMembersPacket(Map<UUID, UUID> memberOf) {
        this.memberOf = memberOf;
    }

    public SyncTeamMembersPacket(FriendlyByteBuf from) {
        memberOf = Maps.newHashMap();
        int size = from.readByte();
        IntStream.range(0, size).forEach(i ->
                memberOf.put(from.readUUID(), from.readUUID()));
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeByte(memberOf.size());
        memberOf.forEach((key, value) -> {
            friendlyByteBuf.writeUUID(key);
            friendlyByteBuf.writeUUID(value);
        });
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public static class Provider implements PayloadProvider<SyncTeamMembersPacket, PlayPayloadContext> {
        @Override
        public ResourceLocation id() {
            return ID;
        }

        @Override
        public SyncTeamMembersPacket read(FriendlyByteBuf buf) {
            return new SyncTeamMembersPacket(buf);
        }

        @Override
        public void handle(SyncTeamMembersPacket msg, PlayPayloadContext ctx) {
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
