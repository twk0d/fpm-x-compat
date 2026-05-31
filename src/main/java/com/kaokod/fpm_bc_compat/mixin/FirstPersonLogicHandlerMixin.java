package com.kaokod.fpm_bc_compat.mixin;

import com.kaokod.fpm_bc_compat.AttackStateManager;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin targeting First Person Model's logic handler.
 * Prevents the mod from rotating the player's body or hiding parts when an attack is in progress.
 */
@Mixin(targets = "dev.tr7zw.firstperson.LogicHandler", remap = false)
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

   /**
    * Forces the "looking down" state to false during attacks.
    * This prevents FPM from hiding the character's body when the player aims downwards while swinging.
    */
   @Inject(method = "lookingDown()Z", at = @At("HEAD"), cancellable = true)
   private void forcePitchVisibility(CallbackInfoReturnable<Boolean> returnableInfo) {
      if (AttackStateManager.isPlayerInAttackState()) {
         returnableInfo.setReturnValue(false);
      }
   }

   /**
    * Overloaded variant of lookingDown for specific entity checks.
    */
   @Inject(method = "lookingDown(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
   private void forcePitchVisibilityWithEntity(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> returnableInfo) {
      if (AttackStateManager.isPlayerInAttackState()) {
         returnableInfo.setReturnValue(false);
      }
   }
}
