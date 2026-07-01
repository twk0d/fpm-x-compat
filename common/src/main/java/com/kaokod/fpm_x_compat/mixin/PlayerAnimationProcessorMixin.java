package com.kaokod.fpm_x_compat.mixin;

import com.kaokod.fpm_x_compat.AttackStateManager;
import com.kaokod.fpm_x_compat.config.ConfigRegistry;
import com.kaokod.fpm_x_compat.config.FpmXCompatConfigValues;
import com.kaokod.fpm_x_compat.util.KinematicsUtil;
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
 * Intercepts transformations and applies dynamic kinematic corrections.
 */
@Mixin(value = AnimationProcessor.class, remap = false)
public class PlayerAnimationProcessorMixin {
   
   @Unique
   private static final Vec3f EMPTY_TRANSFORM_VEC = new Vec3f(0.0F, 0.0F, 0.0F);

   @Unique private float currentRightArmBias = 0.0f;
   @Unique private float currentLeftArmBias = 0.0f;

   @Inject(method = "get3DTransform", at = @At("RETURN"), cancellable = true)
   private void syncModelTransformWithCamera(String modelPartName, TransformType transformType, Vec3f originalTransform, CallbackInfoReturnable<Vec3f> returnableInfo) {
      if (!AttackStateManager.isPlayerInAttackState() || AttackStateManager.isPlayerInRollState()) {
          return;
      }

      Vec3f currentTransform = returnableInfo.getReturnValue();
      if (currentTransform == null) return;

      if ("body".equals(modelPartName) || "torso".equals(modelPartName)) {
          handleBodyTransformation(transformType, returnableInfo);
      }

      boolean isRightArm = "rightArm".equals(modelPartName) || "right_arm".equals(modelPartName);
      boolean isLeftArm = "leftArm".equals(modelPartName) || "left_arm".equals(modelPartName);

      if (isRightArm || isLeftArm) {
          handleArmTransformation(isRightArm, transformType, currentTransform, returnableInfo);
      }

      if (("rightLeg".equals(modelPartName) || "right_leg".equals(modelPartName) || "leftLeg".equals(modelPartName) || "left_leg".equals(modelPartName)) && transformType == TransformType.ROTATION) {
          handleLegRotation(returnableInfo, currentTransform);
      }
   }

   @Unique
   private void handleBodyTransformation(TransformType type, CallbackInfoReturnable<Vec3f> info) {
       if (type == TransformType.ROTATION) {
           float rotationY = info.getReturnValue().getY();
           info.setReturnValue(new Vec3f(0.0F, rotationY, 0.0F));
       } else if (type == TransformType.POSITION) {
           float verticalOffset = AttackStateManager.isPlayerCrouching() ? 
                (float) ConfigRegistry.getConfig().getBodyYOffset() : 0.0F;
           info.setReturnValue(new Vec3f(0.0F, verticalOffset, 0.0F));
       } else if (type == TransformType.BEND) {
           info.setReturnValue(EMPTY_TRANSFORM_VEC);
       }
   }

   @Unique
   private void handleArmTransformation(boolean isRight, TransformType type, Vec3f current, CallbackInfoReturnable<Vec3f> info) {
        FpmXCompatConfigValues config = ConfigRegistry.getConfig();

        // Common variables
        float multiplier = isRight ? (float) config.getRightArmPitchMultiplier() : (float) config.getLeftArmPitchMultiplier();
        float pitchOffset = isRight ? (float) config.getRightArmPitchOffset() : (float) config.getLeftArmPitchOffset();
        float curvePow = isRight ? (float) config.getRightArmPitchCurve() : (float) config.getLeftArmPitchCurve();
        float curveFloor = isRight ? (float) config.getRightArmPitchCurveFloor() : (float) config.getLeftArmPitchCurveFloor();
        float targetUpperBias = isRight ? (float) config.getRightArmUpperPitchBias() : (float) config.getLeftArmUpperPitchBias();
        float targetLowerBias = isRight ? (float) config.getRightArmLowerPitchBias() : (float) config.getLeftArmLowerPitchBias();
        
        float posXOffset = isRight ? (float) config.getRightArmPositionXOffset() : (float) config.getLeftArmPositionXOffset();
        float posYOffset = isRight ? (float) config.getRightArmPositionYOffset() : (float) config.getLeftArmPositionYOffset();
        float posZOffset = isRight ? (float) config.getRightArmPositionZOffset() : (float) config.getLeftArmPositionZOffset();
        float lookUpZShift = isRight ? (float) config.getRightArmLookUpZShift() : (float) config.getLeftArmLookUpZShift();

       float rawPitch = AttackStateManager.getPlayerCameraPitchRadians();
       float pitch = KinematicsUtil.clampPitch(rawPitch);

       if (type == TransformType.ROTATION) {
           float curveFactor = KinematicsUtil.calculateTrigCurveFactor(pitch, curvePow, curveFloor);
           
           float targetBias = (pitch < 0) ? targetUpperBias : (pitch > 0 ? targetLowerBias : 0.0f);
            float smoothing = (float) config.getBiasSmoothingSpeed();
           
           if (isRight) currentRightArmBias = KinematicsUtil.lerp(currentRightArmBias, targetBias, smoothing);
           else currentLeftArmBias = KinematicsUtil.lerp(currentLeftArmBias, targetBias, smoothing);

           float activeBias = isRight ? currentRightArmBias : currentLeftArmBias;
           float rotX = current.getX() + KinematicsUtil.calculateFinalArmRotationX(pitch, multiplier, curveFactor, pitchOffset, activeBias);

           info.setReturnValue(new Vec3f(rotX, current.getY(), current.getZ()));

       } else if (type == TransformType.POSITION) {
           // Apply dynamic Z shift to prevent clipping when looking UP
           float dynamicZ = KinematicsUtil.calculateDynamicZOffset(pitch, posZOffset, lookUpZShift);

           info.setReturnValue(new Vec3f(current.getX() + posXOffset, current.getY() + posYOffset, current.getZ() + dynamicZ));
       }
   }

   @Unique
   private void handleLegRotation(CallbackInfoReturnable<Vec3f> info, Vec3f current) {
       if (AttackStateManager.isPlayerInAttackState()) {
           if (AttackStateManager.isPlayerMoving()) {
               info.setReturnValue(EMPTY_TRANSFORM_VEC);
           } else {
               info.setReturnValue(new Vec3f(0.0F, current.getY(), current.getZ()));
           }
       }
   }
}
