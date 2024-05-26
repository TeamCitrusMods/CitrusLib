package dev.teamcitrus.citruslib.datagen;

import dev.teamcitrus.citruslib.block.WoodSet;
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

    public void toBlock(Block block) {
        toBlockCustomModel(block, BuiltInRegistries.BLOCK.getKey(block).getPath());
    }

    public void toBlockCustomModel(Block block, String model) {
        withExistingParent(BuiltInRegistries.BLOCK.getKey(block).getPath(), modLoc("block/" + model));
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

    public void generateWoodSetItems(WoodSet woodSet) {
        toBlock(woodSet.getPlanks().get());
        toBlock(woodSet.getStairs().get());
        toBlock(woodSet.getSlab().get());
        fenceInventory(woodSet.getID() + "_fence", modLoc("block/" + woodSet.getID() +"_planks"));
        toBlock(woodSet.getFenceGate().get());
        basicItem(woodSet.getDoor().get().asItem());
        toBlockCustomModel(woodSet.getTrapDoor().get(),  woodSet.getID() + "_trapdoor_bottom");
        toBlock(woodSet.getPressurePlate().get());
        buttonInventory(woodSet.getID() + "_button", modLoc("block/" + woodSet.getID() + "_planks"));
    }
}
