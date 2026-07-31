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

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FancyLanterns.MODID);

    // Health Lantern
    public static final DeferredBlock<Block> MURKY_LANTERN = registerBlock("murky_lantern",
            () -> new FizzeledLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN)
            ));
    public static final DeferredBlock<Block> MURKY_LANTERN_UPGRADE_I = registerBlock("murky_lantern_upgrade_i",
            () -> new FizzeledLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN)
            ));
    public static final DeferredBlock<Block> MURKY_LANTERN_UPGRADE_II = registerBlock("murky_lantern_upgrade_ii",
            () -> new FizzeledLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN)
            ));
    public static final DeferredBlock<Block> MURKY_LANTERN_PERMANENT = registerBlock("permanent_murky_lantern",
            () -> new FizzeledLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN)
            ));

    public static final DeferredBlock<Block> HASTY_LANTERN = registerBlock("hasty_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.DIG_SPEED,
                    ColorParticleOption.create(
                            ParticleTypes.ENTITY_EFFECT,
                            1.0F, 0.85F, 0.1F
                    ),
                    1
            ));

    public static final DeferredBlock<Block> HEALTHY_LANTERN = registerBlock("healthy_lantern",
            () -> new FancyLanternBlock(
                    BlockBehaviour.Properties.of().strength(0.3f).lightLevel(state -> 10).sound(SoundType.LANTERN),
                    MobEffects.REGENERATION,
                    ParticleTypes.HEART,
                    3
            ));



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