package com.nucleusbeast.fancy_lanterns.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class FizzeledLanternBlock extends LanternBlock {
    public FizzeledLanternBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(LanternStateProperties.LEVEL, LanternStateProperties.MIN_LEVEL));
    }

    private static final Map<Item, DeferredBlock<Block>> RELIGHT_MATERIAL =
            Map.ofEntries(
                    Map.entry(Items.GOLDEN_APPLE, ModBlocks.HEALTHY_LANTERN),
                    Map.entry(Items.IRON_SWORD, ModBlocks.STRENGTHY_LANTERN),
                    Map.entry(Items.GOLD_BLOCK, ModBlocks.ABSORBY_LANTERN),
                    Map.entry(Items.IRON_BOOTS, ModBlocks.SPEEDY_LANTERN),
                    Map.entry(Items.RABBIT_HIDE, ModBlocks.JUMPY_LANTERN),
                    Map.entry(Items.SOUL_TORCH, ModBlocks.NIGHTY_LANTERN),
                    Map.entry(Items.RABBIT_FOOT, ModBlocks.LUCKY_LANTERN),
                    Map.entry(Items.IRON_PICKAXE, ModBlocks.HASTY_LANTERN),
                    Map.entry(Items.APPLE, ModBlocks.SATURATY_LANTERN),
                    Map.entry(Items.FLINT_AND_STEEL, ModBlocks.FIERY_LANTERN),
                    Map.entry(Items.NAUTILUS_SHELL, ModBlocks.BREATHY_LANTERN)
            );

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            if (player.isShiftKeyDown()) {
                player.displayClientMessage(Component.literal("This lantern has fizzled! Add fuel!"), true);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        DeferredBlock<Block> relitBlock = RELIGHT_MATERIAL.get(itemStack.getItem());
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
                        );
                level.setBlockAndUpdate(pos, relitState);
                itemStack.consume(1, player);
                level.playSound(null, pos, SoundEvents.FOX_EAT, SoundSource.BLOCKS);
            }

            return ItemInteractionResult.SUCCESS;
        }

        int currentLevel = state.getValue(LanternStateProperties.LEVEL);
        if (currentLevel >= LanternStateProperties.MAX_LEVEL) {
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }
        if (LanternUpgradeMaterials.forLevel(currentLevel).contains(itemStack.getItem())) {
            if (!level.isClientSide) {
                level.setBlockAndUpdate(
                        pos,
                        state.setValue(LanternStateProperties.LEVEL, currentLevel + 1)
                );
                itemStack.consume(1, player);
                level.playSound(null, pos, SoundEvents.FOX_EAT, SoundSource.BLOCKS);
            }

            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LanternStateProperties.LEVEL);
    }
}
