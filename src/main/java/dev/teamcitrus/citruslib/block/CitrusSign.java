package dev.teamcitrus.citruslib.block;

import dev.teamcitrus.citruslib.block.entity.CitrusSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class CitrusSign {
    public static class CitrusStandingSignBlock extends StandingSignBlock {
        public CitrusStandingSignBlock(WoodType type, Properties properties) {
            super(type, properties);
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
            return new CitrusSignBlockEntity(pPos, pState);
        }
    }

    public static class CitrusWallSignBlock extends WallSignBlock {
        public CitrusWallSignBlock(WoodType type, Properties properties) {
            super(type, properties);
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
            return new CitrusSignBlockEntity(pPos, pState);
        }
    }
}
