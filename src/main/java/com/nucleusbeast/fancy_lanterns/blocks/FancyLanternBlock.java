package com.nucleusbeast.fancy_lanterns.blocks;

import com.nucleusbeast.fancy_lanterns.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FancyLanternBlock extends LanternBlock implements EntityBlock {

    public FancyLanternBlock(Properties properties, Holder<MobEffect> effect, ParticleOptions particleType, int level) {
        super(properties);
        this.effect = effect;
        this.particleType = particleType;
        this.level = level;
    }

    private final Holder<MobEffect> effect;
    private final ParticleOptions particleType;
    private final int level;

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

}
