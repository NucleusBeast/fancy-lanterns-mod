package com.nucleusbeast.fancy_lanterns.datagen;

import com.nucleusbeast.fancy_lanterns.FancyLanterns;
import com.nucleusbeast.fancy_lanterns.blocks.LanternStateProperties;
import com.nucleusbeast.fancy_lanterns.blocks.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.LanternBlock;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.CompositeModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    private static final String LANTERN_MARKER = "_lantern";

    private final ExistingFileHelper existingFileHelper;

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, FancyLanterns.MODID, exFileHelper);
        this.existingFileHelper = exFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.ALL_LANTERNS.forEach(this::lanternBlock);
    }

    private void lanternBlock(DeferredBlock<?> block) {
        String blockName = block.getId().getPath();
        String familyName = getFamilyName(blockName);
        ResourceLocation flameTexture = modLoc("block/lantern/flames/" + familyName);

        for (int level = LanternStateProperties.MIN_LEVEL;
             level <= LanternStateProperties.MAX_LEVEL;
             level++) {
            ResourceLocation borderTexture = getBorderTexture(familyName, level);
            String levelModelName = blockName + "_level_" + level;

            ModelFile standingModel = layeredLanternModel(
                    levelModelName,
                    modLoc("block/template_fancy_lantern"),
                    flameTexture,
                    borderTexture
            );
            ModelFile hangingModel = layeredLanternModel(
                    levelModelName + "_hanging",
                    modLoc("block/template_fancy_hanging_lantern"),
                    flameTexture,
                    borderTexture
            );

            getVariantBuilder(block.get())
                    .partialState()
                    .with(LanternBlock.HANGING, false)
                    .with(LanternStateProperties.LEVEL, level)
                    .modelForState()
                    .modelFile(standingModel)
                    .addModel()
                    .partialState()
                    .with(LanternBlock.HANGING, true)
                    .with(LanternStateProperties.LEVEL, level)
                    .modelForState()
                    .modelFile(hangingModel)
                    .addModel();
        }
    }

    private ResourceLocation getBorderTexture(String familyName, int level) {
        if (level == LanternStateProperties.MAX_LEVEL) {
            return modLoc("block/lantern/borders/level_4/" + familyName);
        }

        return modLoc("block/lantern/borders/level_" + level);
    }

    private ModelFile layeredLanternModel(
            String modelName,
            ResourceLocation template,
            ResourceLocation flameTexture,
            ResourceLocation borderTexture) {
        BlockModelBuilder model = models()
                .getBuilder(modelName)
                .texture("particle", borderTexture);

        BlockModelBuilder flameLayer = layerModel(modelName, "flame", template, flameTexture);
        BlockModelBuilder borderLayer = layerModel(modelName, "border", template, borderTexture);

        model.customLoader(CompositeModelBuilder::begin)
                .child("base_flame", flameLayer)
                .child("upgrade_border", borderLayer)
                .itemRenderOrder("base_flame", "upgrade_border")
                .end();

        return model;
    }

    private BlockModelBuilder layerModel(
            String modelName,
            String layerName,
            ResourceLocation template,
            ResourceLocation texture) {
        return new BlockModelBuilder(
                modLoc("block/" + modelName + "/" + layerName),
                existingFileHelper
        )
                .parent(models().getExistingFile(template))
                .renderType("minecraft:cutout")
                .texture("lantern", texture);
    }

    private static String getFamilyName(String blockName) {
        int lanternMarkerIndex = blockName.indexOf(LANTERN_MARKER);

        if (lanternMarkerIndex < 1) {
            throw new IllegalArgumentException("Cannot determine lantern family from block name: " + blockName);
        }

        return blockName.substring(0, lanternMarkerIndex);
    }
}
