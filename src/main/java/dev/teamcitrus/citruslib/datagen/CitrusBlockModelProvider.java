package dev.teamcitrus.citruslib.datagen;

import dev.teamcitrus.citruslib.block.WoodSet;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public abstract class CitrusBlockModelProvider extends BlockStateProvider {
    public CitrusBlockModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    public void generateSetModels(WoodSet woodSet) {
        Block planks = woodSet.getPlanks().get();
        simpleBlock(planks);
        stairsBlock(woodSet.getStairs().get(), blockTexture(planks));
        slabBlock(woodSet.getSlab().get(), blockTexture(planks), blockTexture(planks));
        fenceBlock(woodSet.getFence().get(), blockTexture(planks));
        fenceGateBlock(woodSet.getFenceGate().get(), blockTexture(planks));
        doorBlockWithRenderType(woodSet.getDoor().get(), modLoc("block/" + woodSet.getID() + "_door_bottom"), modLoc("block/" + woodSet.getID() + "_door_top"), "cutout");
        trapdoorBlockWithRenderType(woodSet.getTrapDoor().get(), modLoc("block/" + woodSet.getID() + "_trapdoor"), true, "cutout");
        pressurePlateBlock(woodSet.getPressurePlate().get(), blockTexture(planks));
        buttonBlock(woodSet.getButton().get(), blockTexture(planks));
    }
}
