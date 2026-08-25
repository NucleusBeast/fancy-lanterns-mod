package com.nucleusbeast.fancy_lanterns.jei;

import com.nucleusbeast.fancy_lanterns.blocks.ModBlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

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
        List<ItemStack> lanternItems = ModBlocks.ALL_LANTERNS.stream()
                .map(block -> block.get().asItem().getDefaultInstance())
                .toList();

        registration.addItemStackInfo(
                lanternItems,
                Component.translatable("jei.fancy_lanterns.upgrade.level_1")
        );
        registration.addItemStackInfo(
                lanternItems,
                Component.translatable("jei.fancy_lanterns.upgrade.level_2")
        );
        registration.addItemStackInfo(
                lanternItems,
                Component.translatable("jei.fancy_lanterns.upgrade.level_3")
        );
        registration.addItemStackInfo(
                lanternItems,
                Component.translatable("jei.fancy_lanterns.muted")
        );
    }
}
