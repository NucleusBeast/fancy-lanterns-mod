package com.nucleusbeast.fancy_lanterns.datagen;

import com.nucleusbeast.fancy_lanterns.blocks.ModBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems;

public class ModRecipeProvider extends RecipeProvider {

    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new ModRecipeProvider(provider, recipeOutput);
        }

        @Override
        public String getName() {
            return "Fancy Lanterns Recipes";
        }
    }

    @Override
    protected void buildRecipes() {
        shapeless(RecipeCategory.MISC, ModBlocks.MURKY_LANTERN.get(), 1)
                .requires(Items.LANTERN)
                .requires(Items.WATER_BUCKET)
                .unlockedBy("has_lantern_item", hasItems(Items.LANTERN))
                .save(this.output);

        shapeless(RecipeCategory.MISC, ModBlocks.MURKY_LANTERN.get(), 2)
                .requires(Items.LANTERN)
                .requires(ModBlocks.MURKY_LANTERN.get())
                .unlockedBy("has_lantern_item_and_murky_one", hasItems(Items.LANTERN, ModBlocks.MURKY_LANTERN.get()))
                .save(this.output, "fancy_lanterns:bulk_crafting");

        HolderGetter<Item> itemLookup = this.registries.lookupOrThrow(Registries.ITEM);
        relightRecipe(this.output, itemLookup, ModBlocks.HEALTHY_LANTERN, Items.GOLDEN_APPLE);
        relightRecipe(this.output, itemLookup, ModBlocks.STRENGTHY_LANTERN, Items.IRON_SWORD);
        relightRecipe(this.output, itemLookup, ModBlocks.ABSORBY_LANTERN, Items.GOLD_BLOCK);
        relightRecipe(this.output, itemLookup, ModBlocks.SPEEDY_LANTERN, Items.IRON_BOOTS);
        relightRecipe(this.output, itemLookup, ModBlocks.JUMPY_LANTERN, Items.RABBIT_HIDE);
        relightRecipe(this.output, itemLookup, ModBlocks.NIGHTY_LANTERN, Items.SOUL_TORCH);
        relightRecipe(this.output, itemLookup, ModBlocks.LUCKY_LANTERN, Items.RABBIT_FOOT);
        relightRecipe(this.output, itemLookup, ModBlocks.HASTY_LANTERN, Items.IRON_PICKAXE);
        relightRecipe(this.output, itemLookup, ModBlocks.SATURATY_LANTERN, Items.APPLE);
        relightRecipe(this.output, itemLookup, ModBlocks.FIERY_LANTERN, Items.FLINT_AND_STEEL);
        relightRecipe(this.output, itemLookup, ModBlocks.BREATHY_LANTERN, Items.NAUTILUS_SHELL);
    }

    private static void relightRecipe(
            RecipeOutput recipeOutput,
            HolderGetter<Item> itemLookup,
            net.neoforged.neoforge.registries.DeferredBlock<?> lantern,
            Item material) {
        ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.MISC, lantern.get())
                .requires(ModBlocks.MURKY_LANTERN.get())
                .requires(material)
                .unlockedBy("has_murky_lantern_item", hasItems(ModBlocks.MURKY_LANTERN.get()))
                .save(recipeOutput);
    }
}
