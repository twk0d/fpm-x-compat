package com.kaokod.fpm_x_compat.config;

/**
 * Registry for the platform-specific configuration implementation.
 */
public class ConfigRegistry {
    private static FpmXCompatConfigValues instance;

    public static FpmXCompatConfigValues getConfig() {
        if (instance == null) {
            throw new IllegalStateException("Config not initialized for this platform!");
        }
        return instance;
    }

    public static void setConfig(FpmXCompatConfigValues config) {
        instance = config;
    }
}