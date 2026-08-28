package com.nucleusbeast.fancy_lanterns.datagen;

import com.nucleusbeast.fancy_lanterns.FancyLanterns;
import com.nucleusbeast.fancy_lanterns.ModItems;
import com.nucleusbeast.fancy_lanterns.blocks.LanternStateProperties;
import com.nucleusbeast.fancy_lanterns.blocks.ModBlocks;
import com.nucleusbeast.fancy_lanterns.blocks.fancy_lantern.FancyLanternBlock;
import com.nucleusbeast.fancy_lanterns.blocks.fizzeled_lantern.FizzeledLanternBlock;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.neoforged.neoforge.client.model.generators.loaders.CompositeModelBuilder;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplateBuilder;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, FancyLanterns.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        ModBlocks.ALL_LANTERNS.forEach(lantern -> this.lanternBlock(lantern, blockModels));
        ModBlocks.ALL_LANTERNS.forEach(lantern -> itemModels.generateFlatItem(lantern.asItem(), ModelTemplates.FLAT_ITEM));
    }

    private void lanternBlock(DeferredBlock<?> deferredBlock, BlockModelGenerators blockModels) {
        Block block = deferredBlock.get();
        String blockName = deferredBlock.getId().getPath();
        String familyName = getFamilyName(blockName);
        boolean supportsMutedState = block instanceof FancyLanternBlock || block instanceof FizzeledLanternBlock;
        Identifier flameTexture = modLoc("block/lantern/flames/" + familyName);

        PropertyDispatch.C3<VariantMutator, Boolean, Integer, Boolean> dispatch =
                PropertyDispatch.modify(LanternBlock.HANGING, LanternStateProperties.LEVEL, LanternStateProperties.MUTED);

        for (int level = LanternStateProperties.MIN_LEVEL; level <= LanternStateProperties.MAX_LEVEL; level++) {
            for (boolean muted : supportsMutedState ? new boolean[]{false, true} : new boolean[]{false}) {
                Identifier borderTexture = getBorderTexture(familyName, level, muted);
                String suffix = "_level_" + level + (muted ? "_muted" : "");
                Identifier standingModel = layeredLanternModel(
                        blockModels, modLoc("block/" + blockName + suffix),
                        modLoc("block/template_fancy_lantern"), flameTexture, borderTexture);
                Identifier hangingModel = layeredLanternModel(
                        blockModels, modLoc("block/" + blockName + suffix + "_hanging"),
                        modLoc("block/template_fancy_hanging_lantern"), flameTexture, borderTexture);

                dispatch.select(false, level, muted, modelVariant(standingModel));
                dispatch.select(true, level, muted, modelVariant(hangingModel));
            }
        }

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block, BlockModelGenerators.variant(new Variant(
                                modLoc("block/" + blockName + "_level_1"))))
                        .with(dispatch));
    }

    private static VariantMutator modelVariant(Identifier model) {
        return VariantMutator.MODEL.withValue(model);
    }

    private Identifier layeredLanternModel(
            BlockModelGenerators blockModels,
            Identifier modelLocation,
            Identifier template,
            Identifier flameTexture,
            Identifier borderTexture) {
        ModelTemplate layerTemplate = ExtendedModelTemplateBuilder.builder()
                .parent(template)
                .requiredTextureSlot(TextureSlot.LANTERN)
                .renderType("minecraft:cutout")
                .build();
        ModelTemplate compositeTemplate = ExtendedModelTemplateBuilder.builder()
                .parent(Identifier.withDefaultNamespace("block/block"))
                .requiredTextureSlot(TextureSlot.PARTICLE)
                .renderType("minecraft:cutout")
                .customLoader(CompositeModelBuilder::new, loader -> loader
                        .inlineChild("base_flame", layerTemplate,
                                TextureMapping.singleSlot(TextureSlot.LANTERN, flameTexture))
                        .inlineChild("upgrade_border", layerTemplate,
                                TextureMapping.singleSlot(TextureSlot.LANTERN, borderTexture))
                        .itemRenderOrder("base_flame", "upgrade_border"))
                .build();

        return compositeTemplate.create(
                modelLocation,
                TextureMapping.singleSlot(TextureSlot.PARTICLE, borderTexture),
                blockModels.modelOutput);
    }

    private Identifier getBorderTexture(String familyName, int level, boolean muted) {
        if (muted) {
            if (level == LanternStateProperties.MAX_LEVEL) {
                return modLoc("block/lantern/muted_borders/level_4/" + familyName);
            }
            return modLoc("block/lantern/muted_borders/level_" + level);
        }
        if (level == LanternStateProperties.MAX_LEVEL) {
            return modLoc("block/lantern/borders/level_4/" + familyName);
        }
        return modLoc("block/lantern/borders/level_" + level);
    }

    private static String getFamilyName(String blockName) {
        int lanternMarkerIndex = blockName.indexOf("_lantern");
        if (lanternMarkerIndex < 1) {
            throw new IllegalArgumentException("Cannot determine lantern family from block name: " + blockName);
        }
        return blockName.substring(0, lanternMarkerIndex);
    }

    private static Identifier modLoc(String path) {
        return Identifier.fromNamespaceAndPath(FancyLanterns.MODID, path);
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream();
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return ModItems.ITEMS.getEntries().stream();
    }
}
