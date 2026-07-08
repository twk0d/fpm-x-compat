package com.kaokod.fpm_x_compat.mixin;

import com.kaokod.fpm_x_compat.integration.PunchyCompat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Lets First Person Model take priority when Punchy's own first-person model
 * bridge is disabled or configured to never hide the FPM body.
 */
@Pseudo
@Mixin(targets = "punchy.compat.FirstPersonModelCompat", remap = false)
public abstract class PunchyFirstPersonModelCompatMixin {
    @Inject(method = "shouldAllowBodyRender", at = @At("HEAD"), cancellable = true, require = 0)
    private static void fpm_x_compat$allowFpmBodyWhenPunchyDisabled(@Coerce Object client, CallbackInfoReturnable<Boolean> cir) {
        if (PunchyCompat.isPunchyModDisabled()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "shouldApplyThirdPersonAnimations", at = @At("HEAD"), cancellable = true, require = 0)
    private static void fpm_x_compat$keepExternalAnimationsWhenFpmBodyHasPriority(@Coerce Object client, CallbackInfoReturnable<Boolean> cir) {
        if (PunchyCompat.shouldForceDisablePunchyForFirstPersonModelBody()) {
            cir.setReturnValue(true);
        }
    }
}
