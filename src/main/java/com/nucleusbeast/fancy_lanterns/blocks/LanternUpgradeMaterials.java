package com.nucleusbeast.fancy_lanterns.blocks;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class LanternUpgradeMaterials {
    public LanternUpgradeMaterials() {
    }

    public static TagKey<Item> forLevel(int level) {
        return switch (level) {
            case 1 -> tag("lantern_upgrade_level_1");
            case 2 -> tag("lantern_upgrade_level_2");
            case 3 -> tag("lantern_upgrade_level_3");
            default -> null;
        };
    }

    static TagKey<Item> tag(String name) {
        return TagKey.create(Registries.ITEM,
                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(
                        "fancy_lanterns", name));
    }
}
