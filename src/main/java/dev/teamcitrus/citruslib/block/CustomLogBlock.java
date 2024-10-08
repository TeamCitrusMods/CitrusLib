package dev.teamcitrus.citruslib.block;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class CustomLogBlock extends RotatedPillarBlock {
    private final Supplier<? extends RotatedPillarBlock> stripResult;

    public CustomLogBlock(Supplier<? extends RotatedPillarBlock> stripResult, Properties properties) {
        super(properties);
        this.stripResult = stripResult;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (itemAbility.equals(ItemAbilities.AXE_STRIP)) {
            return stripResult.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
        }
        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }
}
