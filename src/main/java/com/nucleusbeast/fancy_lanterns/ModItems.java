package com.nucleusbeast.fancy_lanterns;

import com.nucleusbeast.fancy_lanterns.blocks.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FancyLanterns.MODID);


    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FancyLanterns.MODID);

    public static final Supplier<CreativeModeTab> USELESS_THINGS_TAB = CREATIVE_MODE_TAB.register("fancy_lanterns_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.fancy_lanterns"))
                    .icon(() -> new ItemStack(ModBlocks.HEALTHY_LANTERN.get()))
                    .displayItems((itemDisplayParameters, output) -> {
                        ModBlocks.ALL_LANTERNS.forEach(lantern -> output.accept(lantern.get()));
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
