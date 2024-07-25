package dev.teamcitrus.citruslib.datagen;

import dev.teamcitrus.citruslib.block.WoodSet;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Set;

public class CitrusBlockDropProvider extends BlockLootSubProvider {
    private final Set<Block> knownBlocks = new ReferenceOpenHashSet<>();

    protected CitrusBlockDropProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {

    }

    public void generateSetDrops(WoodSet woodSet) {
        dropSelf(woodSet.getPlanks().get());
        dropSelf(woodSet.getStairs().get());
        dropSelf(woodSet.getSlab().get());
        dropSelf(woodSet.getFence().get());
        dropSelf(woodSet.getFenceGate().get());
        add(woodSet.getDoor().get(), createDoorTable(woodSet.getDoor().get()));
        dropSelf(woodSet.getTrapDoor().get());
        dropSelf(woodSet.getPressurePlate().get());
        dropSelf(woodSet.getButton().get());
    }

    @Override
    protected void dropSelf(Block pBlock) {
        super.dropSelf(pBlock);
        knownBlocks.add(pBlock);
    }

    @Override
    protected void add(Block pBlock, LootTable.Builder pBuilder) {
        super.add(pBlock, pBuilder);
        knownBlocks.add(pBlock);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }
}
