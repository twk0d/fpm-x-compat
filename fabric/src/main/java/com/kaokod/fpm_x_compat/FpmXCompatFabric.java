package com.kaokod.fpm_x_compat;

import net.fabricmc.api.ClientModInitializer;

public class FpmXCompatFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        com.kaokod.fpm_x_compat.config.ConfigRegistry.setConfig(new com.kaokod.fpm_x_compat.config.FabricConfigWrapper());
        FpmXCompatMod.init();
    }
}
