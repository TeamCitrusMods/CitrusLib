package dev.teamcitrus.citruslib.registry;

import dev.teamcitrus.citruslib.block.WoodSet;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class WoodSetRegistry {
    public static void registerWoodSet(DeferredRegister.Blocks register, String woodName) {
        BlockSetType blockType = new BlockSetType(woodName);
        WoodType woodType = new WoodType(woodName, blockType);
        DeferredBlock<Block> planks = register.register(woodName + "_planks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
        DeferredBlock<StairBlock> stairs = register.register(woodName + "_stairs", () -> new StairBlock(planks.get()::defaultBlockState, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS)));
        DeferredBlock<SlabBlock> slab = register.register(woodName + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)));
        DeferredBlock<FenceBlock> fence = register.register(woodName + "_fence", () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)));
        DeferredBlock<FenceGateBlock> fenceGate = register.register(woodName + "_fence_gate", () -> new FenceGateBlock(woodType, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)));
        DeferredBlock<DoorBlock> door = register.register(woodName + "_door", () -> new DoorBlock(blockType, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)));
        DeferredBlock<TrapDoorBlock> trapdoor = register.register(woodName + "_trapdoor", () -> new TrapDoorBlock(blockType, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)));
        DeferredBlock<PressurePlateBlock> pressure_plate = register.register(woodName + "_pressure_plate", () -> new PressurePlateBlock(blockType, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));
        DeferredBlock<ButtonBlock> button = register.register(woodName + "_button", () -> new ButtonBlock(blockType, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON)));
        WoodSet.register(new WoodSet(woodName, planks, stairs, slab, fence, fenceGate,
                door, trapdoor, pressure_plate, button));
    }
}
