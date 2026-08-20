package com.nucleusbeast.fancy_lanterns.blocks;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public final class LanternStateProperties {
    public static final int MIN_LEVEL = 1;
    public static final int MAX_LEVEL = 4;
    public static final IntegerProperty LEVEL = IntegerProperty.create("level", MIN_LEVEL, MAX_LEVEL);

    public static final BooleanProperty MUTED = BooleanProperty.create("muted");

    private LanternStateProperties() {
    }
}
