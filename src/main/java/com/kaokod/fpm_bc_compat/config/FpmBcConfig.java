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

        // --- Global Spec ---
        public final ModConfigSpec.DoubleValue biasSmoothingSpeed;
        public final ModConfigSpec.DoubleValue bodyYOffset;

        public Client(ModConfigSpec.Builder builder) {
            builder.push("Global Animation Settings");
            
            bodyYOffset = builder
                    .comment("Vertical offset of the body when the player is crouching.")
                    .defineInRange("bodyYOffset", -0.1, -1.0, 1.0);
            
            biasSmoothingSpeed = builder
                    .comment("Speed of the bias transition (0.01 to 1.0). Smaller values are smoother/slower.")
                    .defineInRange("biasSmoothingSpeed", 0.1, 0.01, 1.0);
            builder.pop();

            builder.push("Right Arm Settings");
            rightArmPitchMultiplier = builder.comment("Multiplier for camera pitch applied to the RIGHT arm.").defineInRange("rightArmPitchMultiplier", 0.8, -5.0, 5.0);
            rightArmPitchOffset = builder.comment("Fixed rotation offset (Pitch) for the RIGHT arm.").defineInRange("rightArmPitchOffset", 0.05, -3.14, 3.14);
            rightArmPitchCurve = builder.comment("Trigonometric curve intensity. Values > 0 smooth out rotation at extreme angles.").defineInRange("rightArmPitchCurve", 0.1, 0.0, 5.0);
            rightArmPitchCurveFloor = builder.comment("Minimum rotation factor at 90 degrees to prevent the weapon from vanishing.").defineInRange("rightArmPitchCurveFloor", 0.75, 0.0, 1.0);
            rightArmUpperPitchBias = builder.comment("Additional rotation applied ONLY when looking at the sky.").defineInRange("rightArmUpperPitchBias", 0.1, -3.14, 3.14);
            rightArmLowerPitchBias = builder.comment("Additional rotation applied ONLY when looking at the ground.").defineInRange("rightArmLowerPitchBias", 0.1, -3.14, 3.14);
            rightArmPositionXOffset = builder.comment("Horizontal (X) position offset for the RIGHT arm.").defineInRange("rightArmPositionXOffset", -3.0, -10.0, 10.0);
            rightArmPositionYOffset = builder.comment("Vertical (Y) position offset for the RIGHT arm.").defineInRange("rightArmPositionYOffset", 1.0, -10.0, 10.0);
            rightArmPositionZOffset = builder.comment("Depth (Z) position offset for the RIGHT arm.").defineInRange("rightArmPositionZOffset", 0.5, -10.0, 10.0);
            builder.pop();

            builder.push("Left Arm Settings");
            leftArmPitchMultiplier = builder.defineInRange("leftArmPitchMultiplier", 0.8, -5.0, 5.0);
            leftArmPitchOffset = builder.defineInRange("leftArmPitchOffset", 0.05, -3.14, 3.14);
            leftArmPitchCurve = builder.defineInRange("leftArmPitchCurve", 0.1, 0.0, 5.0);
            leftArmPitchCurveFloor = builder.defineInRange("leftArmPitchCurveFloor", 0.75, 0.0, 1.0);
            leftArmUpperPitchBias = builder.defineInRange("leftArmUpperPitchBias", 0.1, -3.14, 3.14);
            leftArmLowerPitchBias = builder.defineInRange("leftArmLowerPitchBias", 0.1, -3.14, 3.14);
            leftArmPositionXOffset = builder.defineInRange("leftArmPositionXOffset", 3.0, -10.0, 10.0);
            leftArmPositionYOffset = builder.defineInRange("leftArmPositionYOffset", 1.0, -10.0, 10.0);
            leftArmPositionZOffset = builder.defineInRange("leftArmPositionZOffset", 0.5, -10.0, 10.0);
            builder.pop();
        }
    }
}
