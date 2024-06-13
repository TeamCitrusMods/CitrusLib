package dev.teamcitrus.citruslib.block;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.Map;

public class WoodSet {
    private static final Map<String, WoodSet> SET = new Object2ObjectArrayMap<>();
    public static final Codec<WoodSet> CODEC = ExtraCodecs.stringResolverCodec(WoodSet::getID, SET::get);

    private final String id;
    private final DeferredBlock<Block> planks;
    private final DeferredBlock<StairBlock> stairs;
    private final DeferredBlock<SlabBlock> slab;
    private final DeferredBlock<FenceBlock> fence;
    private final DeferredBlock<FenceGateBlock> fenceGate;
    private final DeferredBlock<DoorBlock> door;
    private final DeferredBlock<TrapDoorBlock> trapDoor;
    private final DeferredBlock<PressurePlateBlock> pressurePlate;
    private final DeferredBlock<ButtonBlock> button;

    private WoodSet(String id, DeferredBlock<Block> planks, DeferredBlock<StairBlock> stairs, DeferredBlock<SlabBlock> slab,
                   DeferredBlock<FenceBlock> fence, DeferredBlock<FenceGateBlock> fenceGate, DeferredBlock<DoorBlock> door,
                   DeferredBlock<TrapDoorBlock> trapDoor, DeferredBlock<PressurePlateBlock> pressurePlate, DeferredBlock<ButtonBlock> button
    ) {
        this.id = id;
        this.planks = planks;
        this.stairs = stairs;
        this.slab = slab;
        this.fence = fence;
        this.fenceGate = fenceGate;
        this.door = door;
        this.trapDoor = trapDoor;
        this.pressurePlate = pressurePlate;
        this.button = button;
    }

    private static WoodSet register(WoodSet woodSet) {
        SET.put(woodSet.getID(), woodSet);
        return woodSet;
    }

    public static WoodSet registerSetBlocks(DeferredRegister.Blocks register, String woodName) {
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
        return register(new WoodSet(woodName, planks, stairs, slab, fence, fenceGate,
                door, trapdoor, pressure_plate, button));
    }

    public static void registerSetItems(DeferredRegister.Items register, WoodSet woodSet) {
        register.registerSimpleBlockItem(woodSet.getPlanks());
        register.registerSimpleBlockItem(woodSet.getStairs());
        register.registerSimpleBlockItem(woodSet.getSlab());
        register.registerSimpleBlockItem(woodSet.getFence());
        register.registerSimpleBlockItem(woodSet.getFenceGate());
        register.registerSimpleBlockItem(woodSet.getDoor());
        register.registerSimpleBlockItem(woodSet.getTrapDoor());
        register.registerSimpleBlockItem(woodSet.getPressurePlate());
        register.registerSimpleBlockItem(woodSet.getButton());
    }

    public static Collection<WoodSet> values() {
        return SET.values();
    }

    public String getID() {
        return this.id;
    }

    public DeferredBlock<Block> getPlanks() {
        return this.planks;
    }

    public DeferredBlock<StairBlock> getStairs() {
        return this.stairs;
    }

    public DeferredBlock<SlabBlock> getSlab() {
        return this.slab;
    }

    public DeferredBlock<FenceBlock> getFence() {
        return this.fence;
    }

    public DeferredBlock<FenceGateBlock> getFenceGate() {
        return this.fenceGate;
    }

    public DeferredBlock<DoorBlock> getDoor() {
        return this.door;
    }

    public DeferredBlock<TrapDoorBlock> getTrapDoor() {
        return this.trapDoor;
    }

    public DeferredBlock<PressurePlateBlock> getPressurePlate() {
        return this.pressurePlate;
    }

    public DeferredBlock<ButtonBlock> getButton() {
        return this.button;
    }
}
