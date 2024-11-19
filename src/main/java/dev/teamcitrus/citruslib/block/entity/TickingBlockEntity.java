package dev.teamcitrus.citruslib.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Convenience interface for allowing {@linkplain BlockEntity block entities} to hold their own tick methods.
 * <p>
 * Static tick methods were a mistake™
 */
public interface TickingBlockEntity {

    /**
     * Ticks this block entity on the logical server.
     * <p>
     * Only called if the block entity type {@linkplain TickingBlockEntityType.TickSide#ticksOnServer() ticks on the server}.
     *
     * @param level The level the block entity is in
     * @param pos   The position of the block entity
     * @param state The block state of the block entity
     */
    public default void serverTick(Level level, BlockPos pos, BlockState state) {}

    /**
     * Ticks this block entity on the logical client.
     * <p>
     * Only called if the block entity type {@linkplain TickingBlockEntityType.TickSide#ticksOnClient() ticks on the client}.
     *
     * @param level The level the block entity is in
     * @param pos   The position of the block entity
     * @param state The block state of the block entity
     */
    public default void clientTick(Level level, BlockPos pos, BlockState state) {}
}
