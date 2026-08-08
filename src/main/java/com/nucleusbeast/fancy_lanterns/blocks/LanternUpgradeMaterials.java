package com.nucleusbeast.fancy_lanterns.blocks;

import com.nucleusbeast.fancy_lanterns.Config;
import net.minecraft.world.item.Item;

import java.util.Set;

final class LanternUpgradeMaterials {
    private LanternUpgradeMaterials() {
    }

    static Set<Item> forLevel(int level) {
        return switch (level) {
            case 1 -> Config.upgradeItemsToLevel1;
            case 2 -> Config.upgradeItemsToLevel2;
            case 3 -> Config.upgradeItemsToPermanent;
            default -> Set.of();
        };
    }
}
