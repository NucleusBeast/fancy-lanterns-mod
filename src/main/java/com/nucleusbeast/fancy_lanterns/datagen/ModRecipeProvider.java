package com.nucleusbeast.fancy_lanterns.datagen;

import com.nucleusbeast.fancy_lanterns.blocks.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
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

//        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.SIMPLE_GENERATOR.get())
//                .pattern("ORO")
//                .pattern("ENE")
//                .pattern("ORO")
//                .define('O', Items.OBSIDIAN)
//                .define('R', Items.REDSTONE)
//                .define('E', Items.EMERALD)
//                .define('N', ModItems.NUCLEUS_CORE.get())
//                .unlockedBy("has_obsidian_redstone_emerald_nucleus_pearl", hasItems(Items.REDSTONE, Items.OBSIDIAN, Items.EMERALD, ModItems.NUCLEUS_CORE.get()))
//                .save(recipeOutput);
//
//        // Second recipe for same item with different ingredients
//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.NUCLEUS_CORE.get(), 1)
//                .requires(Items.ENDER_PEARL)
//                .requires(Items.CLAY_BALL)
//                .unlockedBy("has_ender_pearl_gun_powder", hasItems(Items.ENDER_PEARL, Items.CLAY_BALL))
//                .save(recipeOutput, "useless_things:nucleus_core_from_ender_pearl_and_clay_ball");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.MURKY_LANTERN.get(), 1)
                .requires(Items.LANTERN)
                .requires(Items.WATER_BUCKET)
                .unlockedBy("has_lantern_item", hasItems(Items.LANTERN))
                .save(recipeOutput);
    }
}
