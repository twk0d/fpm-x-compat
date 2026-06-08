package com.kaokod.fpm_x_compat;

import com.kaokod.fpm_x_compat.config.FpmXCompatConfig;
import com.kaokod.fpm_x_compat.config.FpmXCompatConfigScreen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(FpmXCompatMod.MOD_IDENTIFIER)
public class FpmXCompatMod {
    public static final String MOD_IDENTIFIER = "fpm_x_compat";
    public static final Logger MOD_LOGGER = LoggerFactory.getLogger(MOD_IDENTIFIER);

    /**
     * NeoForge 1.21.1 entry point.
     * ModContainer and IEventBus are automatically injected by the loader.
     */
    public FpmXCompatMod(ModContainer container, IEventBus modEventBus) {
        MOD_LOGGER.info(
        """
        ***************************************************
                             FPM x Compat
                          Developed by Kaokod
                            Hi from Kaokod!
                         Hyenas are not stinky :3
        ***************************************************"""
        );


        // Register client configuration file
        container.registerConfig(ModConfig.Type.CLIENT, FpmXCompatConfig.CLIENT_SPEC);
        
        // Register the Cloth Config screen factory for the "Mods" menu
        container.registerExtensionPoint(IConfigScreenFactory.class, 
            (modContainer, parentScreen) -> FpmXCompatConfigScreen.create(parentScreen));

        // Initialize cross-mod compatibility bridges
        MOD_LOGGER.info("[Lifecycle] Initializing compatibility bridges...");
        com.kaokod.fpm_x_compat.integration.BetterCombatCompat.init();
        com.kaokod.fpm_x_compat.integration.SpellEngineCompat.init();
        com.kaokod.fpm_x_compat.integration.CombatRollCompat.init();
        com.kaokod.fpm_x_compat.integration.FirstPersonModelCompat.init();
        MOD_LOGGER.info("[Lifecycle] Mod initialization sequence complete.");
    }
}
