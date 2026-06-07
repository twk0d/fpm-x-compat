package com.kaokod.fpm_x_compat;

import com.kaokod.fpm_x_compat.config.FpmBcConfig;
import com.kaokod.fpm_x_compat.config.FpmBcConfigScreen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(FpmBcCompatMod.MOD_IDENTIFIER)
public class FpmBcCompatMod {
    public static final String MOD_IDENTIFIER = "fpm_x_compat";
    public static final Logger MOD_LOGGER = LoggerFactory.getLogger(MOD_IDENTIFIER);

    /**
     * NeoForge 1.21.1 entry point.
     * ModContainer and IEventBus are automatically injected by the loader.
     */
    public FpmBcCompatMod(ModContainer container, IEventBus modEventBus) {
        MOD_LOGGER.info("***************************************************");
        MOD_LOGGER.info("* FPM x Better Combat Compatibility - version 0.0.4 *");
        MOD_LOGGER.info("* Developed by Kaokod                              *");
        MOD_LOGGER.info("Hi from Kaokod!");
        MOD_LOGGER.info("Hyenas are not stinky :3");
        MOD_LOGGER.info("***************************************************");


        // Register client configuration file
        container.registerConfig(ModConfig.Type.CLIENT, FpmBcConfig.CLIENT_SPEC);
        
        // Register the Cloth Config screen factory for the "Mods" menu
        container.registerExtensionPoint(IConfigScreenFactory.class, 
            (modContainer, parentScreen) -> FpmBcConfigScreen.create(parentScreen));

        // Initialize cross-mod compatibility bridges
        MOD_LOGGER.info("[Lifecycle] Initializing compatibility bridges...");
        com.kaokod.fpm_x_compat.integration.BetterCombatCompat.init();
        com.kaokod.fpm_x_compat.integration.SpellEngineCompat.init();
        com.kaokod.fpm_x_compat.integration.CombatRollCompat.init();
        com.kaokod.fpm_x_compat.integration.FirstPersonModelCompat.init();
        com.kaokod.fpm_x_compat.integration.EmfCompat.init();
        MOD_LOGGER.info("[Lifecycle] Mod initialization sequence complete.");
    }
}
