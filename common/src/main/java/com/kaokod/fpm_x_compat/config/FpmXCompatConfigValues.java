package com.kaokod.fpm_x_compat.config;

import java.util.List;

public interface FpmXCompatConfigValues {
    
    // --- Global Spec ---
    double getBodyYOffset();
    double getBiasSmoothingSpeed();
    List<? extends String> getIgnoredAccessorySlots();

    // --- Right Arm Spec ---
    double getRightArmPitchMultiplier();
    double getRightArmPitchOffset();
    double getRightArmPitchCurve();
    double getRightArmPitchCurveFloor();
    double getRightArmUpperPitchBias();
    double getRightArmLowerPitchBias();
    double getRightArmPositionXOffset();
    double getRightArmPositionYOffset();
    double getRightArmPositionZOffset();
    double getRightArmLookUpZShift();

    // --- Left Arm Spec ---
    double getLeftArmPitchMultiplier();
    double getLeftArmPitchOffset();
    double getLeftArmPitchCurve();
    double getLeftArmPitchCurveFloor();
    double getLeftArmUpperPitchBias();
    double getLeftArmLowerPitchBias();
    double getLeftArmPositionXOffset();
    double getLeftArmPositionYOffset();
    double getLeftArmPositionZOffset();
    double getLeftArmLookUpZShift();
}