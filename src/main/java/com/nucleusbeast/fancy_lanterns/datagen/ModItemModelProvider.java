package com.nucleusbeast.fancy_lanterns.datagen;

import com.nucleusbeast.fancy_lanterns.FancyLanterns;
import com.nucleusbeast.fancy_lanterns.blocks.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FancyLanterns.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModBlocks.ALL_LANTERNS.forEach(this::lanternItem);
    }

    private void lanternItem(DeferredBlock<?> block) {
        String blockName = block.getId().getPath();

        if (blockName.contains("murky")){
            basicItem(block.asItem());
            return;
        }

        if (blockName.contains("_upgrade_") || blockName.startsWith("permanent_")) {
            withExistingParent(blockName, modLoc("block/" + blockName));
            return;
        }

        basicItem(block.asItem());
    }

    private ItemModelBuilder handheldItem(DeferredItem<?> item){
        return withExistingParent(item.getId().getPath(), ResourceLocation.parse("item/handheld"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(FancyLanterns.MODID,
                        "item/" + item.getId().getPath()));
    }
}
