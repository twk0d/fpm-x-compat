package com.kaokod.fpm_x_compat;

import com.kaokod.fpm_x_compat.config.ConfigRegistry;
import com.kaokod.fpm_x_compat.config.ForgeClientConfig;
import com.kaokod.fpm_x_compat.config.ForgeConfigWrapper;
import com.kaokod.fpm_x_compat.integration.CompatibilityRegistry;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(FpmXCompatMod.MOD_IDENTIFIER)
public class FpmXCompatMod {
    public static final String MOD_IDENTIFIER = "fpm_x_compat";
    public static final Logger MOD_LOGGER = LoggerFactory.getLogger(MOD_IDENTIFIER);

    public FpmXCompatMod() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ForgeClientConfig.SPEC, "fpm_x_compat-client.toml");
        ConfigRegistry.setConfig(new ForgeConfigWrapper());
        registerOptionalConfigScreen();
        init();
    }

    private static void init() {
        MOD_LOGGER.info("FPM x Compat Forge 1.20.1 initializing.");
        CompatibilityRegistry.initRuntimeBridges();
        CompatibilityRegistry.logMixinOnlyIntegrations();
        MOD_LOGGER.info("[Lifecycle] Mod initialization sequence complete.");
    }

    private static void registerOptionalConfigScreen() {
        if (!ModList.get().isLoaded("cloth_config")) {
            MOD_LOGGER.info("[Config] Cloth Config not detected; using Forge TOML config only.");
            return;
        }

        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, parent) -> ClothConfigScreenFactory.create(parent))
        );
        MOD_LOGGER.info("[Config] Cloth Config screen registered.");
    }
}
