package com.kaokod.fpm_x_compat.integration;

import java.lang.reflect.Method;

import com.kaokod.fpm_x_compat.FpmXCompatMod;
import com.kaokod.fpm_x_compat.util.MinecraftPlayerUtil;

/**
 * Integration bridge for the Combat Roll mod.
 * Monitors the rolling state to determine if kinematic fixes should be temporarily disabled.
 */
public class CombatRollCompat {
    private static boolean isInitialized = false;
    private static boolean isModAvailable = false;
    private static final String[][] CLASS_NAME_CANDIDATES = new String[][] {
            new String[] { "net.combat_roll.internals.RollingEntity", "net.combat_roll.internals.RollManager" },
            new String[] { "net.combatroll.internals.RollingEntity", "net.combatroll.internals.RollManager" }
    };

    private static Method RESOLVE_ROLL_MANAGER_INSTANCE = null;
    private static Method CHECK_PLAYER_ROLLING_STATE = null;

    public static void init() {
        if (isInitialized) return;
        isInitialized = true;

        for (String[] classNames : CLASS_NAME_CANDIDATES) {
            try {
                Class<?> rollingEntityClass = Class.forName(classNames[0]);
                RESOLVE_ROLL_MANAGER_INSTANCE = rollingEntityClass.getMethod("getRollManager");

                Class<?> rollManagerClass = Class.forName(classNames[1]);
                CHECK_PLAYER_ROLLING_STATE = rollManagerClass.getMethod("isRolling");

                isModAvailable = true;
                FpmXCompatMod.MOD_LOGGER.info("[Bridge] Combat Roll integration established.");
                return;
            } catch (Exception ignored) {
                RESOLVE_ROLL_MANAGER_INSTANCE = null;
                CHECK_PLAYER_ROLLING_STATE = null;
            }
        }

        isModAvailable = false;
        FpmXCompatMod.MOD_LOGGER.info("[Bridge] Combat Roll not detected, skipping integration.");
    }

    private static void initializeReflection() {
        init();
    }

    /**
     * Returns true if the player is currently performing a roll animation.
     */
    public static boolean isPlayerRolling(net.minecraft.world.entity.player.Player player) {
        initializeReflection();
        if (!isModAvailable || player == null) return false;

        try {
            Object rollManagerInstance = RESOLVE_ROLL_MANAGER_INSTANCE.invoke(player);
            if (rollManagerInstance == null) return false;

            return (Boolean) CHECK_PLAYER_ROLLING_STATE.invoke(rollManagerInstance);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isPlayerRolling() {
        return isPlayerRolling(MinecraftPlayerUtil.getClientPlayer());
    }
}
