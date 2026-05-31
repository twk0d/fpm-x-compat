package com.kaokod.fpm_bc_compat.mixin;

import com.kaokod.fpm_bc_compat.AttackStateManager;
import com.kaokod.fpm_bc_compat.config.FpmBcConfig;
import com.kaokod.fpm_bc_compat.util.KinematicsUtil;
import dev.kosmx.playerAnim.api.TransformType;
import dev.kosmx.playerAnim.core.impl.AnimationProcessor;
import dev.kosmx.playerAnim.core.util.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Core Mixin for synchronizing the player's 3D animation model with the camera and combat state.
 * This class intercept transformations from PlayerAnimator and applies kinematic corrections
 * to align arms, body, and legs for a first-person experience.
 */
@Mixin(value = AnimationProcessor.class, remap = false)
public class PlayerAnimationProcessorMixin {
   
   @Unique
   private static final Vec3f EMPTY_TRANSFORM_VEC = new Vec3f(0.0F, 0.0F, 0.0F);

   /**
    * Persistent state trackers for the 'Biased Smoothing' system.
    * These ensure that look-based offsets transition fluidly across the horizon line.
    */
   @Unique private float currentRightArmBias = 0.0f;
   @Unique private float currentLeftArmBias = 0.0f;

   /**
    * Injects into the result of model part transformations to apply dynamic overrides.
    * Only active when the player is in a valid attack/casting state.
    */
   @Inject(method = "get3DTransform", at = @At("RETURN"), cancellable = true)
   private void syncModelTransformWithCamera(String modelPartName, TransformType transformType, Vec3f originalTransform, CallbackInfoReturnable<Vec3f> returnableInfo) {
      // Skip logic if not attacking or if a roll (which has its own kinematics) is active
      if (!AttackStateManager.isPlayerInAttackState() || AttackStateManager.isPlayerInRollState()) {
          return;
      }

      Vec3f currentTransform = returnableInfo.getReturnValue();
      if (currentTransform == null) return;

      // Delegate transformations based on the target model part
      if ("body".equals(modelPartName) || "torso".equals(modelPartName)) {
          handleBodyTransformation(transformType, returnableInfo);
      }

      boolean isRightArm = "rightArm".equals(modelPartName);
      boolean isLeftArm = "leftArm".equals(modelPartName);

      if (isRightArm || isLeftArm) {
          handleArmTransformation(isRightArm, transformType, currentTransform, returnableInfo);
      }

      if (("rightLeg".equals(modelPartName) || "leftLeg".equals(modelPartName)) && transformType == TransformType.ROTATION) {
          handleLegRotation(returnableInfo, currentTransform);
      }
   }

   /**
    * Stabilizes the player's torso during combat.
    * Handles vertical alignment for sneaking and prevents horizontal shaking.
    */
   @Unique
   private void handleBodyTransformation(TransformType type, CallbackInfoReturnable<Vec3f> info) {
       if (type == TransformType.ROTATION) {
           // Lock torso X/Z to maintain a stable first-person horizon
           float rotationY = info.getReturnValue().getY();
           info.setReturnValue(new Vec3f(0.0F, rotationY, 0.0F));
       } else if (type == TransformType.POSITION) {
           // Apply custom vertical offset from config if crouching
           float verticalOffset = AttackStateManager.isPlayerCrouching() ? 
               FpmBcConfig.CLIENT.bodyYOffset.get().floatValue() : 0.0F;
           info.setReturnValue(new Vec3f(0.0F, verticalOffset, 0.0F));
       } else if (type == TransformType.BEND) {
           // Disable torso bending to prevent first-person model distortion
           info.setReturnValue(EMPTY_TRANSFORM_VEC);
       }
   }

   /**
    * Applies complex kinematic alignment to the arms.
    * Processes camera pitch, applies trigonometric curves for pole safety, 
    * and executes smoothed biased offsets for perfect crosshair alignment.
    */
   @Unique
   private void handleArmTransformation(boolean isRight, TransformType type, Vec3f current, CallbackInfoReturnable<Vec3f> info) {
       FpmBcConfig.Client config = FpmBcConfig.CLIENT;

       if (type == TransformType.ROTATION) {
           // Fetch limb-specific configuration
           float multiplier = isRight ? config.rightArmPitchMultiplier.get().floatValue() : config.leftArmPitchMultiplier.get().floatValue();
           float pitchOffset = isRight ? config.rightArmPitchOffset.get().floatValue() : config.leftArmPitchOffset.get().floatValue();
           float curvePow = isRight ? config.rightArmPitchCurve.get().floatValue() : config.leftArmPitchCurve.get().floatValue();
           float curveFloor = isRight ? config.rightArmPitchCurveFloor.get().floatValue() : config.leftArmPitchCurveFloor.get().floatValue();
           float targetUpperBias = isRight ? config.rightArmUpperPitchBias.get().floatValue() : config.leftArmUpperPitchBias.get().floatValue();
           float targetLowerBias = isRight ? config.rightArmLowerPitchBias.get().floatValue() : config.leftArmLowerPitchBias.get().floatValue();

           // Normalize and clamp pitch to avoid 90-degree singularities
           float pitch = KinematicsUtil.clampPitch(AttackStateManager.getPlayerCameraPitchRadians());
           
           // Apply trigonometric curve factor to dampen rotation speed at extreme vertical angles
           float curveFactor = KinematicsUtil.calculateTrigCurveFactor(pitch, curvePow, curveFloor);
           
           // Calculate smoothed directional bias based on look direction (UP vs DOWN)
           float targetBias = (pitch < 0) ? targetUpperBias : (pitch > 0 ? targetLowerBias : 0.0f);
           float smoothing = config.biasSmoothingSpeed.get().floatValue();
           
           if (isRight) currentRightArmBias = KinematicsUtil.lerp(currentRightArmBias, targetBias, smoothing);
           else currentLeftArmBias = KinematicsUtil.lerp(currentLeftArmBias, targetBias, smoothing);

           float activeBias = isRight ? currentRightArmBias : currentLeftArmBias;
           
           // Assemble final rotation adding our kinematic corrections to the base animation X-rotation
           float rotX = current.getX() + KinematicsUtil.calculateFinalArmRotationX(pitch, multiplier, curveFactor, pitchOffset, activeBias);

           info.setReturnValue(new Vec3f(rotX, current.getY(), current.getZ()));

       } else if (type == TransformType.POSITION) {
           // Apply configured coordinate offsets to align the arm's pivot with the view camera
           float posX = isRight ? config.rightArmPositionXOffset.get().floatValue() : config.leftArmPositionXOffset.get().floatValue();
           float posY = isRight ? config.rightArmPositionYOffset.get().floatValue() : config.leftArmPositionYOffset.get().floatValue();
           float posZ = isRight ? config.rightArmPositionZOffset.get().floatValue() : config.leftArmPositionZOffset.get().floatValue();

           info.setReturnValue(new Vec3f(current.getX() + posX, current.getY() + posY, current.getZ() + posZ));
       }
   }

   /**
    * Manages leg visibility and rotation during combat.
    * Prevents legs from clipping into view or the floor while attacking.
    */
   @Unique
   private void handleLegRotation(CallbackInfoReturnable<Vec3f> info, Vec3f current) {
       if (AttackStateManager.isPlayerMoving()) {
           // Hide leg swing noise during motion for first-person stability
           info.setReturnValue(EMPTY_TRANSFORM_VEC);
       } else {
           // Stabilize legs during stationary combat
           info.setReturnValue(new Vec3f(0.0F, current.getY(), current.getZ()));
       }
   }
}
