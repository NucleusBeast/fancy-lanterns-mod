package com.nucleusbeast.fancy_lanterns.datagen;

import com.nucleusbeast.fancy_lanterns.FancyLanterns;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = FancyLanterns.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        gen.addProvider(event.includeServer(), new LootTableProvider(output, Collections.emptySet(), List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)
        ), lookupProvider));
        gen.addProvider(event.includeServer(), new ModRecipeProvider(output, lookupProvider));
//
//        BlockTagsProvider blockTagsProvider = new ModBlockTagProvider(output, lookupProvider, existingFileHelper);
//        gen.addProvider(event.includeServer(), blockTagsProvider);
//        gen.addProvider(event.includeServer(), new ModItemTagProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
//
        gen.addProvider(event.includeClient(), new ModBlockStateProvider(output, existingFileHelper));
        gen.addProvider(event.includeClient(), new ModItemModelProvider(output, existingFileHelper));
//        gen.addProvider(event.includeClient(), new ModGlobalLootModifierProvider(output, lookupProvider));
    }
}
