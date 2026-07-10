package com.kaokod.fpm_x_compat.config;

import java.util.List;

public class ForgeConfigWrapper implements FpmXCompatConfigValues {
    @Override public double getBodyYOffset() { return ForgeClientConfig.BODY_Y_OFFSET.get(); }
    @Override public double getBiasSmoothingSpeed() { return ForgeClientConfig.BIAS_SMOOTHING_SPEED.get(); }
    @Override public List<? extends String> getIgnoredAccessorySlots() { return ForgeClientConfig.IGNORED_ACCESSORY_SLOTS.get(); }

    @Override public double getRightArmPitchMultiplier() { return ForgeClientConfig.RIGHT_ARM_PITCH_MULTIPLIER.get(); }
    @Override public double getRightArmPitchOffset() { return ForgeClientConfig.RIGHT_ARM_PITCH_OFFSET.get(); }
    @Override public double getRightArmPitchCurve() { return ForgeClientConfig.RIGHT_ARM_PITCH_CURVE.get(); }
    @Override public double getRightArmPitchCurveFloor() { return ForgeClientConfig.RIGHT_ARM_PITCH_CURVE_FLOOR.get(); }
    @Override public double getRightArmUpperPitchBias() { return ForgeClientConfig.RIGHT_ARM_UPPER_PITCH_BIAS.get(); }
    @Override public double getRightArmLowerPitchBias() { return ForgeClientConfig.RIGHT_ARM_LOWER_PITCH_BIAS.get(); }
    @Override public double getRightArmPositionXOffset() { return ForgeClientConfig.RIGHT_ARM_POSITION_X_OFFSET.get(); }
    @Override public double getRightArmPositionYOffset() { return ForgeClientConfig.RIGHT_ARM_POSITION_Y_OFFSET.get(); }
    @Override public double getRightArmPositionZOffset() { return ForgeClientConfig.RIGHT_ARM_POSITION_Z_OFFSET.get(); }
    @Override public double getRightArmLookUpZShift() { return ForgeClientConfig.RIGHT_ARM_LOOK_UP_Z_SHIFT.get(); }

    @Override public double getLeftArmPitchMultiplier() { return ForgeClientConfig.LEFT_ARM_PITCH_MULTIPLIER.get(); }
    @Override public double getLeftArmPitchOffset() { return ForgeClientConfig.LEFT_ARM_PITCH_OFFSET.get(); }
    @Override public double getLeftArmPitchCurve() { return ForgeClientConfig.LEFT_ARM_PITCH_CURVE.get(); }
    @Override public double getLeftArmPitchCurveFloor() { return ForgeClientConfig.LEFT_ARM_PITCH_CURVE_FLOOR.get(); }
    @Override public double getLeftArmUpperPitchBias() { return ForgeClientConfig.LEFT_ARM_UPPER_PITCH_BIAS.get(); }
    @Override public double getLeftArmLowerPitchBias() { return ForgeClientConfig.LEFT_ARM_LOWER_PITCH_BIAS.get(); }
    @Override public double getLeftArmPositionXOffset() { return ForgeClientConfig.LEFT_ARM_POSITION_X_OFFSET.get(); }
    @Override public double getLeftArmPositionYOffset() { return ForgeClientConfig.LEFT_ARM_POSITION_Y_OFFSET.get(); }
    @Override public double getLeftArmPositionZOffset() { return ForgeClientConfig.LEFT_ARM_POSITION_Z_OFFSET.get(); }
    @Override public double getLeftArmLookUpZShift() { return ForgeClientConfig.LEFT_ARM_LOOK_UP_Z_SHIFT.get(); }
}
