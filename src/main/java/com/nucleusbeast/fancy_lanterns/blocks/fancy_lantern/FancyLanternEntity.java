package com.nucleusbeast.fancy_lanterns.blocks.fancy_lantern;

import com.nucleusbeast.fancy_lanterns.Config;
import com.nucleusbeast.fancy_lanterns.ModBlockEntities;
import com.nucleusbeast.fancy_lanterns.blocks.LanternStateProperties;
import com.nucleusbeast.fancy_lanterns.blocks.ModBlocks;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.*;

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
        resetUsesForLevel(blockState.getValue(LanternStateProperties.LEVEL));
    }

    public Holder<MobEffect> primaryPower;
    public ParticleOptions particleTypes;
    private int usesRemaining = 0;
    private float effectDuration = 0;

    void resetUsesForLevel(int lanternLevel) {
        this.usesRemaining = switch (lanternLevel) {
            case 1 -> Config.regularLantern_Uses;
            case 2 -> Config.upgradedLanternI_Uses;
            default -> Config.upgradedLanternII_Uses;
        };
        setChanged();
    }

    public void startRangePreview(ServerPlayer player) {
        if (level instanceof ServerLevel serverLevel) {
            rangePreviewEndTimes.put(
                    player.getUUID(),
                    serverLevel.getGameTime() + RANGE_PREVIEW_DURATION_TICKS
            );
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, FancyLanternEntity blockEntity) {
        int lanternLevel = state.getValue(LanternStateProperties.LEVEL);

        if (level instanceof ServerLevel serverLevel) {
            blockEntity.tickRangePreview(serverLevel, pos);
        }

        if (level.getGameTime() % 80L == 0L) {
            if (lanternLevel > 0) {
                if (blockEntity.usesRemaining < 1 && Config.doesFizzleOut) {
                    if (!level.isClientSide) {
                        BlockState fizzledState = getFizzledLanternState(state, lanternLevel);
                        level.setBlockAndUpdate(pos, fizzledState);
                    }

                    return;
                }

                int duration = switch (lanternLevel) {
                    case 1 -> Config.regularLantern_EffectDuration;
                    case 2 -> Config.upgradedLanternI_EffectDuration;
                    default -> Config.upgradedLanternII_EffectDuration;
                };

                if (blockEntity.effectDuration >= 4) {
                    blockEntity.effectDuration -= 4f;
                    return;
                }
                blockEntity.effectDuration = duration;
                blockEntity.effectDuration -= 4f;
                duration *= 20;

                if (Config.mutingAffectsEffect) {
                    return;
                }
                applyEffects(level, pos, blockEntity, lanternLevel, blockEntity.primaryPower, duration);

                if (state.getValue(LanternStateProperties.MUTED)) {
                    return;
                }
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
                sendRangeOutline(
                        level,
                        player,
                        pos,
                        getBlockState().getValue(LanternStateProperties.LEVEL)
                );
            }
        }
    }

    private static void sendRangeOutline(ServerLevel level, ServerPlayer player, BlockPos pos, int levels) {
        double centerX = pos.getX() + 0.5D;
        double centerZ = pos.getZ() + 0.5D;
        double particleY = pos.getY() + 0.1D;

        for (int point = 0; point < (RANGE_PREVIEW_PARTICLE_COUNT * levels); point++) {
            double angle = Math.PI * 2.0D * point / (RANGE_PREVIEW_PARTICLE_COUNT * levels);
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

    private static int getRange(int lanternLevel) {
        return switch (lanternLevel) {
            case 1 -> Config.regularLanternRange;
            case 2 -> Config.upgradedLanternRangeI;
            case 3 -> Config.upgradedLanternRangeII;
            case 4 -> Config.permanentLanternRange;
            default -> 1;
        };
    }

    private static BlockState getFizzledLanternState(BlockState state, int lanternLevel) {
        int fizzledLevel = Config.retainLanterLevel
                ? lanternLevel
                : LanternStateProperties.MIN_LEVEL;

        return ModBlocks.MURKY_LANTERN.get()
                .defaultBlockState()
                .setValue(LanternStateProperties.LEVEL, fizzledLevel)
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
    }

    private static void applyEffects(
            Level level,
            BlockPos pos,
            FancyLanternEntity lantern,
            int lanternLevel,
            @Nullable Holder<MobEffect> primaryEffect,
            int duration) {
        if (!level.isClientSide && primaryEffect != null) {

            int amplifier = (Config.effectAmplifier ? lanternLevel - 1 : 1);
            AABB aabb = new AABB(pos)
                    .inflate(getRange(lanternLevel))
                    .expandTowards(0.0, getRange(lanternLevel) * 1.5D, 0.0);
            List<Player> list = level.getEntitiesOfClass(Player.class, aabb);

            for (Player player : list) {
                boolean wasApplied = player.addEffect(new MobEffectInstance(primaryEffect, duration, amplifier, true, true));
                if (wasApplied) {
                    lantern.usesRemaining--;
                }
            }
        }
    }
}
