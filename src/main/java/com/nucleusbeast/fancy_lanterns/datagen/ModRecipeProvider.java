package com.nucleusbeast.fancy_lanterns.datagen;

import com.nucleusbeast.fancy_lanterns.blocks.ModBlocks;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredBlock;

//? if <26.3 {
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;
//?} else {
/*import net.minecraft.advancements.Advancement;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.crafting.Recipe;
*///?}
//? if <1.21.4 {
/*import net.minecraft.data.DataProvider;
*///?}
//? if <1.21.2 {
/*import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.level.ItemLike;
*///?}
//? if <26.2 {
import static net.minecraft.advancements.criterion.InventoryChangeTrigger.TriggerInstance.hasItems;
//?} else {
/*import static net.minecraft.advancements.triggers.InventoryChangeTrigger.TriggerInstance.hasItems;
*///?}

public class ModRecipeProvider extends RecipeProvider {

    //? if <1.21.2 {
    /*public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    static DataProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        return new ModRecipeProvider(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        build(output);
    }

    // Newer versions have this helper in RecipeProvider. It lets build() use the same code on all versions.
    private static ShapelessRecipeBuilder shapeless(RecipeCategory category, ItemLike result, int count) {
        return ShapelessRecipeBuilder.shapeless(category, result, count);
    }
    *///?} elif <26.3 {
    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        build(this.output);
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
    //?} else {
    /*// 26.3+: recipes are registry entries, added in DataGenerators.gatherRegistries
    public ModRecipeProvider(BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        super(recipes, advancements);
    }

    @Override
    protected void buildRecipes() {
        build(this.output);
    }
    *///?}

    //? if >=1.21.2 <1.21.4 {
    /*static DataProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        return new Runner(output, registries);
    }
    *///?}

    private void build(RecipeOutput output) {
        shapeless(RecipeCategory.MISC, ModBlocks.MURKY_LANTERN.get(), 1)
                .requires(Items.LANTERN)
                .requires(Items.WATER_BUCKET)
                .unlockedBy("has_lantern_item", hasItems(Items.LANTERN))
                .save(output);

        shapeless(RecipeCategory.MISC, ModBlocks.MURKY_LANTERN.get(), 2)
                .requires(Items.LANTERN)
                .requires(ModBlocks.MURKY_LANTERN.get())
                .unlockedBy("has_lantern_item_and_murky_one", hasItems(Items.LANTERN, ModBlocks.MURKY_LANTERN.get()))
                .save(output, "fancy_lanterns:bulk_crafting");

        relightRecipe(output, ModBlocks.HEALTHY_LANTERN, Items.GOLDEN_APPLE);
        relightRecipe(output, ModBlocks.STRENGTHY_LANTERN, Items.IRON_SWORD);
        relightRecipe(output, ModBlocks.ABSORBY_LANTERN, Items.GOLD_BLOCK);
        relightRecipe(output, ModBlocks.SPEEDY_LANTERN, Items.IRON_BOOTS);
        relightRecipe(output, ModBlocks.JUMPY_LANTERN, Items.RABBIT_HIDE);
        relightRecipe(output, ModBlocks.NIGHTY_LANTERN, Items.SOUL_TORCH);
        relightRecipe(output, ModBlocks.LUCKY_LANTERN, Items.RABBIT_FOOT);
        relightRecipe(output, ModBlocks.HASTY_LANTERN, Items.IRON_PICKAXE);
        relightRecipe(output, ModBlocks.SATURATY_LANTERN, Items.APPLE);
        relightRecipe(output, ModBlocks.FIERY_LANTERN, Items.FLINT_AND_STEEL);
        relightRecipe(output, ModBlocks.BREATHY_LANTERN, Items.NAUTILUS_SHELL);
    }

    private void relightRecipe(RecipeOutput output, DeferredBlock<?> lantern, Item material) {
        shapeless(RecipeCategory.MISC, lantern.get(), 1)
                .requires(ModBlocks.MURKY_LANTERN.get())
                .requires(material)
                .unlockedBy("has_murky_lantern_item", hasItems(ModBlocks.MURKY_LANTERN.get()))
                .save(output);
    }
}
