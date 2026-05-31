package com.kaokod.fpm_bc_compat.mixin;


import com.kaokod.fpm_bc_compat.AttackStateManager;
import dev.kosmx.playerAnim.impl.animation.AnimationApplier;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for PlayerAnimator's AnimationApplier.
 * Fixes technical offsets related to the sneak (crouch) state during active animations.
 */
@Mixin(value = AnimationApplier.class, remap = false)
public class PlayerAnimationApplierMixin {
   
   /**
    * Injects into the part update cycle to manually adjust Y and Z offsets when sneaking.
    * This prevents the player's head and limbs from clipping incorrectly in first-person view.
    */
   @Inject(method = "updatePart", at = @At("TAIL"))
   private void adjustCrouchRendering(String modelPartName, ModelPart modelPart, CallbackInfo callbackInfo) {
      if (AttackStateManager.isPlayerInAttackState()) {
         if (AttackStateManager.isPlayerCrouching()) {
            // Offset head upwards to maintain vision alignment
            if ("head".equals(modelPartName)) {
               modelPart.y += 1.5F;
            }

            // Offset legs downwards to match the shifted torso
            if ("rightLeg".equals(modelPartName) || "leftLeg".equals(modelPartName)) {
               modelPart.y -= 1.5F;
            }

            // Shift torso slightly forward/backward to align with the camera pivot
            if ("torso".equals(modelPartName)) {
               modelPart.z += 0.15F;
            }
         }
      }
   }
}
