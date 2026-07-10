package com.kaokod.fpm_x_compat.mixin;

import com.kaokod.fpm_x_compat.AttackStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import dev.tr7zw.firstperson.LogicHandler;
/**
 * Mixin targeting First Person Model's logic handler.
 * Prevents the mod from rotating the player's body during an attack.
 * 
 * Note: Pitch visibility overrides were removed to allow FPM to natively 
 * handle head/overlay visibility when looking down, preventing clipping issues.
 */
@Mixin(value = LogicHandler.class, remap = false)
public class FirstPersonLogicHandlerMixin {
   
   /**
    * Suppresses automatic body rotation during attacks to ensure kinematic alignment stays consistent.
    */
   @Inject(method = "calculateBodyRot", at = @At("HEAD"), cancellable = true)
   private static void suppressBodyYawDuringAttack(float partialTicks, float bodyYaw, CallbackInfoReturnable<Float> returnableInfo) {
      if (AttackStateManager.isPlayerInAttackState()) {
         returnableInfo.setReturnValue(0.0F);
      }
   }
}
