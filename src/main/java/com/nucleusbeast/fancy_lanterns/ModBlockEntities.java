package com.nucleusbeast.fancy_lanterns;

import com.nucleusbeast.fancy_lanterns.blocks.ModBlocks;
import com.nucleusbeast.fancy_lanterns.blocks.fancy_lantern.FancyLanternEntity;
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
            BLOCK_ENTITIES.register("murky_lantern_entity", ModBlockEntities::createLanternType);

    private static BlockEntityType<FancyLanternEntity> createLanternType() {
        Block[] lanterns = ModBlocks.EFFECT_LANTERNS.stream()
                .map(DeferredBlock::get)
                .toArray(Block[]::new);
        //? if >=1.21.2 {
        return new BlockEntityType<>(FancyLanternEntity::new, lanterns);
        //?} else {
        /*return BlockEntityType.Builder.of(FancyLanternEntity::new, lanterns).build(null);
         *///?}
    }

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
