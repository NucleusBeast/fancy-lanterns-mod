package com.nucleusbeast.fancy_lanterns.jei;

import com.nucleusbeast.fancy_lanterns.blocks.LanternUpgradeMaterials;
import com.nucleusbeast.fancy_lanterns.blocks.ModBlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Iterator;
import java.util.List;

@JeiPlugin
public class FancyLanternsJeiPlugin implements IModPlugin {
    private static final ResourceLocation PLUGIN_UID = ResourceLocation.fromNamespaceAndPath(
            "fancy_lanterns",
            "jei_plugin"
    );

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_UID;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<ItemStack> lanternItems = ModBlocks.EFFECT_LANTERNS.stream()
                .map(block -> block.get().asItem().getDefaultInstance())
                .toList();

        registration.addItemStackInfo(
                ModBlocks.MURKY_LANTERN.get().asItem().getDefaultInstance(),
                Component.translatable("jei.fancy_lanterns.relight")
        );

        registration.addItemStackInfo(
                lanternItems,
                Component.translatable(
                        "jei.fancy_lanterns.upgrade.level_1",
                        upgradeMaterials(1)
                )
        );
        registration.addItemStackInfo(
                lanternItems,
                Component.translatable(
                        "jei.fancy_lanterns.upgrade.level_2",
                        upgradeMaterials(2)
                )
        );
        registration.addItemStackInfo(
                lanternItems,
                Component.translatable(
                        "jei.fancy_lanterns.upgrade.level_3",
                        upgradeMaterials(3)
                )
        );
        registration.addItemStackInfo(
                lanternItems,
                Component.translatable("jei.fancy_lanterns.muted")

        );
    }

    private static Component upgradeMaterials(int level) {
        TagKey<Item> tag = LanternUpgradeMaterials.forLevel(level);
        Iterator<Holder<Item>> items = BuiltInRegistries.ITEM.getTagOrEmpty(tag).iterator();

        if (!items.hasNext()) {
            return Component.translatable("jei.fancy_lanterns.upgrade.no_materials");
        }

        MutableComponent materials = Component.empty();
        boolean first = true;
        while (items.hasNext()) {
            if (!first) {
                materials.append(Component.literal(", "));
            }
            materials.append(new ItemStack(items.next()).getHoverName());
            first = false;
        }

        return materials;
    }
}
