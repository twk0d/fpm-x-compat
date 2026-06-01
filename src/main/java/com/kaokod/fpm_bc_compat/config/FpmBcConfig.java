package com.kaokod.fpm_bc_compat.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class FpmBcConfig {
    public static final ModConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    static {
        final Pair<Client, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    public static class Client {
        // --- Right Arm Spec ---
        public final ModConfigSpec.DoubleValue rightArmPitchMultiplier;
        public final ModConfigSpec.DoubleValue rightArmPitchOffset;
        public final ModConfigSpec.DoubleValue rightArmPitchCurve;
        public final ModConfigSpec.DoubleValue rightArmPitchCurveFloor;
        public final ModConfigSpec.DoubleValue rightArmUpperPitchBias;
        public final ModConfigSpec.DoubleValue rightArmLowerPitchBias;
        public final ModConfigSpec.DoubleValue rightArmPositionXOffset;
        public final ModConfigSpec.DoubleValue rightArmPositionYOffset;
        public final ModConfigSpec.DoubleValue rightArmPositionZOffset;
        public final ModConfigSpec.DoubleValue rightArmLookUpZShift;

        // --- Left Arm Spec ---
        public final ModConfigSpec.DoubleValue leftArmPitchMultiplier;
        public final ModConfigSpec.DoubleValue leftArmPitchOffset;
        public final ModConfigSpec.DoubleValue leftArmPitchCurve;
        public final ModConfigSpec.DoubleValue leftArmPitchCurveFloor;
        public final ModConfigSpec.DoubleValue leftArmUpperPitchBias;
        public final ModConfigSpec.DoubleValue leftArmLowerPitchBias;
        public final ModConfigSpec.DoubleValue leftArmPositionXOffset;
        public final ModConfigSpec.DoubleValue leftArmPositionYOffset;
        public final ModConfigSpec.DoubleValue leftArmPositionZOffset;
        public final ModConfigSpec.DoubleValue leftArmLookUpZShift;

        // --- Global Spec ---
        public final ModConfigSpec.DoubleValue biasSmoothingSpeed;
        public final ModConfigSpec.DoubleValue bodyYOffset;

        public Client(ModConfigSpec.Builder builder) {
            builder.push("Global Animation Settings");
            bodyYOffset = builder.defineInRange("bodyYOffset", -0.1, -1.0, 1.0);
            biasSmoothingSpeed = builder.defineInRange("biasSmoothingSpeed", 0.1, 0.01, 1.0);
            builder.pop();

            builder.push("Right Arm Settings");
            rightArmPitchMultiplier = builder.defineInRange("rightArmPitchMultiplier", 0.8, -5.0, 5.0);
            rightArmPitchOffset = builder.defineInRange("rightArmPitchOffset", 0.15, -3.14, 3.14);
            rightArmPitchCurve = builder.defineInRange("rightArmPitchCurve", 0.0, 0.0, 5.0);
            rightArmPitchCurveFloor = builder.defineInRange("rightArmPitchCurveFloor", 0.75, 0.0, 1.0);
            rightArmUpperPitchBias = builder.defineInRange("rightArmUpperPitchBias", 0.1, -3.14, 3.14);
            rightArmLowerPitchBias = builder.defineInRange("rightArmLowerPitchBias", 0.1, -3.14, 3.14);
            
            rightArmPositionXOffset = builder.defineInRange("rightArmPositionXOffset", 0.0, -10.0, 10.0);
            rightArmPositionYOffset = builder.defineInRange("rightArmPositionYOffset", 0.2, -10.0, 10.0);
            rightArmPositionZOffset = builder.defineInRange("rightArmPositionZOffset", 2.0, -10.0, 10.0);
            rightArmLookUpZShift = builder.defineInRange("rightArmLookUpZShift", 2.0, 0.0, 10.0);
            builder.pop();

            builder.push("Left Arm Settings");
            leftArmPitchMultiplier = builder.defineInRange("leftArmPitchMultiplier", 0.8, -5.0, 5.0);
            leftArmPitchOffset = builder.defineInRange("leftArmPitchOffset", 0.15, -3.14, 3.14);
            leftArmPitchCurve = builder.defineInRange("leftArmPitchCurve", 0.0, 0.0, 5.0);
            leftArmPitchCurveFloor = builder.defineInRange("leftArmPitchCurveFloor", 0.75, 0.0, 1.0);
            leftArmUpperPitchBias = builder.defineInRange("leftArmUpperPitchBias", 0.1, -3.14, 3.14);
            leftArmLowerPitchBias = builder.defineInRange("leftArmLowerPitchBias", 0.1, -3.14, 3.14);
            
            leftArmPositionXOffset = builder.defineInRange("leftArmPositionXOffset", 0.0, -10.0, 10.0);
            leftArmPositionYOffset = builder.defineInRange("leftArmPositionYOffset", 0.2, -10.0, 10.0);
            leftArmPositionZOffset = builder.defineInRange("leftArmPositionZOffset", 2.0, -10.0, 10.0);
            leftArmLookUpZShift = builder.defineInRange("leftArmLookUpZShift", 2.0, 0.0, 10.0);
            builder.pop();
        }
    }
}
