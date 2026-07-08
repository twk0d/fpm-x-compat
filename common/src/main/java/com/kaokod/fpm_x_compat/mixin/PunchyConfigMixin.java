package com.kaokod.fpm_x_compat.mixin;

import com.kaokod.fpm_x_compat.integration.PunchyCompat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Keeps Punchy's already-registered compatibility hooks from mutating external
 * animation mods after Punchy is disabled in its own config.
 */
@Pseudo
@Mixin(targets = "punchy.config.PunchyConfig", remap = false)
public abstract class PunchyConfigMixin {
    @Inject(method = "isModEnabled", at = @At("HEAD"), cancellable = true, require = 0)
    private static void fpm_x_compat$disablePunchyWhenFpmBodyNeverHides(CallbackInfoReturnable<Boolean> cir) {
        if (PunchyCompat.shouldForceDisablePunchyForFirstPersonModelBody()) {
            PunchyCompat.onModEnabledState(false);
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "isModEnabled", at = @At("RETURN"), require = 0)
    private static void fpm_x_compat$trackPunchyEnabledState(CallbackInfoReturnable<Boolean> cir) {
        PunchyCompat.onModEnabledState(Boolean.TRUE.equals(cir.getReturnValue()));
    }

    @Inject(method = "isBetterCombatCompatEnabled", at = @At("HEAD"), cancellable = true, require = 0)
    private static void fpm_x_compat$disableBetterCombatBridgeWhenPunchyDisabled(CallbackInfoReturnable<Boolean> cir) {
        if (PunchyCompat.isPunchyModDisabled()) {
            cir.setReturnValue(false);
        }
    }
}
