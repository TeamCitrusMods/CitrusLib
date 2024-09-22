package dev.teamcitrus.citruslib.block.entity;

import dev.teamcitrus.citruslib.internal.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CitrusSignBlockEntity extends SignBlockEntity {
    public CitrusSignBlockEntity(BlockPos pos, BlockState blockState) {
        super(pos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return BlockEntityRegistry.CITRUS_SIGN_BLOCK.get();
    }
}
