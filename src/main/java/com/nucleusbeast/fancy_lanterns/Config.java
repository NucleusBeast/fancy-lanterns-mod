package com.nucleusbeast.fancy_lanterns;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = FancyLanterns.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue REGULAR_LANTERN_RANGE = BUILDER.comment("Range of a regular lantern area of effects").defineInRange("regularLanternRange", 2, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue I_UPGRADE_LANTERN_RANGE = BUILDER.comment("Range of an upgraded (level I) lantern area of effects").defineInRange("upgradedLanternRangeI", 4, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue II_UPGRADE_LANTERN_RANGE = BUILDER.comment("Range of an upgraded (level II) lantern area of effects").defineInRange("upgradedLanternRangeII", 8, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.BooleanValue RETAIN_LEVEL = BUILDER.comment("Whether to lantern retains the level after expiring").define("retainLanternLevel", true);

    private static final ModConfigSpec.BooleanValue ENABLE_PARTICLES = BUILDER.comment("Whether to show particles of lantern effect").define("enable_particle_effects", true);
    private static final ModConfigSpec.BooleanValue ENABLE_SOUNDS = BUILDER.comment("Whether to play the sound on lantern effect trigger").define("enable_sound_effects", true);
    private static final ModConfigSpec.BooleanValue ENABLE_PREVIEW = BUILDER.comment("Whether to allow player to sneak click the lantern to show the radius of the lantern effects.").define("enable_preview_effects", true);

    private static final ModConfigSpec.ConfigValue<List<? extends String>> UPGRADE_ITEM_TO_I = BUILDER.comment("A list of items lantern can be upgraded with.").defineListAllowEmpty("upgrade_item_to_I", List.of("minecraft:iron_ingot", "minecraft:iron_block"), Config::validateItemName);
    private static final ModConfigSpec.ConfigValue<List<? extends String>> UPGRADE_ITEM_TO_II = BUILDER.comment("A list of items lantern can be upgraded with.").defineListAllowEmpty("upgrade_item_to_II", List.of("minecraft:gold_ingot", "minecraft:iron_block"), Config::validateItemName);
    private static final ModConfigSpec.ConfigValue<List<? extends String>> UPGRADE_ITEM_TO_PERM = BUILDER.comment("A list of items lantern can be upgraded with.").defineListAllowEmpty("upgrade_item_to_permanent_lantern", List.of("minecraft:nether_star", "useless_things:nucleus_core"), Config::validateItemName);

//    public static final ModConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER.comment("What you want the introduction message to be for the magic number").define("magicNumberIntroduction", "The magic number is... ");
    static final ModConfigSpec SPEC = BUILDER.build();

    public static int regularLanternRange;
    public static int upgradedLanternRangeI;
    public static int upgradedLanternRangeII;
    public static boolean retainLanterLevel;

    public static boolean enableParticles;
    public static boolean enableSoundEffects;
    public static boolean enableRangePreview;
    public static Set<Item> upgradeItemsToLevel1;
    public static Set<Item> upgradeItemsToLevel2;
    public static Set<Item> upgradeItemsToPermanent;


    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        regularLanternRange = REGULAR_LANTERN_RANGE.get();
        upgradedLanternRangeI = I_UPGRADE_LANTERN_RANGE.get();
        upgradedLanternRangeII = II_UPGRADE_LANTERN_RANGE.get();
        retainLanterLevel = RETAIN_LEVEL.get();

        enableParticles = ENABLE_PARTICLES.get();
        enableRangePreview = ENABLE_PREVIEW.get();
        enableSoundEffects = ENABLE_SOUNDS.get();

        // convert the list of strings into a set of items
        upgradeItemsToLevel1 = UPGRADE_ITEM_TO_I.get().stream().map(itemName -> BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemName))).collect(Collectors.toSet());
        upgradeItemsToLevel2 = UPGRADE_ITEM_TO_II.get().stream().map(itemName -> BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemName))).collect(Collectors.toSet());
        upgradeItemsToPermanent = UPGRADE_ITEM_TO_PERM.get().stream().map(itemName -> BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemName))).collect(Collectors.toSet());
    }
}
