package dev.teamcitrus.citruslib.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.event.level.LevelEvent;

public class NewDayEvent extends LevelEvent {
    public NewDayEvent(LevelAccessor level) {
        super(level);
    }

    @Override
    public ServerLevel getLevel() {
        return (ServerLevel)super.getLevel();
    }
}
