package com.kaokod.fpm_bc_compat;

import com.kaokod.fpm_bc_compat.config.FpmBcConfig;
import com.kaokod.fpm_bc_compat.config.FpmBcConfigScreen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(FpmBcCompatMod.MOD_IDENTIFIER)
public class FpmBcCompatMod {
    public static final String MOD_IDENTIFIER = "fpm_bc_compat";
    public static final Logger MOD_LOGGER = LoggerFactory.getLogger(MOD_IDENTIFIER);

    /**
     * NeoForge 1.21.1 entry point.
     * ModContainer and IEventBus are automatically injected by the loader.
     */
    public FpmBcCompatMod(ModContainer container, IEventBus modEventBus) {
        // Register client configuration file
        container.registerConfig(ModConfig.Type.CLIENT, FpmBcConfig.CLIENT_SPEC);
        
        // Register the Cloth Config screen factory for the "Mods" menu
        container.registerExtensionPoint(IConfigScreenFactory.class, 
            (modContainer, parentScreen) -> FpmBcConfigScreen.create(parentScreen));

        MOD_LOGGER.info("[First Person Model X Better Combat Fix] Initialized successfully.");
        MOD_LOGGER.info("Hi from Kao!");
        MOD_LOGGER.info("Hyenas are not stinky!");
    }
}
