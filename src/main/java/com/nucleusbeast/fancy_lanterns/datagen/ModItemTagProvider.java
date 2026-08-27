package com.nucleusbeast.fancy_lanterns.datagen;

import com.nucleusbeast.fancy_lanterns.FancyLanterns;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    private static final TagKey<Item> UPGRADE_LEVEL_1 = tag("lantern_upgrade_level_1");
    private static final TagKey<Item> UPGRADE_LEVEL_2 = tag("lantern_upgrade_level_2");
    private static final TagKey<Item> UPGRADE_LEVEL_3 = tag("lantern_upgrade_level_3");

    public ModItemTagProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, CompletableFuture.completedFuture(null),
                FancyLanterns.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(UPGRADE_LEVEL_1)
                .add(Items.IRON_INGOT)
                .add(Items.IRON_BLOCK);
        tag(UPGRADE_LEVEL_2)
                .add(Items.GOLD_INGOT)
                .add(Items.IRON_BLOCK);
        tag(UPGRADE_LEVEL_3)
                .add(Items.NETHER_STAR)
                .add(Items.NETHERITE_SCRAP)
                .addOptional(ResourceLocation.parse("useless_things:nucleus_core"));

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
        tag(tag("relights/" + lantern)).add(item);
    }

    private static TagKey<Item> tag(String path) {
        return TagKey.create(net.minecraft.core.registries.Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath(FancyLanterns.MODID, path));
    }
}
