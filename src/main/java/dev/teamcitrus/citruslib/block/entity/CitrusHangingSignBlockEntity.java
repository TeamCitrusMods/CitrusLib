package dev.teamcitrus.citruslib.block.entity;

import dev.teamcitrus.citruslib.internal.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CitrusHangingSignBlockEntity extends HangingSignBlockEntity {
    public CitrusHangingSignBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return BlockEntityRegistry.CITRUS_HANGING_SIGN.get();
    }
}
