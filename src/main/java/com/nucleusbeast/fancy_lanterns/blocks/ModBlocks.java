package com.nucleusbeast.fancy_lanterns.blocks;

import com.nucleusbeast.fancy_lanterns.FancyLanterns;
import com.nucleusbeast.fancy_lanterns.ModItems;
import com.nucleusbeast.fancy_lanterns.blocks.fancy_lantern.FancyLanternBlock;
import com.nucleusbeast.fancy_lanterns.blocks.fizzeled_lantern.FizzeledLanternBlock;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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

public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FancyLanterns.MODID);

    public static final DeferredBlock<Block> MURKY_LANTERN = registerBlock(
            "murky_lantern",
            () -> new FizzeledLanternBlock(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(FancyLanterns.MODID, "murky_lantern")))
                    .strength(0.3f)
                    .lightLevel(state -> 1)
                    .sound(SoundType.LANTERN)
            )
    );

    public static final DeferredBlock<Block> HASTY_LANTERN = registerBlock(
            "hasty_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(FancyLanterns.MODID, "hasty_lantern")))
                            .strength(0.3f)
                            .lightLevel(state -> 14)
                            .sound(SoundType.LANTERN),
                    MobEffects.HASTE,
                    coloredEffectParticle(1.0F, 0.85F, 0.1F))
    );
    public static final DeferredBlock<Block> HEALTHY_LANTERN = registerBlock(
            "healthy_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(FancyLanterns.MODID, "healthy_lantern")))
                            .strength(0.3f)
                            .lightLevel(state -> 8)
                            .sound(SoundType.LANTERN),
                    MobEffects.REGENERATION,
                    ParticleTypes.HEART)
    );
    public static final DeferredBlock<Block> ABSORBY_LANTERN = registerBlock(
            "absorby_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(FancyLanterns.MODID, "absorby_lantern")))
                            .strength(0.3f)
                            .lightLevel(state -> 10)
                            .sound(SoundType.LANTERN),
                    MobEffects.ABSORPTION,
                    coloredEffectParticle(0F, 0.925F, 1F))
    );
    public static final DeferredBlock<Block> SATURATY_LANTERN = registerBlock(
            "saturaty_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(FancyLanterns.MODID, "saturaty_lantern")))
                            .strength(0.3f)
                            .lightLevel(state -> 15)
                            .sound(SoundType.LANTERN),
                    MobEffects.SATURATION,
                    coloredEffectParticle(0.729F, 0.29F, 0.09F))
    );
    public static final DeferredBlock<Block> NIGHTY_LANTERN = registerBlock(
            "nighty_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(FancyLanterns.MODID, "nighty_lantern")))
                            .strength(0.3f)
                            .lightLevel(state -> 9)
                            .sound(SoundType.LANTERN),
                    MobEffects.NIGHT_VISION,
                    ParticleTypes.WITCH)
    );
    public static final DeferredBlock<Block> LUCKY_LANTERN = registerBlock(
            "lucky_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(FancyLanterns.MODID, "lucky_lantern")))
                            .strength(0.3f)
                            .lightLevel(state -> 12)
                            .sound(SoundType.LANTERN),
                    MobEffects.LUCK,
                    ParticleTypes.HAPPY_VILLAGER)
    );
    public static final DeferredBlock<Block> JUMPY_LANTERN = registerBlock(
            "jumpy_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(FancyLanterns.MODID, "jumpy_lantern")))
                            .strength(0.3f)
                            .lightLevel(state -> 12)
                            .sound(SoundType.LANTERN),
                    MobEffects.JUMP_BOOST,
                    ParticleTypes.GLOW_SQUID_INK)
    );
    public static final DeferredBlock<Block> SPEEDY_LANTERN = registerBlock(
            "speedy_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(FancyLanterns.MODID, "speedy_lantern")))
                            .strength(0.3f)
                            .lightLevel(state -> 14)
                            .sound(SoundType.LANTERN),
                    MobEffects.SPEED,
                    ParticleTypes.WAX_OFF)
    );
    public static final DeferredBlock<Block> FIERY_LANTERN = registerBlock(
            "fiery_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(FancyLanterns.MODID, "fiery_lantern")))
                            .strength(0.3f)
                            .lightLevel(state -> 13)
                            .sound(SoundType.LANTERN),
                    MobEffects.FIRE_RESISTANCE,
                    ParticleTypes.LAVA)
    );
    public static final DeferredBlock<Block> STRENGTHY_LANTERN = registerBlock(
            "strengthy_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(FancyLanterns.MODID, "strengthy_lantern")))
                            .strength(0.3f)
                            .lightLevel(state -> 6)
                            .sound(SoundType.LANTERN),
                    MobEffects.STRENGTH,
                    ParticleTypes.CRIT
            )
    );

    public static final DeferredBlock<Block> BREATHY_LANTERN = registerBlock(
            "breathy_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(FancyLanterns.MODID, "breathy_lantern")))
                            .strength(0.3f)
                            .lightLevel(state -> 7)
                            .sound(SoundType.LANTERN),
                    MobEffects.WATER_BREATHING,
                    ParticleTypes.BUBBLE
            )
    );

    public static final List<DeferredBlock<Block>> EFFECT_LANTERNS = List.of(
            HASTY_LANTERN,
            HEALTHY_LANTERN,
            ABSORBY_LANTERN,
            SATURATY_LANTERN,
            NIGHTY_LANTERN,
            LUCKY_LANTERN,
            JUMPY_LANTERN,
            SPEEDY_LANTERN,
            FIERY_LANTERN,
            STRENGTHY_LANTERN,
            BREATHY_LANTERN
    );

    public static final List<DeferredBlock<Block>> ALL_LANTERNS = List.of(
            MURKY_LANTERN,
            HASTY_LANTERN,
            HEALTHY_LANTERN,
            ABSORBY_LANTERN,
            SATURATY_LANTERN,
            NIGHTY_LANTERN,
            LUCKY_LANTERN,
            JUMPY_LANTERN,
            SPEEDY_LANTERN,
            FIERY_LANTERN,
            STRENGTHY_LANTERN,
            BREATHY_LANTERN
    );

    private static ParticleOptions coloredEffectParticle(float red, float green, float blue) {
        return ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, red, green, blue);
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> registeredBlock = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(registeredBlock.get(), new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(FancyLanterns.MODID, name)))));
        return registeredBlock;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
