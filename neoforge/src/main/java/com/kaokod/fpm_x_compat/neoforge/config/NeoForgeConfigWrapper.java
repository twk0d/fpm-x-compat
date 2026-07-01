package com.kaokod.fpm_x_compat.neoforge.config;

import com.kaokod.fpm_x_compat.config.FpmXCompatConfigValues;
import java.util.Arrays;
import java.util.List;

public class NeoForgeConfigWrapper implements FpmXCompatConfigValues {
    
    // Hardcoded defaults for NeoForge temporarily until ClothConfig JSON serialization is fully implemented.
    @Override public double getBodyYOffset() { return -0.1; }
    @Override public double getBiasSmoothingSpeed() { return 0.1; }
    @Override public List<? extends String> getIgnoredAccessorySlots() { return Arrays.asList("head", "hat", "face", "mask", "goggle"); }

    @Override public double getRightArmPitchMultiplier() { return 0.7; }
    @Override public double getRightArmPitchOffset() { return 0.15; }
    @Override public double getRightArmPitchCurve() { return 0.0; }
    @Override public double getRightArmPitchCurveFloor() { return 0.75; }
    @Override public double getRightArmUpperPitchBias() { return 0.1; }
    @Override public double getRightArmLowerPitchBias() { return 0.1; }
    @Override public double getRightArmPositionXOffset() { return 0.0; }
    @Override public double getRightArmPositionYOffset() { return 0.2; }
    @Override public double getRightArmPositionZOffset() { return 2.0; }
    @Override public double getRightArmLookUpZShift() { return 2.0; }

    @Override public double getLeftArmPitchMultiplier() { return 0.7; }
    @Override public double getLeftArmPitchOffset() { return 0.15; }
    @Override public double getLeftArmPitchCurve() { return 0.0; }
    @Override public double getLeftArmPitchCurveFloor() { return 0.75; }
    @Override public double getLeftArmUpperPitchBias() { return 0.1; }
    @Override public double getLeftArmLowerPitchBias() { return 0.1; }
    @Override public double getLeftArmPositionXOffset() { return 0.0; }
    @Override public double getLeftArmPositionYOffset() { return 0.2; }
    @Override public double getLeftArmPositionZOffset() { return 2.0; }
    @Override public double getLeftArmLookUpZShift() { return 2.0; }
}
