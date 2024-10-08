package dev.teamcitrus.citruslib.datagen;

import dev.teamcitrus.citruslib.block.WoodSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public abstract class CitrusBlockTagsProvider extends BlockTagsProvider {
    public CitrusBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    public TagKey<Block> modTag(String id) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath(this.modId, id));
    }

    public TagKey<Block> commonTag(String id) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", id));
    }

    public TagKey<Block> modSupportTag(String modId, String id) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath(modId, id));
    }

    public void generateSetTags(WoodSet woodSet) {
        tag(BlockTags.PLANKS).add(woodSet.getPlanks().get());
        tag(BlockTags.STAIRS).add(woodSet.getStairs().get());
        tag(BlockTags.WOODEN_STAIRS).add(woodSet.getStairs().get());
        tag(BlockTags.SLABS).add(woodSet.getSlab().get());
        tag(BlockTags.WOODEN_SLABS).add(woodSet.getSlab().get());
        tag(BlockTags.FENCES).add(woodSet.getFence().get());
        tag(BlockTags.WOODEN_FENCES).add(woodSet.getFence().get());
        tag(BlockTags.FENCE_GATES).add(woodSet.getFenceGate().get());
        tag(Tags.Blocks.FENCE_GATES_WOODEN).add(woodSet.getFenceGate().get());
        tag(BlockTags.DOORS).add(woodSet.getDoor().get());
        tag(BlockTags.WOODEN_DOORS).add(woodSet.getDoor().get());
        tag(BlockTags.TRAPDOORS).add(woodSet.getTrapDoor().get());
        tag(BlockTags.WOODEN_TRAPDOORS).add(woodSet.getTrapDoor().get());
        tag(BlockTags.PRESSURE_PLATES).add(woodSet.getPressurePlate().get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(woodSet.getPressurePlate().get());
        tag(BlockTags.BUTTONS).add(woodSet.getButton().get());
        tag(BlockTags.WOODEN_BUTTONS).add(woodSet.getButton().get());
        tag(BlockTags.MINEABLE_WITH_AXE).add(woodSet.getPlanks().get(), woodSet.getSlab().get(), woodSet.getStairs().get(),
                woodSet.getFence().get(), woodSet.getFenceGate().get(), woodSet.getDoor().get(), woodSet.getTrapDoor().get(),
                woodSet.getPressurePlate().get(), woodSet.getButton().get()
        );
    }
}
