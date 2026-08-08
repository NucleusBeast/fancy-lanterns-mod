package com.nucleusbeast.fancy_lanterns.datagen;

import com.nucleusbeast.fancy_lanterns.FancyLanterns;
import com.nucleusbeast.fancy_lanterns.blocks.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FancyLanterns.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModBlocks.ALL_LANTERNS.forEach(this::lanternItem);
    }

    private void lanternItem(DeferredBlock<?> block) {
        basicItem(block.asItem());
    }
}
