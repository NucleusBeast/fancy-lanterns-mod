package com.nucleusbeast.fancy_lanterns.blocks;

import com.nucleusbeast.fancy_lanterns.FancyLanterns;
import com.nucleusbeast.fancy_lanterns.ModItems;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FancyLanterns.MODID);

    // Fizzeled out Lantern
    public static final DeferredBlock<Block> MURKY_LANTERN_PERMANENT = registerBlock("permanent_murky_lantern",
            () -> new FizzeledLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 1).sound(SoundType.LANTERN),
                    null,
                    4
            ));
    public static final DeferredBlock<Block> MURKY_LANTERN_UPGRADE_II = registerBlock("murky_lantern_upgrade_ii",
            () -> new FizzeledLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 1).sound(SoundType.LANTERN),
                    MURKY_LANTERN_PERMANENT,
                    3
            ));
    public static final DeferredBlock<Block> MURKY_LANTERN_UPGRADE_I = registerBlock("murky_lantern_upgrade_i",
            () -> new FizzeledLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 1).sound(SoundType.LANTERN),
                    MURKY_LANTERN_UPGRADE_II,
                    2
            ));
    public static final DeferredBlock<Block> MURKY_LANTERN = registerBlock("murky_lantern",
            () -> new FizzeledLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 1).sound(SoundType.LANTERN),
                    MURKY_LANTERN_UPGRADE_I,
                    1
            ));

    public static final DeferredBlock<Block> HASTY_LANTERN_PERMANENT = registerBlock("permanent_hasty_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.DIG_SPEED,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    4,
                    null
            ));

    public static final DeferredBlock<Block> HASTY_LANTERN_UPGRADE_II = registerBlock("hasty_lantern_upgrade_ii",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.DIG_SPEED,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    3,
                    HASTY_LANTERN_PERMANENT
            ));

    public static final DeferredBlock<Block> HASTY_LANTERN_UPGRADE_I = registerBlock("hasty_lantern_upgrade_i",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.DIG_SPEED,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    2,
                    HASTY_LANTERN_UPGRADE_II
            ));

    public static final DeferredBlock<Block> HASTY_LANTERN = registerBlock("hasty_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.DIG_SPEED,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    1,
                    HASTY_LANTERN_UPGRADE_I
            ));

    public static final DeferredBlock<Block> HEALTHY_LANTERN_PERMANENT = registerBlock("permanent_healthy_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.REGENERATION,
                    ParticleTypes.HEART,
                    4,
                    null
            ));
    public static final DeferredBlock<Block> HEALTHY_LANTERN_UPGRADE_II = registerBlock("healthy_lantern_upgrade_ii",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.REGENERATION,
                    ParticleTypes.HEART,
                    3,
                    HEALTHY_LANTERN_PERMANENT
            ));
    public static final DeferredBlock<Block> HEALTHY_LANTERN_UPGRADE_I = registerBlock("healthy_lantern_upgrade_i",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.REGENERATION,
                    ParticleTypes.HEART,
                    2,
                    HEALTHY_LANTERN_UPGRADE_II
            ));
    public static final DeferredBlock<Block> HEALTHY_LANTERN = registerBlock("healthy_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.REGENERATION,
                    ParticleTypes.HEART,
                    1,
                    HEALTHY_LANTERN_UPGRADE_I
            ));

    public static final DeferredBlock<Block> ABSORBY_LANTERN_PERMANENT = registerBlock("permanent_absorby_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.ABSORPTION,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    4,
                    null
            ));
    public static final DeferredBlock<Block> ABSORBY_LANTERN_UPGRADE_II = registerBlock("absorby_lantern_upgrade_ii",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.ABSORPTION,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    3,
                    ABSORBY_LANTERN_PERMANENT
            ));
    public static final DeferredBlock<Block> ABSORBY_LANTERN_UPGRADE_I = registerBlock("absorby_lantern_upgrade_i",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.ABSORPTION,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    2,
                    ABSORBY_LANTERN_UPGRADE_II
            ));
    public static final DeferredBlock<Block> ABSORBY_LANTERN = registerBlock("absorby_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.ABSORPTION,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    1,
                    ABSORBY_LANTERN_UPGRADE_I
            ));

    public static final DeferredBlock<Block> SATURATY_LANTERN_PERMANENT = registerBlock("permanent_saturaty_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.SATURATION,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    4,
                    null
            ));
    public static final DeferredBlock<Block> SATURATY_LANTERN_UPGRADE_II = registerBlock("saturaty_lantern_upgrade_ii",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.SATURATION,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    3,
                    SATURATY_LANTERN_PERMANENT
            ));
    public static final DeferredBlock<Block> SATURATY_LANTERN_UPGRADE_I = registerBlock("saturaty_lantern_upgrade_i",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.SATURATION,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    2,
                    SATURATY_LANTERN_UPGRADE_II
            ));
    public static final DeferredBlock<Block> SATURATY_LANTERN = registerBlock("saturaty_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.SATURATION,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    1,
                    SATURATY_LANTERN_UPGRADE_I
            ));

    public static final DeferredBlock<Block> NIGHTY_LANTERN_PERMANENT = registerBlock("permanent_nighty_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.NIGHT_VISION,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    4,
                    null
            ));
    public static final DeferredBlock<Block> NIGHTY_LANTERN_UPGRADE_II = registerBlock("nighty_lantern_upgrade_ii",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.NIGHT_VISION,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    3,
                    NIGHTY_LANTERN_PERMANENT
            ));
    public static final DeferredBlock<Block> NIGHTY_LANTERN_UPGRADE_I = registerBlock("nighty_lantern_upgrade_i",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.NIGHT_VISION,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    2,
                    NIGHTY_LANTERN_UPGRADE_II
            ));
    public static final DeferredBlock<Block> NIGHTY_LANTERN = registerBlock("nighty_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.NIGHT_VISION,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    1,
                    NIGHTY_LANTERN_UPGRADE_I
            ));

    public static final DeferredBlock<Block> LUCKY_LANTERN_PERMANENT = registerBlock("permanent_lucky_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.LUCK,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    4,
                    null
            ));
    public static final DeferredBlock<Block> LUCKY_LANTERN_UPGRADE_II = registerBlock("lucky_lantern_upgrade_ii",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.LUCK,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    3,
                    LUCKY_LANTERN_PERMANENT
            ));
    public static final DeferredBlock<Block> LUCKY_LANTERN_UPGRADE_I = registerBlock("lucky_lantern_upgrade_i",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.LUCK,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    2,
                    LUCKY_LANTERN_UPGRADE_II
            ));
    public static final DeferredBlock<Block> LUCKY_LANTERN = registerBlock("lucky_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.LUCK,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    1,
                    LUCKY_LANTERN_UPGRADE_I
            ));

    public static final DeferredBlock<Block> JUMPY_LANTERN_PERMANENT = registerBlock("permanent_jumpy_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.JUMP,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    4,
                    null
            ));
    public static final DeferredBlock<Block> JUMPY_LANTERN_UPGRADE_II = registerBlock("jumpy_lantern_upgrade_ii",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.JUMP,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    3,
                    JUMPY_LANTERN_PERMANENT
            ));
    public static final DeferredBlock<Block> JUMPY_LANTERN_UPGRADE_I = registerBlock("jumpy_lantern_upgrade_i",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.JUMP,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    2,
                    JUMPY_LANTERN_UPGRADE_II
            ));
    public static final DeferredBlock<Block> JUMPY_LANTERN = registerBlock("jumpy_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.JUMP,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    1,
                    JUMPY_LANTERN_UPGRADE_I
            ));

    public static final DeferredBlock<Block> SPEEDY_LANTERN_PERMANENT = registerBlock("permanent_speedy_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.MOVEMENT_SPEED,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    4,
                    null
            ));
    public static final DeferredBlock<Block> SPEEDY_LANTERN_UPGRADE_II = registerBlock("speedy_lantern_upgrade_ii",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.MOVEMENT_SPEED,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    3,
                    SPEEDY_LANTERN_PERMANENT
            ));
    public static final DeferredBlock<Block> SPEEDY_LANTERN_UPGRADE_I = registerBlock("speedy_lantern_upgrade_i",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.MOVEMENT_SPEED,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    2,
                    SPEEDY_LANTERN_UPGRADE_II
            ));
    public static final DeferredBlock<Block> SPEEDY_LANTERN = registerBlock("speedy_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.MOVEMENT_SPEED,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    1,
                    SPEEDY_LANTERN_UPGRADE_I
            ));

    public static final DeferredBlock<Block> FIERY_LANTERN_PERMANENT = registerBlock("permanent_fiery_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.FIRE_RESISTANCE,
                    ParticleTypes.LAVA,
                    4,
                    null
            ));
    public static final DeferredBlock<Block> FIERY_LANTERN_UPGRADE_II = registerBlock("fiery_lantern_upgrade_ii",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.FIRE_RESISTANCE,
                    ParticleTypes.LAVA,
                    3,
                    FIERY_LANTERN_PERMANENT
            ));
    public static final DeferredBlock<Block> FIERY_LANTERN_UPGRADE_I = registerBlock("fiery_lantern_upgrade_i",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.FIRE_RESISTANCE,
                    ParticleTypes.LAVA,
                    2,
                    FIERY_LANTERN_UPGRADE_II
            ));
    public static final DeferredBlock<Block> FIERY_LANTERN = registerBlock("fiery_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.FIRE_RESISTANCE,
                    ParticleTypes.LAVA,
                    1,
                    FIERY_LANTERN_UPGRADE_I
            ));

    public static final DeferredBlock<Block> STRENGTHY_LANTERN_PERMANENT = registerBlock("permanent_strengthy_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.DAMAGE_BOOST,
                    ParticleTypes.CRIT,
                    4,
                    null
            ));
    public static final DeferredBlock<Block> STRENGTHY_LANTERN_UPGRADE_II = registerBlock("strengthy_lantern_upgrade_ii",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.DAMAGE_BOOST,
                    ParticleTypes.CRIT,
                    3,
                    STRENGTHY_LANTERN_PERMANENT
            ));
    public static final DeferredBlock<Block> STRENGTHY_LANTERN_UPGRADE_I = registerBlock("strengthy_lantern_upgrade_i",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.DAMAGE_BOOST,
                    ParticleTypes.CRIT,
                    2,
                    STRENGTHY_LANTERN_UPGRADE_II
            ));
    public static final DeferredBlock<Block> STRENGTHY_LANTERN = registerBlock("strengthy_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.DAMAGE_BOOST,
                    ParticleTypes.CRIT,
                    1,
                    STRENGTHY_LANTERN_UPGRADE_I
            ));

    public static final DeferredBlock<Block> BREATHY_LANTERN_PERMANENT = registerBlock("permanent_breathy_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.WATER_BREATHING,
                    ParticleTypes.NAUTILUS,
                    4,
                    null
            ));
    public static final DeferredBlock<Block> BREATHY_LANTERN_UPGRADE_II = registerBlock("breathy_lantern_upgrade_ii",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.WATER_BREATHING,
                    ParticleTypes.NAUTILUS,
                    3,
                    BREATHY_LANTERN_PERMANENT
            ));
    public static final DeferredBlock<Block> BREATHY_LANTERN_UPGRADE_I = registerBlock("breathy_lantern_upgrade_i",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.WATER_BREATHING,
                    ParticleTypes.NAUTILUS,
                    2,
                    BREATHY_LANTERN_UPGRADE_II
            ));
    public static final DeferredBlock<Block> BREATHY_LANTERN = registerBlock("breathy_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.WATER_BREATHING,
                    ParticleTypes.NAUTILUS,
                    1,
                    BREATHY_LANTERN_UPGRADE_I
            ));

    public static final List<DeferredBlock<Block>> EFFECT_LANTERNS = List.of(
            HASTY_LANTERN, HASTY_LANTERN_UPGRADE_I, HASTY_LANTERN_UPGRADE_II, HASTY_LANTERN_PERMANENT,
            HEALTHY_LANTERN, HEALTHY_LANTERN_UPGRADE_I, HEALTHY_LANTERN_UPGRADE_II, HEALTHY_LANTERN_PERMANENT,
            ABSORBY_LANTERN, ABSORBY_LANTERN_UPGRADE_I, ABSORBY_LANTERN_UPGRADE_II, ABSORBY_LANTERN_PERMANENT,
            SATURATY_LANTERN, SATURATY_LANTERN_UPGRADE_I, SATURATY_LANTERN_UPGRADE_II, SATURATY_LANTERN_PERMANENT,
            NIGHTY_LANTERN, NIGHTY_LANTERN_UPGRADE_I, NIGHTY_LANTERN_UPGRADE_II, NIGHTY_LANTERN_PERMANENT,
            LUCKY_LANTERN, LUCKY_LANTERN_UPGRADE_I, LUCKY_LANTERN_UPGRADE_II, LUCKY_LANTERN_PERMANENT,
            JUMPY_LANTERN, JUMPY_LANTERN_UPGRADE_I, JUMPY_LANTERN_UPGRADE_II, JUMPY_LANTERN_PERMANENT,
            SPEEDY_LANTERN, SPEEDY_LANTERN_UPGRADE_I, SPEEDY_LANTERN_UPGRADE_II, SPEEDY_LANTERN_PERMANENT,
            FIERY_LANTERN, FIERY_LANTERN_UPGRADE_I, FIERY_LANTERN_UPGRADE_II, FIERY_LANTERN_PERMANENT,
            STRENGTHY_LANTERN, STRENGTHY_LANTERN_UPGRADE_I, STRENGTHY_LANTERN_UPGRADE_II, STRENGTHY_LANTERN_PERMANENT,
            BREATHY_LANTERN, BREATHY_LANTERN_UPGRADE_I, BREATHY_LANTERN_UPGRADE_II, BREATHY_LANTERN_PERMANENT
    );

    public static final List<DeferredBlock<Block>> ALL_LANTERNS = Stream.concat(
            Stream.of(MURKY_LANTERN, MURKY_LANTERN_UPGRADE_I, MURKY_LANTERN_UPGRADE_II, MURKY_LANTERN_PERMANENT),
            EFFECT_LANTERNS.stream()
    ).toList();

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
