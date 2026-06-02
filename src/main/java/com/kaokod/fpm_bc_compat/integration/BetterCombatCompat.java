package com.kaokod.fpm_bc_compat.integration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import com.kaokod.fpm_bc_compat.util.MinecraftPlayerUtil;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;

/**
 * Integration bridge for the Better Combat mod.
 * Uses reflection to detect active attack animations from the Better Combat engine.
 */
public class BetterCombatCompat {
    private static boolean isInitialized = false;
    private static boolean isModAvailable = false;

    // Reflection handles
    private static Field INTERNAL_ATTACK_ANIM_FIELD = null;
    private static Field SUBSTACK_BASE_LAYER_FIELD = null;
    private static Method LAYER_ACTIVITY_CHECK_METHOD = null;

    /**
     * Initializes reflection handles for Better Combat.
     * Prevents classloading issues if the mod is not installed.
     */
    public static void init() {
        if (isInitialized) return;
        isInitialized = true;

        try {
            // Better Combat stores its animation state in the player's attackAnimation field
            INTERNAL_ATTACK_ANIM_FIELD = AbstractClientPlayer.class.getDeclaredField("attackAnimation");
            INTERNAL_ATTACK_ANIM_FIELD.setAccessible(true);

            Class<?> attackAnimationSubStackClass = Class.forName("net.bettercombat.client.animation.AttackAnimationSubStack");
            SUBSTACK_BASE_LAYER_FIELD = attackAnimationSubStackClass.getField("base");

            Class<?> modifierLayerClass = Class.forName("dev.kosmx.playerAnim.api.layered.ModifierLayer");
            LAYER_ACTIVITY_CHECK_METHOD = modifierLayerClass.getMethod("isActive");

            isModAvailable = true;
            com.kaokod.fpm_bc_compat.FpmBcCompatMod.MOD_LOGGER.info("[Bridge] Better Combat integration established.");
        } catch (Exception e) {
            isModAvailable = false;
            com.kaokod.fpm_bc_compat.FpmBcCompatMod.MOD_LOGGER.info("[Bridge] Better Combat not detected, skipping integration.");
        }
    }

    private static void initializeReflection() {
        init();
    }

    /**
     * Checks if the Better Combat mod is performing an attack animation for the given player.
     */
    public static boolean isPlayerAttacking(net.minecraft.world.entity.player.Player player) {
        initializeReflection();
        if (!isModAvailable || player == null) return false;

        try {
            Object attackAnimationInstance = INTERNAL_ATTACK_ANIM_FIELD.get(player);
            if (attackAnimationInstance == null) return false;

            Object baseLayer = SUBSTACK_BASE_LAYER_FIELD.get(attackAnimationInstance);
            if (baseLayer == null) return false;

            return (Boolean) LAYER_ACTIVITY_CHECK_METHOD.invoke(baseLayer);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isPlayerAttacking() {
        return isPlayerAttacking(MinecraftPlayerUtil.getClientPlayer());
    }
}
