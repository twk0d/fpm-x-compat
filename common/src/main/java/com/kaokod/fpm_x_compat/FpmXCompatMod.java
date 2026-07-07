package com.kaokod.fpm_x_compat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FpmXCompatMod {
    public static final String MOD_IDENTIFIER = "fpm_x_compat";
    public static final Logger MOD_LOGGER = LoggerFactory.getLogger(MOD_IDENTIFIER);

    public static void init() {
        MOD_LOGGER.info(
        """
        
        *************************=*************************
                            FPM x Compat
                           Hi from Kaokod
                        Hyenas are not stinky!
        *************************=*************************"""
        );

        // Initialize cross-mod compatibility bridges
        MOD_LOGGER.info("[Lifecycle] Initializing compatibility bridges...");
        com.kaokod.fpm_x_compat.integration.CompatibilityRegistry.initRuntimeBridges();
        com.kaokod.fpm_x_compat.integration.CompatibilityRegistry.logMixinOnlyIntegrations();
        MOD_LOGGER.info("[Lifecycle] Mod initialization sequence complete.");
    }
}
