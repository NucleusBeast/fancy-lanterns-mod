package com.nucleusbeast.fancy_lanterns.datagen;

import com.nucleusbeast.fancy_lanterns.blocks.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
//        dropSelf(ModBlocks.SIMPLE_GENERATOR.get());
        dropSelf(ModBlocks.MURKY_LANTERN.get());
        dropSelf(ModBlocks.MURKY_LANTERN_UPGRADE_I.get());
        dropSelf(ModBlocks.MURKY_LANTERN_UPGRADE_II.get());
        dropSelf(ModBlocks.MURKY_LANTERN_PERMANENT.get());

        dropSelf(ModBlocks.HEALTHY_LANTERN.get());
        dropSelf(ModBlocks.HASTY_LANTERN.get());
        dropSelf(ModBlocks.SPEEDY_LANTERN.get());
        dropSelf(ModBlocks.JUMPY_LANTERN.get());
        dropSelf(ModBlocks.BREATHY_LANTERN.get());
        dropSelf(ModBlocks.ABSORBY_LANTERN.get());
        dropSelf(ModBlocks.FIERY_LANTERN.get());
        dropSelf(ModBlocks.LUCKY_LANTERN.get());
        dropSelf(ModBlocks.NIGHTY_LANTERN.get());
        dropSelf(ModBlocks.SATURATY_LANTERN.get());
        dropSelf(ModBlocks.STRENGTHY_LANTERN.get());

        // add(ModBlocks.ORE_1.get(), block -> createOreDrop(block, ModItems.NUCLEUS_CORE.get()));
        // add(ModBlocks.ORE_1.get(), block -> createMultipleOreDrops(ModBlocks.ORE_1.get(), ModItems.RAW_ORE_ITEM1.get(), 1.0f, 2.0f));
    }

    // modified from createCopperOreDrops in BlockLootSubProvider
    protected LootTable.Builder createMultipleOreDrops(Block block, Item item, float minCount, float maxCount) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(
                block,
                this.applyExplosionDecay(
                        block,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minCount, maxCount)))
                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
