package dev.teamcitrus.citruslib.block;

import com.mojang.serialization.Codec;
import dev.teamcitrus.citruslib.registry.WoodSetRegistry;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.ApiStatus;

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

    /**
     * To create a WoodSet, use {@link WoodSetRegistry}
     */
    @ApiStatus.Internal
    public WoodSet(String id, DeferredBlock<Block> planks, DeferredBlock<StairBlock> stairs, DeferredBlock<SlabBlock> slab,
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

    public static WoodSet register(WoodSet woodSet) {
        SET.put(woodSet.getID(), woodSet);
        return woodSet;
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
