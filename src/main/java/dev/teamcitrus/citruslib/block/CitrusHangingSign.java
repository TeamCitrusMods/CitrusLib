package dev.teamcitrus.citruslib.block;

import dev.teamcitrus.citruslib.block.entity.CitrusHangingSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class CitrusHangingSign {
    public static class CitrusCeilingHangingSignBlock extends CeilingHangingSignBlock {
        public CitrusCeilingHangingSignBlock(WoodType woodType, Properties properties) {
            super(woodType, properties);
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
            return new CitrusHangingSignBlockEntity(pPos, pState);
        }
    }

    public static class CitrusWallHangingSignBlock extends WallHangingSignBlock {
        public CitrusWallHangingSignBlock(WoodType woodType, Properties properties) {
            super(woodType, properties);
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
            return new CitrusHangingSignBlockEntity(pPos, pState);
        }
    }
}
