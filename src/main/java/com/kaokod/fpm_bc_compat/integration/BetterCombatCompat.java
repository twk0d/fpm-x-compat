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
     * Lazily initializes reflection handles for Better Combat.
     * Prevents classloading issues if the mod is not installed.
     */
    private static void initializeReflection() {
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
        } catch (Exception e) {
            isModAvailable = false;
        }
    }

    /**
     * Checks if the Better Combat mod is performing an attack animation for the local player.
     */
    public static boolean isPlayerAttacking() {
        initializeReflection();
        if (!isModAvailable) return false;

        try {
            LocalPlayer player = MinecraftPlayerUtil.getClientPlayer();
            if (player == null) return false;

            Object attackAnimationInstance = INTERNAL_ATTACK_ANIM_FIELD.get(player);
            if (attackAnimationInstance == null) return false;

            Object baseLayer = SUBSTACK_BASE_LAYER_FIELD.get(attackAnimationInstance);
            if (baseLayer == null) return false;

            return (Boolean) LAYER_ACTIVITY_CHECK_METHOD.invoke(baseLayer);
        } catch (Exception e) {
            return false;
        }
    }
}
