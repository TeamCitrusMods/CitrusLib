package dev.teamcitrus.citruslib.datagen;

import dev.teamcitrus.citruslib.block.WoodSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public abstract class CitrusItemTagsProvider extends ItemTagsProvider {
    public CitrusItemTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, modId, existingFileHelper);
    }

    public TagKey<Item> modTag(String id) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(this.modId, id));
    }

    public TagKey<Item> commonTag(String id) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", id));
    }

    public TagKey<Item> modSupportTag(String modId, String id) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(modId, id));
    }

    public void generateSetTags(WoodSet woodSet) {
        tag(ItemTags.PLANKS).add(woodSet.getPlanks().asItem());
        tag(ItemTags.STAIRS).add(woodSet.getStairs().asItem());
        tag(ItemTags.WOODEN_STAIRS).add(woodSet.getStairs().asItem());
        tag(ItemTags.SLABS).add(woodSet.getSlab().asItem());
        tag(ItemTags.WOODEN_SLABS).add(woodSet.getSlab().asItem());
        tag(ItemTags.FENCES).add(woodSet.getFence().asItem());
        tag(ItemTags.WOODEN_FENCES).add(woodSet.getFence().asItem());
        tag(ItemTags.FENCE_GATES).add(woodSet.getFenceGate().asItem());
        tag(Tags.Items.FENCE_GATES_WOODEN).add(woodSet.getFenceGate().asItem());
        tag(ItemTags.DOORS).add(woodSet.getDoor().asItem());
        tag(ItemTags.WOODEN_DOORS).add(woodSet.getDoor().asItem());
        tag(ItemTags.TRAPDOORS).add(woodSet.getTrapDoor().asItem());
        tag(ItemTags.WOODEN_TRAPDOORS).add(woodSet.getTrapDoor().asItem());
        tag(ItemTags.WOODEN_PRESSURE_PLATES).add(woodSet.getPressurePlate().asItem());
        tag(ItemTags.BUTTONS).add(woodSet.getButton().asItem());
        tag(ItemTags.WOODEN_BUTTONS).add(woodSet.getButton().asItem());
    }
}
