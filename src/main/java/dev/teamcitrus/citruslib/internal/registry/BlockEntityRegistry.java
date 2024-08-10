package dev.teamcitrus.citruslib.internal.registry;

import dev.teamcitrus.citruslib.CitrusLib;
import dev.teamcitrus.citruslib.block.CitrusHangingSign;
import dev.teamcitrus.citruslib.block.CitrusSign;
import dev.teamcitrus.citruslib.block.entity.CitrusHangingSignBlockEntity;
import dev.teamcitrus.citruslib.block.entity.CitrusSignBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CitrusLib.MODID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CitrusSignBlockEntity>> CITRUS_SIGN_BLOCK = BLOCK_ENTITIES.register("sign", () -> BlockEntityType.Builder.of(CitrusSignBlockEntity::new, getBlocks(CitrusSign.CitrusStandingSignBlock.class, CitrusSign.CitrusWallSignBlock.class)).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CitrusHangingSignBlockEntity>> CITRUS_HANGING_SIGN = BLOCK_ENTITIES.register("hanging_sign", () -> BlockEntityType.Builder.of(CitrusHangingSignBlockEntity::new, getBlocks(CitrusHangingSign.CitrusCeilingHangingSignBlock.class, CitrusHangingSign.CitrusWallHangingSignBlock.class)).build(null));

    public static Block[] getBlocks(Class<?>... blockClasses) {
        Registry<Block> blocks = BuiltInRegistries.BLOCK;
        List<Block> matchingBlocks = new ArrayList<>();
        for (Block block : blocks) {
            if (Arrays.stream(blockClasses).anyMatch(b -> b.isInstance(block))) {
                matchingBlocks.add(block);
            }
        }
        return matchingBlocks.toArray(new Block[0]);
    }
}
