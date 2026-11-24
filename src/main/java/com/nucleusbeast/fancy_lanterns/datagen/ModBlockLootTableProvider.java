package com.nucleusbeast.fancy_lanterns.datagen;

import com.nucleusbeast.fancy_lanterns.blocks.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

//? if <26.3 {
import net.minecraft.core.HolderLookup;
//?} else {
/*import net.minecraft.data.loot.LootTableSubProvider;
*///?}

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    //? if <26.3 {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }
    //?} else {
    /*protected ModBlockLootTableProvider(LootTableSubProvider.Context output) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), output);
    }
    *///?}

    @Override
    protected void generate() {
        ModBlocks.ALL_LANTERNS.forEach(lantern -> dropSelf(lantern.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
