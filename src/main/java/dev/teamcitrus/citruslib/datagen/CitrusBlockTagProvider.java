package dev.teamcitrus.citruslib.datagen;

import dev.teamcitrus.citruslib.block.WoodSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public abstract class CitrusBlockTagProvider extends BlockTagsProvider {
    public CitrusBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    public void generateWoodTags(WoodSet woodSet) {
        tag(BlockTags.PLANKS).add(woodSet.getPlanks());
        tag(BlockTags.STAIRS).add(woodSet.getStairs());
        tag(BlockTags.WOODEN_STAIRS).add(woodSet.getStairs());
        tag(BlockTags.SLABS).add(woodSet.getSlab());
        tag(BlockTags.WOODEN_SLABS).add(woodSet.getSlab());
        tag(BlockTags.FENCES).add(woodSet.getFence());
        tag(BlockTags.WOODEN_FENCES).add(woodSet.getFence());
        tag(BlockTags.FENCE_GATES).add(woodSet.getFenceGate());
        tag(Tags.Blocks.FENCE_GATES_WOODEN).add(woodSet.getFenceGate());
        tag(BlockTags.DOORS).add(woodSet.getDoor());
        tag(BlockTags.WOODEN_DOORS).add(woodSet.getDoor());
        tag(BlockTags.TRAPDOORS).add(woodSet.getTrapDoor());
        tag(BlockTags.WOODEN_TRAPDOORS).add(woodSet.getTrapDoor());
        tag(BlockTags.PRESSURE_PLATES).add(woodSet.getPressurePlate());
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(woodSet.getPressurePlate());
        tag(BlockTags.BUTTONS).add(woodSet.getButton());
        tag(BlockTags.WOODEN_BUTTONS).add(woodSet.getButton());
        tag(BlockTags.MINEABLE_WITH_AXE).add(woodSet.getPlanks(), woodSet.getSlab(), woodSet.getStairs(),
                woodSet.getFence(), woodSet.getFenceGate(), woodSet.getDoor(), woodSet.getTrapDoor(),
                woodSet.getPressurePlate(), woodSet.getButton()
        );
    }
}
