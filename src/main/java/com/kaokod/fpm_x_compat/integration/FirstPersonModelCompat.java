package com.kaokod.fpm_x_compat.integration;

import com.kaokod.fpm_x_compat.FpmXCompatMod;

import java.lang.reflect.Method;

/**
 * Integration bridge for the First Person Model (FPM) API.
 * Primarily used to determine if the player's body is being rendered in first-person.
 */
public class FirstPersonModelCompat {
    private static boolean isInitialized = false;
    private static boolean isModAvailable = false;

    private static Method RENDER_STATE_VALIDATOR = null;

    /**
     * Initializes reflection handles for First Person Model API.
     */
    public static void init() {
        if (isInitialized) return;
        isInitialized = true;

        try {
            Class<?> firstPersonApiClass = Class.forName("dev.tr7zw.firstperson.api.FirstPersonAPI");
            // API handle to check if the mod is currently rendering the player's body
            RENDER_STATE_VALIDATOR = firstPersonApiClass.getMethod("isRenderingPlayer");

            isModAvailable = true;
            FpmXCompatMod.MOD_LOGGER.info("[Bridge] First Person Model integration established.");
        } catch (Exception e) {
            isModAvailable = false;
            FpmXCompatMod.MOD_LOGGER.info("[Bridge] First Person Model not detected, skipping integration.");
        }
    }

    private static void initializeReflection() {
        init();
    }

    /**
     * Checks if First Person Model is currently rendering the player's body.
     * If false, kinematic fixes should be skipped to avoid visual artifacts.
     */
    public static boolean isRenderingPlayerBody() {
        initializeReflection();
        if (!isModAvailable) return false;

        try {
            return (Boolean) RENDER_STATE_VALIDATOR.invoke(null);
        } catch (Exception e) {
            return false;
        }
    }
}
