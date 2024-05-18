package dev.teamcitrus.citruslib.datagen;

import dev.teamcitrus.citruslib.CitrusLib;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public abstract class CitrusItemModelProvider extends ItemModelProvider {
    public CitrusItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    public void blockItem(Block block) {
        withExistingParent(BuiltInRegistries.BLOCK.getKey(block).getPath(),
                CitrusLib.modLoc("block/" + BuiltInRegistries.BLOCK.getKey(block).getPath())
        );
    }

    public void spawnEggItem(Item item) {
        getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("minecraft:item/template_spawn_egg"));
    }

    public void handheldItem(Item item) {
        getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", new ResourceLocation(BuiltInRegistries.ITEM.getKey(item).getNamespace(), "item/" + BuiltInRegistries.ITEM.getKey(item).getPath()));
    }
}
