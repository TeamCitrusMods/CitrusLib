package dev.teamcitrus.citruslib.brewing;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public record BrewingRecipe(Ingredient base, Ingredient reagent, ItemStack output) {
    public static final Codec<BrewingRecipe> CODEC = RecordCodecBuilder.create(func -> func.group(
            Ingredient.CODEC.fieldOf("base").forGetter(BrewingRecipe::base),
            Ingredient.CODEC.fieldOf("input").forGetter(BrewingRecipe::reagent),
            ItemStack.CODEC.fieldOf("result").forGetter(BrewingRecipe::output)
    ).apply(func, BrewingRecipe::new));
}
