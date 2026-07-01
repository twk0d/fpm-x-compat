package com.kaokod.fpm_x_compat;

import com.kaokod.fpm_x_compat.integration.BetterCombatCompat;
import com.kaokod.fpm_x_compat.integration.CombatRollCompat;
import com.kaokod.fpm_x_compat.integration.FirstPersonModelCompat;
import com.kaokod.fpm_x_compat.integration.SpellEngineCompat;
import com.kaokod.fpm_x_compat.util.MinecraftPlayerUtil;

/**
 * Centralized state tracker that orchestrates multi-mod interaction.
 * Serves as an abstraction layer for Mixins.
 */
public class AttackStateManager {

    /**
     * Validates if rendering fixes should be applied.
     * Requires FPM to be rendering the body and either Better Combat or Spell Engine to be active.
     */
    public static boolean isPlayerInAttackState() {
        if (!FirstPersonModelCompat.isRenderingPlayerBody()) {
            return false;
        }
        return isPlayerInAttackState(com.kaokod.fpm_x_compat.util.MinecraftPlayerUtil.getClientPlayer());
    }

    public static boolean isPlayerInAttackState(net.minecraft.world.entity.player.Player player) {
        if (player == null) return false;
        return BetterCombatCompat.isPlayerAttacking(player) || SpellEngineCompat.isPlayerCasting(player);
    }

    public static boolean isPlayerInRollState() {
        return isPlayerInRollState(com.kaokod.fpm_x_compat.util.MinecraftPlayerUtil.getClientPlayer());
    }

    public static boolean isPlayerInRollState(net.minecraft.world.entity.player.Player player) {
        return CombatRollCompat.isPlayerRolling(player);
    }

    public static boolean isPlayerCrouching() {
        return MinecraftPlayerUtil.isPlayerCrouching();
    }

    public static boolean isPlayerCrouching(net.minecraft.world.entity.player.Player player) {
        return MinecraftPlayerUtil.isPlayerCrouching(player);
    }

    public static boolean isPlayerMoving() {
        return MinecraftPlayerUtil.isPlayerInMotion();
    }

    public static boolean isPlayerMoving(net.minecraft.world.entity.player.Player player) {
        return MinecraftPlayerUtil.isPlayerInMotion(player);
    }

    public static float getPlayerCameraPitchRadians() {
        return MinecraftPlayerUtil.getCameraPitchInRadians();
    }
}
