package com.nucleusbeast.fancy_lanterns.blocks;

import com.nucleusbeast.fancy_lanterns.Config;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

public class FizzeledLanternBlock extends LanternBlock {
    public FizzeledLanternBlock(Properties properties, DeferredBlock<Block> upgradesInto, int level) {
        super(properties);
        this.upgradesInto = upgradesInto;
        this.level = level;
    }

    public DeferredBlock<Block> upgradesInto;
    public int level;

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

        if (RELIGHT_MATERIAL.containsKey(itemStack.getItem())) {
            if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
                level.setBlockAndUpdate(pos, RELIGHT_MATERIAL.get(itemStack.getItem()).get().defaultBlockState());
                itemStack.consume(1, player);
                level.playSound(null, pos, SoundEvents.FOX_EAT, SoundSource.BLOCKS);
            }
        }

        if (this.level > 3){
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }
        if (getLanternUpgradeItems(this.level).contains(itemStack.getItem())) {
            if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
                BlockState upgradedBlcok = upgradesInto.get()
                        .defaultBlockState()
                        .setValue(
                                BlockStateProperties.HANGING,
                                state.getValue(BlockStateProperties.HANGING)
                        )
                        .setValue(
                                BlockStateProperties.WATERLOGGED,
                                state.getValue(BlockStateProperties.WATERLOGGED)
                        );
                level.setBlockAndUpdate(pos, upgradedBlcok);
                itemStack.consume(1, player);
                level.playSound(null, pos, SoundEvents.FOX_EAT, SoundSource.BLOCKS);
            }
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private static Set<Item> getLanternUpgradeItems(int level) {
        return switch (level) {
            case 1 -> Config.upgradeItemsToLevel1;
            case 2 -> Config.upgradeItemsToLevel2;
            case 3 -> Config.upgradeItemsToPermanent;
            default -> Set.of();
        };
    }
}
