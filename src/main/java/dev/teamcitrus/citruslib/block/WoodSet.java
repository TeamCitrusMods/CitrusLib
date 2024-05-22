package dev.teamcitrus.citruslib.block;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.*;

import java.util.Collection;
import java.util.Map;

public class WoodSet {
    private static final Map<String, WoodSet> SET = new Object2ObjectArrayMap<>();
    public static final Codec<WoodSet> CODEC = ExtraCodecs.stringResolverCodec(WoodSet::getID, SET::get);

    private final String id;
    private final Block planks;
    private final StairBlock stairs;
    private final SlabBlock slab;
    private final FenceBlock fence;
    private final FenceGateBlock fenceGate;
    private final DoorBlock door;
    private final TrapDoorBlock trapDoor;
    private final PressurePlateBlock pressurePlate;
    private final ButtonBlock button;

    public WoodSet(String id, Block planks, StairBlock stairs, SlabBlock slab,
                   FenceBlock fence, FenceGateBlock fenceGate, DoorBlock door,
                   TrapDoorBlock trapDoor, PressurePlateBlock pressurePlate, ButtonBlock button
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

    public static void register(WoodSet woodSet) {
        SET.put(woodSet.getID(), woodSet);
    }

    public static Collection<WoodSet> values() {
        return SET.values();
    }

    public String getID() {
        return this.id;
    }

    public Block getPlanks() {
        return this.planks;
    }

    public StairBlock getStairs() {
        return this.stairs;
    }

    public SlabBlock getSlab() {
        return this.slab;
    }

    public FenceBlock getFence() {
        return this.fence;
    }

    public FenceGateBlock getFenceGate() {
        return this.fenceGate;
    }

    public DoorBlock getDoor() {
        return this.door;
    }

    public TrapDoorBlock getTrapDoor() {
        return this.trapDoor;
    }

    public PressurePlateBlock getPressurePlate() {
        return this.pressurePlate;
    }

    public ButtonBlock getButton() {
        return this.button;
    }
}
