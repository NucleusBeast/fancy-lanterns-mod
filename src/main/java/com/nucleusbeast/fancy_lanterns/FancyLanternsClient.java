package com.nucleusbeast.fancy_lanterns;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// Client-only entry point. It adds the "Config" button in the NeoForge mod list screen.
@Mod(value = FancyLanterns.MODID, dist = Dist.CLIENT)
public class FancyLanternsClient {
    public FancyLanternsClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
