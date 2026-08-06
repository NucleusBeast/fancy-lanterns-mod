package com.nucleusbeast.fancy_lanterns;

import com.nucleusbeast.fancy_lanterns.blocks.FancyLanternEntity;
import com.nucleusbeast.fancy_lanterns.blocks.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, FancyLanterns.MODID);

    public static final Supplier<BlockEntityType<FancyLanternEntity>> MURKY_LANTERN_ENTITY =
            BLOCK_ENTITIES.register("murky_lantern_entity", () -> BlockEntityType.Builder.of(
                    FancyLanternEntity::new,
                    ModBlocks.EFFECT_LANTERNS.stream()
                            .map(DeferredBlock::get)
                            .toArray(Block[]::new)
                    ).build(null));

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
