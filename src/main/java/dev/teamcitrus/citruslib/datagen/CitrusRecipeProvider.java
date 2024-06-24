package dev.teamcitrus.citruslib.datagen;

import dev.teamcitrus.citruslib.block.WoodSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public abstract class CitrusRecipeProvider extends RecipeProvider {
    public CitrusRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    public void generateWoodSetRecipes(RecipeOutput recipeOutput, WoodSet woodSet) {
        ShapedRecipeBuilder.shaped(
                RecipeCategory.BUILDING_BLOCKS, woodSet.getStairs(), 4
        ).define('#', woodSet.getPlanks())
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .group("wooden_stairs")
                .unlockedBy("has_item", has(woodSet.getPlanks()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(
                RecipeCategory.BUILDING_BLOCKS, woodSet.getSlab(), 6
        ).define('#', woodSet.getPlanks())
                .pattern("###")
                .group("wooden_slab")
                .unlockedBy("has_item", has(woodSet.getPlanks()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(
                RecipeCategory.MISC, woodSet.getFence(), 4
        ).define('#', Items.STICK).define('W', woodSet.getPlanks())
                .pattern("W#W")
                .pattern("W#W")
                .group("wooden_fence")
                .unlockedBy("has_item", has(woodSet.getPlanks()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(
                        RecipeCategory.REDSTONE, woodSet.getFenceGate()
                ).define('#', Items.STICK).define('W', woodSet.getPlanks())
                .pattern("#W#")
                .pattern("#W#")
                .group("wooden_fence_gate")
                .unlockedBy("has_item", has(woodSet.getPlanks()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(
                RecipeCategory.REDSTONE, woodSet.getDoor(), 3
        ).define('#', woodSet.getPlanks())
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .group("wooden_door")
                .unlockedBy("has_item", has(woodSet.getPlanks()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(
                RecipeCategory.REDSTONE, woodSet.getTrapDoor(), 2
        ).define('#', woodSet.getPlanks())
                .pattern("###")
                .pattern("###")
                .group("wooden_trapdoor")
                .unlockedBy("has_item", has(woodSet.getPlanks()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(
                RecipeCategory.REDSTONE, woodSet.getPressurePlate()
        ).define('#', woodSet.getPlanks())
                .pattern("##")
                .group("wooden_pressure_plate")
                .unlockedBy("has_item", has(woodSet.getPlanks()))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(
                RecipeCategory.REDSTONE, woodSet.getButton()
        ).requires(woodSet.getPlanks())
                .group("wooden_button")
                .unlockedBy("has_item", has(woodSet.getPlanks()))
                .save(recipeOutput);
    }
}
