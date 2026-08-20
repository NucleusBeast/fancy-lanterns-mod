package com.nucleusbeast.fancy_lanterns;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;


// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = FancyLanterns.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // Range
    private static final ModConfigSpec.IntValue REGULAR_LANTERN_RANGE = BUILDER.comment("Range of regular lantern area of effects").defineInRange("regularLanternRange", 2, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue I_UPGRADE_LANTERN_RANGE = BUILDER.comment("Range of upgraded (level I) lantern area of effects").defineInRange("upgradedLanternRangeI", 4, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue II_UPGRADE_LANTERN_RANGE = BUILDER.comment("Range of upgraded (level II) lantern area of effects").defineInRange("upgradedLanternRangeII", 8, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue PERMANENT_LANTERN_RANGE = BUILDER.comment("Range of permanent lantern area of effects").defineInRange("permanentLanternRange", 16, 0, Integer.MAX_VALUE);

    // Uses
    private static final ModConfigSpec.IntValue REGULAR_LANTERN_USES = BUILDER.comment("How many times does a regular fancy lantern trigger until fizzling out").defineInRange("regularLanternUses", 32, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue I_UPGRADE_LANTERN_USES = BUILDER.comment("How many times does an upgraded (level I) fancy lantern trigger until fizzling out").defineInRange("upgradedLanternUsesI", 32, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue II_UPGRADE_LANTERN_USES = BUILDER.comment("How many times does an upgraded (level II) fancy lantern trigger until fizzling out").defineInRange("upgradedLanternUsesII", 32, 0, Integer.MAX_VALUE);

    // Effect duration
    private static final ModConfigSpec.IntValue REGULAR_LANTERN_EFFECT_DURATION = BUILDER.comment("How long should effect of regular fancy lantern be lasting").defineInRange("regularLanternEffectDuration", 16, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue I_UPGRADE_LANTERN_EFFECT_DURATION = BUILDER.comment("How long should the effect of an upgraded (level I) fancy lantern last").defineInRange("upgradedLanternEffectDurationI", 16, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue II_UPGRADE_LANTERN_EFFECT_DURATION = BUILDER.comment("How long should the effect of an upgraded (level II) fancy lantern last").defineInRange("upgradedLanternEffectDurationII", 16, 0, Integer.MAX_VALUE);

    // Other settings
    private static final ModConfigSpec.BooleanValue RETAIN_LEVEL = BUILDER.comment("Whether to lantern retains the level after expiring").define("retainLanternLevel", true);
    private static final ModConfigSpec.BooleanValue FIZZLES_OUT = BUILDER.comment("Whether lanterns ever expire").define("does_fizzles_out", true);
    private static final ModConfigSpec.BooleanValue EFFECT_AMPLIFIER = BUILDER.comment("Whether the effect gets amplified by lantern level").define("effect_amplified_by_level", true);
    private static final ModConfigSpec.BooleanValue MUTING_AFFECTS_EFFECT = BUILDER.comment("Whether muting the lanterns also disables the effects of those lanterns").define("does_muting_disable_effects", false);
    private static final ModConfigSpec.BooleanValue ENABLE_PARTICLES = BUILDER.comment("Whether to show particles of lantern effect").define("enable_particle_effects", true);
    private static final ModConfigSpec.BooleanValue ENABLE_SOUNDS = BUILDER.comment("Whether to play the sound on lantern effect trigger").define("enable_sound_effects", true);
    private static final ModConfigSpec.BooleanValue ENABLE_PREVIEW = BUILDER.comment("Whether to allow player to sneak click the lantern to show the radius of the lantern effects.").define("enable_preview_effects", true);

//    public static final ModConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER.comment("What you want the introduction message to be for the magic number").define("magicNumberIntroduction", "The magic number is... ");
    static final ModConfigSpec SPEC = BUILDER.build();

    // Range
    public static int regularLanternRange;
    public static int upgradedLanternRangeI;
    public static int upgradedLanternRangeII;
    public static int permanentLanternRange;

    // Uses
    public static int regularLantern_Uses;
    public static int upgradedLanternI_Uses;
    public static int upgradedLanternII_Uses;

    // Effect duration
    public static int regularLantern_EffectDuration;
    public static int upgradedLanternI_EffectDuration;
    public static int upgradedLanternII_EffectDuration;

    // Other settings
    public static boolean doesFizzleOut;
    public static boolean retainLanterLevel;
    public static boolean effectAmplifier;
    public static boolean mutingAffectsEffect;
    public static boolean enableParticles;
    public static boolean enableSoundEffects;
    public static boolean enableRangePreview;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {

        // Range
        regularLanternRange = REGULAR_LANTERN_RANGE.get();
        upgradedLanternRangeI = I_UPGRADE_LANTERN_RANGE.get();
        upgradedLanternRangeII = II_UPGRADE_LANTERN_RANGE.get();
        permanentLanternRange = PERMANENT_LANTERN_RANGE.get();

        // Uses
        regularLantern_Uses = REGULAR_LANTERN_USES.get();
        upgradedLanternI_Uses = I_UPGRADE_LANTERN_USES.get();
        upgradedLanternII_Uses = II_UPGRADE_LANTERN_USES.get();

        // Effect duration
        regularLantern_EffectDuration = REGULAR_LANTERN_EFFECT_DURATION.get();
        upgradedLanternI_EffectDuration = I_UPGRADE_LANTERN_EFFECT_DURATION.get();
        upgradedLanternII_EffectDuration = II_UPGRADE_LANTERN_EFFECT_DURATION.get();


        // Other settings

        retainLanterLevel = RETAIN_LEVEL.get();
        doesFizzleOut = FIZZLES_OUT.get();
        effectAmplifier = EFFECT_AMPLIFIER.get();
        mutingAffectsEffect = MUTING_AFFECTS_EFFECT.get();

        enableParticles = ENABLE_PARTICLES.get();
        enableRangePreview = ENABLE_PREVIEW.get();
        enableSoundEffects = ENABLE_SOUNDS.get();

    }
}
