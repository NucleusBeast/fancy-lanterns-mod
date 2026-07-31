package com.nucleusbeast.fancy_lanterns.datagen;

import com.nucleusbeast.fancy_lanterns.FancyLanterns;
import com.nucleusbeast.fancy_lanterns.blocks.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.LanternBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, FancyLanterns.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        lanternBlock(ModBlocks.MURKY_LANTERN);
        lanternBlock(ModBlocks.MURKY_LANTERN_UPGRADE_I);
        lanternBlock(ModBlocks.MURKY_LANTERN_UPGRADE_II);
        lanternBlock(ModBlocks.MURKY_LANTERN_PERMANENT);

        lanternBlock(ModBlocks.HEALTHY_LANTERN);
        lanternBlock(ModBlocks.HASTY_LANTERN);
    }

    private void blockWithItem(DeferredBlock<?> block){
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    private void lanternBlock(DeferredBlock<?> block) {
        String blockName = block.getId().getPath();
        ModelFile standingModel = models()
                .withExistingParent(blockName, modLoc("block/template_fancy_lantern"))
                .texture("lantern", modLoc("block/" + blockName));
        ModelFile hangingModel = models()
                .withExistingParent(blockName + "_hanging", modLoc("block/template_fancy_hanging_lantern"))
                .texture("lantern", modLoc("block/" + blockName));

        getVariantBuilder(block.get())
                .partialState()
                .with(LanternBlock.HANGING, false)
                .modelForState()
                .modelFile(standingModel)
                .addModel()
                .partialState()
                .with(LanternBlock.HANGING, true)
                .modelForState()
                .modelFile(hangingModel)
                .addModel();
    }
}
