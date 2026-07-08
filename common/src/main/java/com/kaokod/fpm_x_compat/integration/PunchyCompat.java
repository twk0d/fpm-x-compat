package com.kaokod.fpm_x_compat.integration;

import net.minecraft.client.Minecraft;

import java.lang.reflect.Method;

/**
 * Reflection bridge for Punchy. Punchy mutates FPM/Better Combat/Spell Engine
 * runtime state, so this bridge restores those states when Punchy is disabled.
 */
public final class PunchyCompat {
    private static Method isModEnabledMethod;
    private static Method isFirstPersonModelHideEnabledMethod;
    private static Method isFirstPersonModelHideNeverMethod;
    private static Method applyVanillaItemDisplayMethod;
    private static Method applySpellFirstPersonAnimationsMethod;
    private static Method setFirstPersonHiddenMethod;

    private static boolean missingPunchyConfig;
    private static boolean missingBetterCombatCompat;
    private static boolean missingSpellEngineCompat;
    private static boolean missingArmRenderer;
    private static boolean restoredWhileDisabled;

    private PunchyCompat() {
    }

    public static void onModEnabledState(boolean enabled) {
        if (enabled) {
            restoredWhileDisabled = false;
            return;
        }

        if (restoredWhileDisabled) {
            return;
        }

        restoredWhileDisabled = true;
        restoreExternalAnimationState();
    }

    public static boolean isPunchyModDisabled() {
        Boolean enabled = invokeBoolean(resolveIsModEnabledMethod());
        if (enabled == null) {
            return false;
        }

        onModEnabledState(enabled);
        return !enabled;
    }

    public static boolean shouldForceDisablePunchyForFirstPersonModelBody() {
        if (!isFirstPersonCamera()) {
            return false;
        }

        Boolean hideEnabled = invokeBoolean(resolveIsFirstPersonModelHideEnabledMethod());
        Boolean hideNever = invokeBoolean(resolveIsFirstPersonModelHideNeverMethod());
        return Boolean.TRUE.equals(hideEnabled) && Boolean.TRUE.equals(hideNever);
    }

    private static void restoreExternalAnimationState() {
        invokeBooleanSetter(resolveApplyVanillaItemDisplayMethod(), false);
        invokeBooleanSetter(resolveApplySpellFirstPersonAnimationsMethod(), false);
        invokeBooleanSetter(resolveSetFirstPersonHiddenMethod(), false);
    }

    private static boolean isFirstPersonCamera() {
        Minecraft client = Minecraft.getInstance();
        return client != null && client.options != null && client.options.getCameraType().isFirstPerson();
    }

    private static Method resolveIsModEnabledMethod() {
        if (isModEnabledMethod == null && !missingPunchyConfig) {
            isModEnabledMethod = findStaticMethod("punchy.config.PunchyConfig", "isModEnabled");
        }
        return isModEnabledMethod;
    }

    private static Method resolveIsFirstPersonModelHideEnabledMethod() {
        if (isFirstPersonModelHideEnabledMethod == null && !missingPunchyConfig) {
            isFirstPersonModelHideEnabledMethod = findStaticMethod("punchy.config.PunchyConfig", "isFirstPersonModelHideEnabled");
        }
        return isFirstPersonModelHideEnabledMethod;
    }

    private static Method resolveIsFirstPersonModelHideNeverMethod() {
        if (isFirstPersonModelHideNeverMethod == null && !missingPunchyConfig) {
            isFirstPersonModelHideNeverMethod = findStaticMethod("punchy.config.PunchyConfig", "isFirstPersonModelHideNever");
        }
        return isFirstPersonModelHideNeverMethod;
    }

    private static Method resolveApplyVanillaItemDisplayMethod() {
        if (applyVanillaItemDisplayMethod == null && !missingBetterCombatCompat) {
            applyVanillaItemDisplayMethod = findStaticBooleanSetter(
                    "punchy.compat.BetterCombatCompat",
                    "applyVanillaItemDisplay",
                    MissingTarget.BETTER_COMBAT
            );
        }
        return applyVanillaItemDisplayMethod;
    }

    private static Method resolveApplySpellFirstPersonAnimationsMethod() {
        if (applySpellFirstPersonAnimationsMethod == null && !missingSpellEngineCompat) {
            applySpellFirstPersonAnimationsMethod = findStaticBooleanSetter(
                    "punchy.compat.SpellEngineCompat",
                    "applyFirstPersonAnimations",
                    MissingTarget.SPELL_ENGINE
            );
        }
        return applySpellFirstPersonAnimationsMethod;
    }

    private static Method resolveSetFirstPersonHiddenMethod() {
        if (setFirstPersonHiddenMethod == null && !missingArmRenderer) {
            setFirstPersonHiddenMethod = findStaticBooleanSetter(
                    "punchy.client.render.PunchyArmRenderer",
                    "setFirstPersonHidden",
                    MissingTarget.ARM_RENDERER
            );
        }
        return setFirstPersonHiddenMethod;
    }

    private static Method findStaticMethod(String className, String methodName) {
        try {
            return Class.forName(className).getMethod(methodName);
        } catch (ReflectiveOperationException | LinkageError | RuntimeException ignored) {
            missingPunchyConfig = true;
            return null;
        }
    }

    private static Method findStaticBooleanSetter(String className, String methodName, MissingTarget target) {
        try {
            return Class.forName(className).getMethod(methodName, boolean.class);
        } catch (ReflectiveOperationException | LinkageError | RuntimeException ignored) {
            markMissing(target);
            return null;
        }
    }

    private static Boolean invokeBoolean(Method method) {
        if (method == null) {
            return null;
        }

        try {
            Object value = method.invoke(null);
            return value instanceof Boolean booleanValue ? booleanValue : null;
        } catch (ReflectiveOperationException | RuntimeException ignored) {
            return null;
        }
    }

    private static void invokeBooleanSetter(Method method, boolean value) {
        if (method == null) {
            return;
        }

        try {
            method.invoke(null, value);
        } catch (ReflectiveOperationException | RuntimeException ignored) {
        }
    }

    private static void markMissing(MissingTarget target) {
        switch (target) {
            case BETTER_COMBAT -> missingBetterCombatCompat = true;
            case SPELL_ENGINE -> missingSpellEngineCompat = true;
            case ARM_RENDERER -> missingArmRenderer = true;
        }
    }

    private enum MissingTarget {
        BETTER_COMBAT,
        SPELL_ENGINE,
        ARM_RENDERER
    }
}
