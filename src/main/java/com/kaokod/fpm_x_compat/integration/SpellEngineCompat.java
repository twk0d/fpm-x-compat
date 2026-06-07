package com.kaokod.fpm_x_compat.integration;

import java.lang.reflect.Method;
import com.kaokod.fpm_x_compat.util.MinecraftPlayerUtil;

/**
 * Integration bridge for the Spell Engine mod.
 * Identifies if the player is currently casting a spell.
 */
public class SpellEngineCompat {
    private static boolean isInitialized = false;
    private static boolean isModAvailable = false;

    private static Method SPELL_CASTING_VALIDATOR_METHOD = null;

    public static void init() {
        if (isInitialized) return;
        isInitialized = true;

        try {
            Class<?> spellCasterEntityClass = Class.forName("net.spell_engine.internals.casting.SpellCasterEntity");
            SPELL_CASTING_VALIDATOR_METHOD = spellCasterEntityClass.getMethod("isCastingSpell");

            isModAvailable = true;
            com.kaokod.fpm_x_compat.FpmBcCompatMod.MOD_LOGGER.info("[Bridge] Spell Engine integration established.");
        } catch (Exception e) {
            isModAvailable = false;
            com.kaokod.fpm_x_compat.FpmBcCompatMod.MOD_LOGGER.info("[Bridge] Spell Engine not detected, skipping integration.");
        }
    }

    private static void initializeReflection() {
        init();
    }

    /**
     * Checks if the player is in the middle of casting a spell.
     */
    public static boolean isPlayerCasting(net.minecraft.world.entity.player.Player player) {
        initializeReflection();
        if (!isModAvailable || player == null) return false;

        try {
            return (Boolean) SPELL_CASTING_VALIDATOR_METHOD.invoke(player);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isPlayerCasting() {
        return isPlayerCasting(MinecraftPlayerUtil.getClientPlayer());
    }
}
