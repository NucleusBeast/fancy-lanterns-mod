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
import net.neoforged.neoforge.registries.DeferredBlock;

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

        dropLanternAsBase(ModBlocks.HASTY_LANTERN, ModBlocks.HASTY_LANTERN_UPGRADE_I,
                ModBlocks.HASTY_LANTERN_UPGRADE_II, ModBlocks.HASTY_LANTERN_PERMANENT);
        dropLanternAsBase(ModBlocks.HEALTHY_LANTERN, ModBlocks.HEALTHY_LANTERN_UPGRADE_I,
                ModBlocks.HEALTHY_LANTERN_UPGRADE_II, ModBlocks.HEALTHY_LANTERN_PERMANENT);
        dropLanternAsBase(ModBlocks.ABSORBY_LANTERN, ModBlocks.ABSORBY_LANTERN_UPGRADE_I,
                ModBlocks.ABSORBY_LANTERN_UPGRADE_II, ModBlocks.ABSORBY_LANTERN_PERMANENT);
        dropLanternAsBase(ModBlocks.SATURATY_LANTERN, ModBlocks.SATURATY_LANTERN_UPGRADE_I,
                ModBlocks.SATURATY_LANTERN_UPGRADE_II, ModBlocks.SATURATY_LANTERN_PERMANENT);
        dropLanternAsBase(ModBlocks.NIGHTY_LANTERN, ModBlocks.NIGHTY_LANTERN_UPGRADE_I,
                ModBlocks.NIGHTY_LANTERN_UPGRADE_II, ModBlocks.NIGHTY_LANTERN_PERMANENT);
        dropLanternAsBase(ModBlocks.LUCKY_LANTERN, ModBlocks.LUCKY_LANTERN_UPGRADE_I,
                ModBlocks.LUCKY_LANTERN_UPGRADE_II, ModBlocks.LUCKY_LANTERN_PERMANENT);
        dropLanternAsBase(ModBlocks.JUMPY_LANTERN, ModBlocks.JUMPY_LANTERN_UPGRADE_I,
                ModBlocks.JUMPY_LANTERN_UPGRADE_II, ModBlocks.JUMPY_LANTERN_PERMANENT);
        dropLanternAsBase(ModBlocks.SPEEDY_LANTERN, ModBlocks.SPEEDY_LANTERN_UPGRADE_I,
                ModBlocks.SPEEDY_LANTERN_UPGRADE_II, ModBlocks.SPEEDY_LANTERN_PERMANENT);
        dropLanternAsBase(ModBlocks.FIERY_LANTERN, ModBlocks.FIERY_LANTERN_UPGRADE_I,
                ModBlocks.FIERY_LANTERN_UPGRADE_II, ModBlocks.FIERY_LANTERN_PERMANENT);
        dropLanternAsBase(ModBlocks.STRENGTHY_LANTERN, ModBlocks.STRENGTHY_LANTERN_UPGRADE_I,
                ModBlocks.STRENGTHY_LANTERN_UPGRADE_II, ModBlocks.STRENGTHY_LANTERN_PERMANENT);
        dropLanternAsBase(ModBlocks.BREATHY_LANTERN, ModBlocks.BREATHY_LANTERN_UPGRADE_I,
                ModBlocks.BREATHY_LANTERN_UPGRADE_II, ModBlocks.BREATHY_LANTERN_PERMANENT);

        // add(ModBlocks.ORE_1.get(), block -> createOreDrop(block, ModItems.NUCLEUS_CORE.get()));
        // add(ModBlocks.ORE_1.get(), block -> createMultipleOreDrops(ModBlocks.ORE_1.get(), ModItems.RAW_ORE_ITEM1.get(), 1.0f, 2.0f));
    }

    private void dropLanternAsBase(DeferredBlock<Block> base, DeferredBlock<Block> upgradeI,
                                   DeferredBlock<Block> upgradeII, DeferredBlock<Block> permanent) {
        dropSelf(base.get());
        dropOther(upgradeI.get(), base.asItem());
        dropOther(upgradeII.get(), base.asItem());
        dropOther(permanent.get(), base.asItem());
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
