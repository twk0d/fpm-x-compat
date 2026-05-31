package com.kaokod.fpm_bc_compat.integration;

import java.lang.reflect.Method;
import com.kaokod.fpm_bc_compat.util.MinecraftPlayerUtil;
import net.minecraft.client.player.LocalPlayer;

/**
 * Integration bridge for the Combat Roll mod.
 * Monitors the rolling state to determine if kinematic fixes should be temporarily disabled.
 */
public class CombatRollCompat {
    private static boolean isInitialized = false;
    private static boolean isModAvailable = false;

    private static Method RESOLVE_ROLL_MANAGER_INSTANCE = null;
    private static Method CHECK_PLAYER_ROLLING_STATE = null;

    private static void initializeReflection() {
        if (isInitialized) return;
        isInitialized = true;

        try {
            Class<?> rollingEntityClass = Class.forName("net.combatroll.internals.RollingEntity");
            RESOLVE_ROLL_MANAGER_INSTANCE = rollingEntityClass.getMethod("getRollManager");

            Class<?> rollManagerClass = Class.forName("net.combatroll.internals.RollManager");
            CHECK_PLAYER_ROLLING_STATE = rollManagerClass.getMethod("isRolling");

            isModAvailable = true;
        } catch (Exception e) {
            isModAvailable = false;
        }
    }

    /**
     * Returns true if the player is currently performing a roll animation.
     */
    public static boolean isPlayerRolling() {
        initializeReflection();
        if (!isModAvailable) return false;

        try {
            LocalPlayer player = MinecraftPlayerUtil.getClientPlayer();
            if (player == null) return false;

            Object rollManagerInstance = RESOLVE_ROLL_MANAGER_INSTANCE.invoke(player);
            if (rollManagerInstance == null) return false;

            return (Boolean) CHECK_PLAYER_ROLLING_STATE.invoke(rollManagerInstance);
        } catch (Exception e) {
            return false;
        }
    }
}
