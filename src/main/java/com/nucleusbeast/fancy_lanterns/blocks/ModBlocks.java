package com.nucleusbeast.fancy_lanterns.blocks;

import com.nucleusbeast.fancy_lanterns.FancyLanterns;
import com.nucleusbeast.fancy_lanterns.ModItems;
import com.nucleusbeast.fancy_lanterns.blocks.fancy_lantern.FancyLanternBlock;
import com.nucleusbeast.fancy_lanterns.blocks.fizzeled_lantern.FizzeledLanternBlock;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
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
            () -> new FizzeledLanternBlock(lanternProperties(1))
    );

    public static final DeferredBlock<Block> HASTY_LANTERN = registerEffectLantern(
            "hasty_lantern",
            MobEffects.DIG_SPEED,
            coloredEffectParticle()
    );
    public static final DeferredBlock<Block> HEALTHY_LANTERN = registerEffectLantern(
            "healthy_lantern",
            MobEffects.REGENERATION,
            ParticleTypes.HEART
    );
    public static final DeferredBlock<Block> ABSORBY_LANTERN = registerEffectLantern(
            "absorby_lantern",
            MobEffects.ABSORPTION,
            coloredEffectParticle()
    );
    public static final DeferredBlock<Block> SATURATY_LANTERN = registerEffectLantern(
            "saturaty_lantern",
            MobEffects.SATURATION,
            coloredEffectParticle()
    );
    public static final DeferredBlock<Block> NIGHTY_LANTERN = registerEffectLantern(
            "nighty_lantern",
            MobEffects.NIGHT_VISION,
            coloredEffectParticle()
    );
    public static final DeferredBlock<Block> LUCKY_LANTERN = registerEffectLantern(
            "lucky_lantern",
            MobEffects.LUCK,
            coloredEffectParticle()
    );
    public static final DeferredBlock<Block> JUMPY_LANTERN = registerEffectLantern(
            "jumpy_lantern",
            MobEffects.JUMP,
            coloredEffectParticle()
    );
    public static final DeferredBlock<Block> SPEEDY_LANTERN = registerEffectLantern(
            "speedy_lantern",
            MobEffects.MOVEMENT_SPEED,
            coloredEffectParticle()
    );
    public static final DeferredBlock<Block> FIERY_LANTERN = registerEffectLantern(
            "fiery_lantern",
            MobEffects.FIRE_RESISTANCE,
            ParticleTypes.LAVA
    );
    public static final DeferredBlock<Block> STRENGTHY_LANTERN = registerEffectLantern(
            "strengthy_lantern",
            MobEffects.DAMAGE_BOOST,
            ParticleTypes.CRIT
    );
    public static final DeferredBlock<Block> BREATHY_LANTERN = registerEffectLantern(
            "breathy_lantern",
            MobEffects.WATER_BREATHING,
            ParticleTypes.NAUTILUS
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

    private ModBlocks() {
    }

    private static DeferredBlock<Block> registerEffectLantern(
            String name,
            Holder<MobEffect> effect,
            ParticleOptions particleType) {
        return registerBlock(
                name,
                () -> new FancyLanternBlock(lanternProperties(10), effect, particleType)
        );
    }

    private static BlockBehaviour.Properties lanternProperties(int lightLevel) {
        return BlockBehaviour.Properties.of()
                .strength(0.3f)
                .lightLevel(state -> lightLevel)
                .sound(SoundType.LANTERN);
    }

    private static ParticleOptions coloredEffectParticle() {
        return coloredEffectParticle(1.0F, 0.85F, 0.1F);
    }

    private static ParticleOptions coloredEffectParticle(float red, float green, float blue) {
        return ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, red, green, blue);
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> registeredBlock = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(registeredBlock.get(), new Item.Properties()));
        return registeredBlock;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
