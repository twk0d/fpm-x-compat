package com.kaokod.fpm_bc_compat;

import com.kaokod.fpm_bc_compat.integration.BetterCombatCompat;
import com.kaokod.fpm_bc_compat.integration.CombatRollCompat;
import com.kaokod.fpm_bc_compat.integration.FirstPersonModelCompat;
import com.kaokod.fpm_bc_compat.integration.SpellEngineCompat;
import com.kaokod.fpm_bc_compat.util.MinecraftPlayerUtil;

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
        return BetterCombatCompat.isPlayerAttacking() || SpellEngineCompat.isPlayerCasting();
    }

    public static boolean isPlayerInRollState() {
        return CombatRollCompat.isPlayerRolling();
    }

    public static boolean isPlayerCrouching() {
        return MinecraftPlayerUtil.isPlayerCrouching();
    }

    public static boolean isPlayerMoving() {
        return MinecraftPlayerUtil.isPlayerInMotion();
    }

    public static float getPlayerCameraPitchRadians() {
        return MinecraftPlayerUtil.getCameraPitchInRadians();
    }
}
