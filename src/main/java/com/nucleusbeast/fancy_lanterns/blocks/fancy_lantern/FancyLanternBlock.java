package com.nucleusbeast.fancy_lanterns.blocks.fancy_lantern;

import com.nucleusbeast.fancy_lanterns.FancyLanterns;
import com.nucleusbeast.fancy_lanterns.ModBlockEntities;
import com.nucleusbeast.fancy_lanterns.blocks.LanternStateProperties;
import com.nucleusbeast.fancy_lanterns.blocks.LanternUpgradeMaterials;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FancyLanternBlock extends LanternBlock implements EntityBlock {

    public FancyLanternBlock(Properties properties, Holder<MobEffect> effect, ParticleOptions particleType) {
        super(properties);
        this.effect = effect;
        this.particleType = particleType;

        this.registerDefaultState(this.defaultBlockState()
                .setValue(LanternStateProperties.LEVEL, LanternStateProperties.MIN_LEVEL)
                .setValue(LanternStateProperties.MUTED, false));
    }

    private final Holder<MobEffect> effect;
    private final ParticleOptions particleType;

    public Holder<MobEffect> getEffect() {
        return effect;
    }

    public ParticleOptions getParticleTypes() {
        return particleType;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FancyLanternEntity(pos, state);
    }

    @javax.annotation.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.MURKY_LANTERN_ENTITY.get(), FancyLanternEntity::tick);
    }

    @javax.annotation.Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker
    ) {
        return clientType == serverType ? (BlockEntityTicker<A>)ticker : null;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        FancyLanterns.LOGGER.info("using");
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            if (player.isShiftKeyDown()) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof FancyLanternEntity fancyLantern) {
                    fancyLantern.startRangePreview(serverPlayer);
                } else {
                    player.displayClientMessage(Component.literal("Something went wrong!"), true);
                }
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

        int currentLevel = state.getValue(LanternStateProperties.LEVEL);
        if (currentLevel >= LanternStateProperties.MAX_LEVEL) {
//            return emptyHandFallback(itemStack);
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }

        if (itemStack.is(LanternUpgradeMaterials.forLevel(currentLevel))) {
            if (!level.isClientSide) {
                int upgradedLevel = currentLevel + 1;
                level.setBlockAndUpdate(pos, state.setValue(LanternStateProperties.LEVEL, upgradedLevel));

                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof FancyLanternEntity fancyLantern) {
                    fancyLantern.resetUsesForLevel(upgradedLevel);
                }

                itemStack.consume(1, player);
                level.playSound(null, pos, SoundEvents.FOX_EAT, SoundSource.BLOCKS);
            }

            return InteractionResult.SUCCESS;
        }

//        return emptyHandFallback(itemStack);
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    private static InteractionResult emptyHandFallback(ItemStack itemStack) {
        return itemStack.isEmpty()
                ? InteractionResult.TRY_WITH_EMPTY_HAND
                : InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(LanternStateProperties.LEVEL, LanternStateProperties.MUTED);
    }
}
