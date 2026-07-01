package com.kaokod.fpm_x_compat.neoforge;

import com.kaokod.fpm_x_compat.FpmXCompatMod;

import com.kaokod.fpm_x_compat.neoforge.config.NeoForgeConfigWrapper;
import com.kaokod.fpm_x_compat.config.ConfigRegistry;
import net.neoforged.fml.common.Mod;

@Mod(FpmXCompatModNeoForge.MOD_IDENTIFIER)
public class FpmXCompatModNeoForge {
    public static final String MOD_IDENTIFIER = "fpm_x_compat";

    public FpmXCompatModNeoForge() {
        ConfigRegistry.setConfig(new NeoForgeConfigWrapper());
        FpmXCompatMod.init();
    }
}
