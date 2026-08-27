package com.nucleusbeast.fancy_lanterns.datagen;

import com.nucleusbeast.fancy_lanterns.FancyLanterns;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = FancyLanterns.MODID)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        gen.addProvider(true, new LootTableProvider(output, Collections.emptySet(), List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)
        ), lookupProvider));
        gen.addProvider(true, new ModRecipeProvider.Runner(output, lookupProvider));
        gen.addProvider(true, new ModItemTagProvider(
                output, lookupProvider));
        gen.addProvider(true, new ModModelProvider(output));
    }

    @SubscribeEvent
    public static void gatherServerData(GatherDataEvent.Server event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        gen.addProvider(true, new LootTableProvider(output, Collections.emptySet(), List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)
        ), lookupProvider));
        gen.addProvider(true, new ModRecipeProvider.Runner(output, lookupProvider));
        gen.addProvider(true, new ModItemTagProvider(
                output, lookupProvider));
    }
}
