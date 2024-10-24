package dev.teamcitrus.citruslib.datagen;

import dev.teamcitrus.citruslib.block.WoodSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public abstract class CitrusItemModelProvider extends ItemModelProvider {
    public CitrusItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    public void blockItemWithCustomModel(Block block, String model) {
        withExistingParent(BuiltInRegistries.BLOCK.getKey(block).getPath(), modLoc("block/" + model));
    }

    public void generateSetModels(WoodSet woodSet) {
        simpleBlockItem(woodSet.getPlanks().get());
        simpleBlockItem(woodSet.getStairs().get());
        simpleBlockItem(woodSet.getSlab().get());
        fenceInventory(woodSet.getID() + "_fence", modLoc("block/" + woodSet.getID() +"_planks"));
        simpleBlockItem(woodSet.getFenceGate().get());
        basicItem(woodSet.getDoor().asItem());
        blockItemWithCustomModel(woodSet.getTrapDoor().get(),  woodSet.getID() + "_trapdoor_bottom");
        simpleBlockItem(woodSet.getPressurePlate().get());
        buttonInventory(woodSet.getID() + "_button", modLoc("block/" + woodSet.getID() + "_planks"));
    }
}
