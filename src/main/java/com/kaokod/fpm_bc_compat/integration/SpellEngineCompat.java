package com.kaokod.fpm_bc_compat.integration;

import java.lang.reflect.Method;
import com.kaokod.fpm_bc_compat.util.MinecraftPlayerUtil;
import net.minecraft.client.player.LocalPlayer;

/**
 * Integration bridge for the Spell Engine mod.
 * Identifies if the player is currently casting a spell.
 */
public class SpellEngineCompat {
    private static boolean isInitialized = false;
    private static boolean isModAvailable = false;

    private static Method SPELL_CASTING_VALIDATOR_METHOD = null;

    private static void initializeReflection() {
        if (isInitialized) return;
        isInitialized = true;

        try {
            Class<?> spellCasterEntityClass = Class.forName("net.spell_engine.internals.casting.SpellCasterEntity");
            SPELL_CASTING_VALIDATOR_METHOD = spellCasterEntityClass.getMethod("isCastingSpell");

            isModAvailable = true;
        } catch (Exception e) {
            isModAvailable = false;
        }
    }

    /**
     * Checks if the player is in the middle of casting a spell.
     */
    public static boolean isPlayerCasting() {
        initializeReflection();
        if (!isModAvailable) return false;

        try {
            LocalPlayer player = MinecraftPlayerUtil.getClientPlayer();
            if (player == null) return false;

            return (Boolean) SPELL_CASTING_VALIDATOR_METHOD.invoke(player);
        } catch (Exception e) {
            return false;
        }
    }
}
