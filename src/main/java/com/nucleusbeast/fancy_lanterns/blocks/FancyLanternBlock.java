package com.nucleusbeast.fancy_lanterns.blocks;

import com.nucleusbeast.fancy_lanterns.Config;
import com.nucleusbeast.fancy_lanterns.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class FancyLanternBlock extends LanternBlock implements EntityBlock {

    public FancyLanternBlock(Properties properties, Holder<MobEffect> effect, ParticleOptions particleType, int level, DeferredBlock<Block> upgradesInto) {
        super(properties);
        this.effect = effect;
        this.particleType = particleType;
        this.level = level;
        this.upgradesInto = upgradesInto;
    }

    private final Holder<MobEffect> effect;
    private final ParticleOptions particleType;
    private final int level;
    public DeferredBlock<Block> upgradesInto;

    public Holder<MobEffect> getEffect() {
        return effect;
    }

    public ParticleOptions getParticleTypes() {
        return particleType;
    }

    public int getLevel(){
        return level;
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
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
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
