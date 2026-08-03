package com.nucleusbeast.fancy_lanterns.blocks;

import com.nucleusbeast.fancy_lanterns.Config;
import com.nucleusbeast.fancy_lanterns.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static net.minecraft.world.level.block.entity.BeaconBlockEntity.playSound;

public class FancyLanternEntity extends BlockEntity {

    private static final int RANGE_PREVIEW_DURATION_TICKS = 5 * 20;
    private static final int RANGE_PREVIEW_INTERVAL_TICKS = 5;
    private static final int RANGE_PREVIEW_PARTICLE_COUNT = 48;

    private final Map<UUID, Long> rangePreviewEndTimes = new HashMap<>();

    public FancyLanternEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.MURKY_LANTERN_ENTITY.get(), pos, blockState);
        this.primaryPower = ((FancyLanternBlock) blockState.getBlock()).getEffect();
        this.particleTypes = ((FancyLanternBlock) blockState.getBlock()).getParticleTypes();
        this.levels = ((FancyLanternBlock) blockState.getBlock()).getLevel();
        this.usesRemaining = this.maxUsesTimesLevel * this.levels;
    }

    public int levels;
    public Holder<MobEffect> primaryPower;
    public ParticleOptions particleTypes;

    public int maxUsesTimesLevel = 5;
    private int usesRemaining = 0;
    public boolean isPermanent = false;

    public void startRangePreview(ServerPlayer player) {
        if (level instanceof ServerLevel serverLevel) {
            rangePreviewEndTimes.put(
                    player.getUUID(),
                    serverLevel.getGameTime() + RANGE_PREVIEW_DURATION_TICKS
            );
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, FancyLanternEntity blockEntity) {
        if (level instanceof ServerLevel serverLevel) {
            blockEntity.tickRangePreview(serverLevel, pos);
        }

        if (level.getGameTime() % 80L == 0L) {
            if (blockEntity.levels > 0) {
                if (blockEntity.usesRemaining < 1){
                    if (!level.isClientSide) {
                        BlockState fizzledState = getFizzledLantern(blockEntity)
                                .defaultBlockState()
                                .setValue(
                                        BlockStateProperties.HANGING,
                                        state.getValue(BlockStateProperties.HANGING)
                                )
                                .setValue(
                                        BlockStateProperties.WATERLOGGED,
                                        state.getValue(BlockStateProperties.WATERLOGGED)
                                );
                        level.setBlockAndUpdate(pos, fizzledState);
                    }

                    return;
                }
                applyEffects(level, pos, blockEntity, blockEntity.primaryPower);
                playSound(level, pos, SoundEvents.ITEM_PICKUP);


                if (!level.isClientSide) {
                    ((ServerLevel) level).sendParticles(blockEntity.particleTypes,
                            pos.getX() + 0.5,
                            pos.getY() + 0.5,
                            pos.getZ() + 0.5,
                            10,
                            5,
                            1,
                            4,
                            1.0
                    );
                }
            }
        }
    }

    private void tickRangePreview(ServerLevel level, BlockPos pos) {
        long gameTime = level.getGameTime();
        Iterator<Map.Entry<UUID, Long>> previews = rangePreviewEndTimes.entrySet().iterator();

        while (previews.hasNext()) {
            Map.Entry<UUID, Long> preview = previews.next();
            ServerPlayer player = level.getServer().getPlayerList().getPlayer(preview.getKey());

            if (preview.getValue() <= gameTime || player == null || player.serverLevel() != level) {
                previews.remove();
                continue;
            }

            if (gameTime % RANGE_PREVIEW_INTERVAL_TICKS == 0L) {
                sendRangeOutline(level, player, pos, levels);
            }
        }
    }

    private static void sendRangeOutline(ServerLevel level, ServerPlayer player, BlockPos pos, int levels) {
        double centerX = pos.getX() + 0.5D;
        double centerZ = pos.getZ() + 0.5D;
        double particleY = pos.getY() + 0.1D;

        for (int point = 0; point < (RANGE_PREVIEW_PARTICLE_COUNT * levels); point++) {
            double angle = Math.PI * 2.0D * point /( RANGE_PREVIEW_PARTICLE_COUNT * levels);
            double particleX = centerX + Math.cos(angle) * getRange(levels);
            double particleZ = centerZ + Math.sin(angle) * getRange(levels);

            level.sendParticles(
                    player,
                    ParticleTypes.COMPOSTER,
                    false,
                    particleX,
                    particleY,
                    particleZ,
                    1,
                    0.0D,
                    0.0D,
                    0.0D,
                    0.0D
            );
        }
    }

    private static int getRange(int lanternLevel){
        switch (lanternLevel){
            case 1:
                return Config.regularLanternRange;
            case 2:
                return Config.upgradedLanternRangeI;
            case 3:
                return Config.upgradedLanternRangeII;
            default:
                return 1;
        }
    }

    private static Block getFizzledLantern(FancyLanternEntity lantern) {
        if (!Config.retainLanterLevel) {
            return ModBlocks.MURKY_LANTERN.get();
        }

        return switch (lantern.levels) {
            case 2 -> ModBlocks.MURKY_LANTERN_UPGRADE_I.get();
            case 3 -> ModBlocks.MURKY_LANTERN_UPGRADE_II.get();
            default -> ModBlocks.MURKY_LANTERN.get();
        };
    }

    private static void applyEffects(
            Level level, BlockPos pos, FancyLanternEntity beaconLevel, @Nullable Holder<MobEffect> primaryEffect) {
        if (!level.isClientSide && primaryEffect != null) {
//            double range = (double)(beaconLevel * 10 + 10);
            int amplifier = 0;

            int duration = (9 + beaconLevel.levels * 2) * 20;
            AABB aabb = new AABB(pos)
                    .inflate(getRange(beaconLevel.levels))
                    .expandTowards(0.0, getRange(beaconLevel.levels) * 1.5D, 0.0);
            List<Player> list = level.getEntitiesOfClass(Player.class, aabb);

            for (Player player : list) {
                boolean wasApplied = player.addEffect(new MobEffectInstance(primaryEffect, duration, amplifier, true, true));
                if (wasApplied){
                    beaconLevel.usesRemaining--;
                }
            }
        }
    }
}
