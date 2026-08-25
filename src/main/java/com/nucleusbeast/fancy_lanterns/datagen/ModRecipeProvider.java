package com.nucleusbeast.fancy_lanterns.datagen;

import com.nucleusbeast.fancy_lanterns.blocks.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.MURKY_LANTERN.get(), 1)
                .requires(Items.LANTERN)
                .requires(Items.WATER_BUCKET)
                .unlockedBy("has_lantern_item", hasItems(Items.LANTERN))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.MURKY_LANTERN.get(), 2)
                .requires(Items.LANTERN)
                .requires(ModBlocks.MURKY_LANTERN.get())
                .unlockedBy("has_lantern_item_and_murky_one", hasItems(Items.LANTERN, ModBlocks.MURKY_LANTERN.get()))
                .save(recipeOutput, "fancy_lanterns:bulk_crafting");

        relightRecipe(recipeOutput, ModBlocks.HEALTHY_LANTERN, Items.GOLDEN_APPLE);
        relightRecipe(recipeOutput, ModBlocks.STRENGTHY_LANTERN, Items.IRON_SWORD);
        relightRecipe(recipeOutput, ModBlocks.ABSORBY_LANTERN, Items.GOLD_BLOCK);
        relightRecipe(recipeOutput, ModBlocks.SPEEDY_LANTERN, Items.IRON_BOOTS);
        relightRecipe(recipeOutput, ModBlocks.JUMPY_LANTERN, Items.RABBIT_HIDE);
        relightRecipe(recipeOutput, ModBlocks.NIGHTY_LANTERN, Items.SOUL_TORCH);
        relightRecipe(recipeOutput, ModBlocks.LUCKY_LANTERN, Items.RABBIT_FOOT);
        relightRecipe(recipeOutput, ModBlocks.HASTY_LANTERN, Items.IRON_PICKAXE);
        relightRecipe(recipeOutput, ModBlocks.SATURATY_LANTERN, Items.APPLE);
        relightRecipe(recipeOutput, ModBlocks.FIERY_LANTERN, Items.FLINT_AND_STEEL);
        relightRecipe(recipeOutput, ModBlocks.BREATHY_LANTERN, Items.NAUTILUS_SHELL);
    }

    private static void relightRecipe(
            RecipeOutput recipeOutput,
            net.neoforged.neoforge.registries.DeferredBlock<?> lantern,
            net.minecraft.world.item.Item material) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, lantern.get(), 1)
                .requires(ModBlocks.MURKY_LANTERN.get())
                .requires(material)
                .unlockedBy("has_murky_lantern_item", hasItems(ModBlocks.MURKY_LANTERN.get()))
                .save(recipeOutput);
    }
}
