package com.kaokod.fpm_x_compat.integration;

import com.kaokod.fpm_x_compat.AttackStateManager;
import com.kaokod.fpm_x_compat.FpmBcCompatMod;
import net.minecraft.world.entity.player.Player;
import traben.entity_model_features.EMFAnimationApi;
import traben.entity_model_features.utils.EMFEntity;

import java.util.function.Function;

/**
 * Integration bridge for Entity Model Features (EMF).
 * Registers a pause condition to stop EMF animations during combat,
 * preventing conflicts with kinematic offsets.
 */
public class EmfCompat {
    private static boolean isInitialized = false;

    /**
     * Registers a pause listener with EMF.
     * When the player is in an attack state, EMF animations will be suspended.
     */
    public static void init() {
        if (isInitialized) return;
        isInitialized = true;

        try {
            // Check if EMF is present by attempting to load its API
            Class.forName("traben.entity_model_features.EMFAnimationApi");
            
            // Register a pause condition that suspends animations during attacks/rolls
            EMFAnimationApi.registerPauseCondition(new Function<EMFEntity, Boolean>() {
                @Override
                public Boolean apply(EMFEntity emfEntity) {
                    if (emfEntity instanceof Player player) {
                        // Suspend EMF animations if the player is currently performing a combat action
                        return AttackStateManager.isPlayerInAttackState(player) || AttackStateManager.isPlayerInRollState(player);
                    }
                    return false;
                }
            });
            
            FpmBcCompatMod.MOD_LOGGER.info("[Bridge] Entity Model Features (EMF) integration established.");
        } catch (ClassNotFoundException e) {
            FpmBcCompatMod.MOD_LOGGER.info("[Bridge] Entity Model Features (EMF) not detected, skipping integration.");
        } catch (Exception e) {
            FpmBcCompatMod.MOD_LOGGER.error("[Bridge] Failed to register EMF pause condition: " + e.getMessage());
        }
    }
}
