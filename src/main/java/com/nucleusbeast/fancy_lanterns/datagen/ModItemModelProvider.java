package com.nucleusbeast.fancy_lanterns.datagen;

import com.nucleusbeast.fancy_lanterns.FancyLanterns;
import com.nucleusbeast.fancy_lanterns.blocks.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FancyLanterns.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        // items
        basicItem(ModBlocks.MURKY_LANTERN.asItem());
        basicItem(ModBlocks.MURKY_LANTERN_UPGRADE_I.asItem());
        basicItem(ModBlocks.MURKY_LANTERN_UPGRADE_II.asItem());
        basicItem(ModBlocks.MURKY_LANTERN_PERMANENT.asItem());

        basicItem(ModBlocks.HEALTHY_LANTERN.asItem());
        basicItem(ModBlocks.HASTY_LANTERN.asItem());
        basicItem(ModBlocks.JUMPY_LANTERN.asItem());
        basicItem(ModBlocks.SPEEDY_LANTERN.asItem());
        basicItem(ModBlocks.SATURATY_LANTERN.asItem());
        basicItem(ModBlocks.STRENGTHY_LANTERN.asItem());
        basicItem(ModBlocks.BREATHY_LANTERN.asItem());
        basicItem(ModBlocks.FIERY_LANTERN.asItem());
        basicItem(ModBlocks.NIGHTY_LANTERN.asItem());
        basicItem(ModBlocks.LUCKY_LANTERN.asItem());
        basicItem(ModBlocks.ABSORBY_LANTERN.asItem());


//        handheldItem(ModItems.MAJESTIC_STICK.get());
    }

    private ItemModelBuilder handheldItem(DeferredItem<?> item){
        return withExistingParent(item.getId().getPath(), ResourceLocation.parse("item/handheld"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(FancyLanterns.MODID,
                        "item/" + item.getId().getPath()));
    }
}
