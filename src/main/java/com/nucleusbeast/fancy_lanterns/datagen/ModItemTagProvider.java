package com.nucleusbeast.fancy_lanterns.datagen;

import com.nucleusbeast.fancy_lanterns.FancyLanterns;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

//? if <1.21.6 {
/*import net.minecraft.data.tags.ItemTagsProvider;
*///?} else {
import net.neoforged.neoforge.common.data.ItemTagsProvider;
//?}
//? if <1.21.4 {
/*import net.neoforged.neoforge.common.data.ExistingFileHelper;
*///?}
//? if >=26.2 {
/*import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
*///?}

public class ModItemTagProvider extends ItemTagsProvider {
    private static final TagKey<Item> UPGRADE_LEVEL_1 = tag("lantern_upgrade_level_1");
    private static final TagKey<Item> UPGRADE_LEVEL_2 = tag("lantern_upgrade_level_2");
    private static final TagKey<Item> UPGRADE_LEVEL_3 = tag("lantern_upgrade_level_3");
    private static final Identifier NUCLEUS_CORE = Identifier.parse("useless_things:nucleus_core");

    //? if <1.21.4 {
    /*public ModItemTagProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CompletableFuture.completedFuture(null),
                FancyLanterns.MODID, existingFileHelper);
    }
    *///?} elif <1.21.6 {
    /*public ModItemTagProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, CompletableFuture.completedFuture(null),
                FancyLanterns.MODID);
    }
    *///?} else {
    public ModItemTagProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider,
                FancyLanterns.MODID);
    }
    //?}

    // Tag entries are items before 26.2 and registry keys from 26.2
    //? if <26.2 {
    private static Item entry(Item item) {
        return item;
    }
    //?} else {
    /*private static ResourceKey<Item> entry(Item item) {
        return BuiltInRegistries.ITEM.getResourceKey(item).orElseThrow();
    }
    *///?}

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(UPGRADE_LEVEL_1)
                .add(entry(Items.IRON_INGOT))
                .add(entry(Items.IRON_BLOCK));
        tag(UPGRADE_LEVEL_2)
                .add(entry(Items.GOLD_INGOT))
                .add(entry(Items.IRON_BLOCK));
        tag(UPGRADE_LEVEL_3)
                .add(entry(Items.NETHER_STAR))
                .add(entry(Items.NETHERITE_SCRAP));
        // Nucleus core is from the Useless Things mod. The entry is optional, so the tag also loads without that mod.
        //? if <1.21.6 {
        /*tag(UPGRADE_LEVEL_3).addOptional(NUCLEUS_CORE);
         *///?} else {
        getOrCreateRawBuilder(UPGRADE_LEVEL_3).addOptionalElement(NUCLEUS_CORE);
        //?}

        relight("healthy_lantern", Items.GOLDEN_APPLE);
        relight("strengthy_lantern", Items.IRON_SWORD);
        relight("absorby_lantern", Items.GOLD_BLOCK);
        relight("speedy_lantern", Items.IRON_BOOTS);
        relight("jumpy_lantern", Items.RABBIT_HIDE);
        relight("nighty_lantern", Items.SOUL_TORCH);
        relight("lucky_lantern", Items.RABBIT_FOOT);
        relight("hasty_lantern", Items.IRON_PICKAXE);
        relight("saturaty_lantern", Items.APPLE);
        relight("fiery_lantern", Items.FLINT_AND_STEEL);
        relight("breathy_lantern", Items.NAUTILUS_SHELL);
    }

    private void relight(String lantern, Item item) {
        tag(tag("relights/" + lantern)).add(entry(item));
    }

    private static TagKey<Item> tag(String path) {
        return TagKey.create(net.minecraft.core.registries.Registries.ITEM,
                Identifier.fromNamespaceAndPath(FancyLanterns.MODID, path));
    }
}
