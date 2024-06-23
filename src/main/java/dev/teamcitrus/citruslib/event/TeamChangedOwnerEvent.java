package dev.teamcitrus.citruslib.event;

import net.neoforged.bus.api.Event;

import javax.annotation.Nullable;
import java.util.UUID;

public class TeamChangedOwnerEvent extends Event {
    private final UUID teamUUID;
    private final UUID newOwner;

    public TeamChangedOwnerEvent(UUID teamUUID, UUID newOwner) {
        this.teamUUID = teamUUID;
        this.newOwner = newOwner;
    }

    @Nullable
    public UUID getNewOwner() {
        return newOwner;
    }

    public UUID getTeamUUID() {
        return teamUUID;
    }
}
