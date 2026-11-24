package com.nucleusbeast.fancy_lanterns.datagen;

import com.nucleusbeast.fancy_lanterns.FancyLanterns;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

//? if <1.21.4 {
/*import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;
*///?} else {
import net.neoforged.api.distmarker.Dist;
//?}
//? if <26.3 {
import java.util.List;
import java.util.Set;
//?} else {
/*import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;
*///?}

/**
 * Each Minecraft version runs its own datagen: `./gradlew :<version>:runData`, or `./gradlew runDataAll` for all versions.
 * Output goes to `versions/<version>/src/generated/resources`.
 */
//? if <1.21.4 {
/*@EventBusSubscriber(modid = FancyLanterns.MODID, bus = EventBusSubscriber.Bus.MOD)
 *///?} elif <1.21.6 {
/*@EventBusSubscriber(modid = FancyLanterns.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
 *///?} else {
@EventBusSubscriber(modid = FancyLanterns.MODID, value = Dist.CLIENT)
//?}
public class DataGenerators {

    //? if <1.21.4 {
    /*@SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        gen.addProvider(event.includeServer(), new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)
        ), lookupProvider));
        gen.addProvider(event.includeServer(), ModRecipeProvider.create(output, lookupProvider));
        gen.addProvider(event.includeServer(), new ModItemTagProvider(output, lookupProvider, existingFileHelper));

        gen.addProvider(event.includeClient(), new ModBlockStateProvider(output, existingFileHelper));
        gen.addProvider(event.includeClient(), new ModItemModelProvider(output, existingFileHelper));
    }
    *///?} elif <26.3 {
    // The clientData run makes both client and server data
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider((output, lookupProvider) -> new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)
        ), lookupProvider));
        event.createProvider(ModRecipeProvider.Runner::new);
        event.createProvider(ModItemTagProvider::new);

        event.createProvider(ModModelProvider::new);
    }
    //?} else {
    /*// The clientData run makes both client and server data
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(ModItemTagProvider::new);

        event.createProvider(ModModelProvider::new);
    }

    // 26.3+: loot tables, recipes and recipe advancements are registry entries
    @SubscribeEvent
    public static void gatherRegistries(GatherDataRegistryEntriesEvent event) {
        event.gatherFor(FancyLanterns.MODID)
                .lootTable(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK))
                .recipe(ModRecipeProvider::new);
    }
    *///?}
}
