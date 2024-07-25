package dev.teamcitrus.citruslib.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.event.level.LevelEvent;

/**
 * An event that is fired at the start of every new day (Aka at the vanilla wakeup time after sleeping)
 */
public class NewDayEvent extends LevelEvent {
    public NewDayEvent(LevelAccessor level) {
        super(level);
    }

    @Override
    public ServerLevel getLevel() {
        return (ServerLevel)super.getLevel();
    }
}
