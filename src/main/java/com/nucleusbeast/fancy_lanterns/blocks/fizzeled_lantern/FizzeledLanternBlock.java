package com.nucleusbeast.fancy_lanterns.blocks.fizzeled_lantern;

import com.nucleusbeast.fancy_lanterns.blocks.LanternStateProperties;
import com.nucleusbeast.fancy_lanterns.blocks.LanternUpgradeMaterials;
import com.nucleusbeast.fancy_lanterns.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class FizzeledLanternBlock extends LanternBlock {
    public FizzeledLanternBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(LanternStateProperties.LEVEL, LanternStateProperties.MIN_LEVEL)
                .setValue(LanternStateProperties.MUTED, false));
    }

    private static final List<Map.Entry<TagKey<Item>, DeferredBlock<Block>>> RELIGHT_MATERIAL = List.of(
            relightTag("healthy_lantern", ModBlocks.HEALTHY_LANTERN),
            relightTag("strengthy_lantern", ModBlocks.STRENGTHY_LANTERN),
            relightTag("absorby_lantern", ModBlocks.ABSORBY_LANTERN),
            relightTag("speedy_lantern", ModBlocks.SPEEDY_LANTERN),
            relightTag("jumpy_lantern", ModBlocks.JUMPY_LANTERN),
            relightTag("nighty_lantern", ModBlocks.NIGHTY_LANTERN),
            relightTag("lucky_lantern", ModBlocks.LUCKY_LANTERN),
            relightTag("hasty_lantern", ModBlocks.HASTY_LANTERN),
            relightTag("saturaty_lantern", ModBlocks.SATURATY_LANTERN),
            relightTag("fiery_lantern", ModBlocks.FIERY_LANTERN),
            relightTag("breathy_lantern", ModBlocks.BREATHY_LANTERN)
    );

    private static Map.Entry<TagKey<Item>, DeferredBlock<Block>> relightTag(
            String lantern, DeferredBlock<Block> block) {
        return Map.entry(TagKey.create(Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath(
                        com.nucleusbeast.fancy_lanterns.FancyLanterns.MODID,
                        "relights/" + lantern)), block);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            if (player.isShiftKeyDown()) {
                player.displayClientMessage(Component.translatable(
                        "block.fancy_lanterns.murky_lantern.fizzled"), true);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        if (!state.getValue(LanternStateProperties.MUTED) && itemStack.is(ItemTags.WOOL)) {
            if (!level.isClientSide) {
                level.setBlockAndUpdate(pos, state.setValue(LanternStateProperties.MUTED, true));
                itemStack.consume(1, player);
                level.playSound(null, pos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS);
            }

            return InteractionResult.SUCCESS;
        }

        DeferredBlock<Block> relitBlock = RELIGHT_MATERIAL.stream()
                .filter(entry -> itemStack.is(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
        if (relitBlock != null) {
            if (!level.isClientSide) {
                BlockState relitState = relitBlock.get()
                        .defaultBlockState()
                        .setValue(
                                LanternStateProperties.LEVEL,
                                state.getValue(LanternStateProperties.LEVEL)
                        )
                        .setValue(
                                BlockStateProperties.HANGING,
                                state.getValue(BlockStateProperties.HANGING)
                        )
                        .setValue(
                                BlockStateProperties.WATERLOGGED,
                                state.getValue(BlockStateProperties.WATERLOGGED)
                        )
                        .setValue(
                                LanternStateProperties.MUTED,
                                state.getValue(LanternStateProperties.MUTED)
                        );
                level.setBlockAndUpdate(pos, relitState);
                itemStack.consume(1, player);
                level.playSound(null, pos, SoundEvents.FOX_EAT, SoundSource.BLOCKS);
            }

            return InteractionResult.SUCCESS;
        }

        int currentLevel = state.getValue(LanternStateProperties.LEVEL);
        if (currentLevel >= LanternStateProperties.MAX_LEVEL) {
            return InteractionResult.PASS;
        }
        if (itemStack.is(LanternUpgradeMaterials.forLevel(currentLevel))) {
            if (!level.isClientSide) {
                level.setBlockAndUpdate(
                        pos,
                        state.setValue(LanternStateProperties.LEVEL, currentLevel + 1)
                );
                itemStack.consume(1, player);
                level.playSound(null, pos, SoundEvents.FOX_EAT, SoundSource.BLOCKS);
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LanternStateProperties.LEVEL, LanternStateProperties.MUTED);
    }
}
