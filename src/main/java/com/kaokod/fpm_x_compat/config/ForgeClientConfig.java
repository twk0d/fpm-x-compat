package com.kaokod.fpm_x_compat.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

public final class ForgeClientConfig {
    public static final double DEFAULT_BODY_Y_OFFSET = -0.1;
    public static final double DEFAULT_BIAS_SMOOTHING_SPEED = 0.1;
    public static final List<String> DEFAULT_IGNORED_ACCESSORY_SLOTS = Arrays.asList("head", "hat", "face", "mask", "goggle");

    public static final double DEFAULT_ARM_PITCH_MULTIPLIER = 0.7;
    public static final double DEFAULT_ARM_PITCH_OFFSET = 0.15;
    public static final double DEFAULT_ARM_PITCH_CURVE = 0.0;
    public static final double DEFAULT_ARM_PITCH_CURVE_FLOOR = 0.75;
    public static final double DEFAULT_ARM_UPPER_PITCH_BIAS = 0.1;
    public static final double DEFAULT_ARM_LOWER_PITCH_BIAS = 0.1;
    public static final double DEFAULT_ARM_POSITION_X_OFFSET = 0.0;
    public static final double DEFAULT_ARM_POSITION_Y_OFFSET = 0.2;
    public static final double DEFAULT_ARM_POSITION_Z_OFFSET = 2.0;
    public static final double DEFAULT_ARM_LOOK_UP_Z_SHIFT = 2.0;

    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.DoubleValue BODY_Y_OFFSET;
    public static final ForgeConfigSpec.DoubleValue BIAS_SMOOTHING_SPEED;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> IGNORED_ACCESSORY_SLOTS;

    public static final ForgeConfigSpec.DoubleValue RIGHT_ARM_PITCH_MULTIPLIER;
    public static final ForgeConfigSpec.DoubleValue RIGHT_ARM_PITCH_OFFSET;
    public static final ForgeConfigSpec.DoubleValue RIGHT_ARM_PITCH_CURVE;
    public static final ForgeConfigSpec.DoubleValue RIGHT_ARM_PITCH_CURVE_FLOOR;
    public static final ForgeConfigSpec.DoubleValue RIGHT_ARM_UPPER_PITCH_BIAS;
    public static final ForgeConfigSpec.DoubleValue RIGHT_ARM_LOWER_PITCH_BIAS;
    public static final ForgeConfigSpec.DoubleValue RIGHT_ARM_POSITION_X_OFFSET;
    public static final ForgeConfigSpec.DoubleValue RIGHT_ARM_POSITION_Y_OFFSET;
    public static final ForgeConfigSpec.DoubleValue RIGHT_ARM_POSITION_Z_OFFSET;
    public static final ForgeConfigSpec.DoubleValue RIGHT_ARM_LOOK_UP_Z_SHIFT;

    public static final ForgeConfigSpec.DoubleValue LEFT_ARM_PITCH_MULTIPLIER;
    public static final ForgeConfigSpec.DoubleValue LEFT_ARM_PITCH_OFFSET;
    public static final ForgeConfigSpec.DoubleValue LEFT_ARM_PITCH_CURVE;
    public static final ForgeConfigSpec.DoubleValue LEFT_ARM_PITCH_CURVE_FLOOR;
    public static final ForgeConfigSpec.DoubleValue LEFT_ARM_UPPER_PITCH_BIAS;
    public static final ForgeConfigSpec.DoubleValue LEFT_ARM_LOWER_PITCH_BIAS;
    public static final ForgeConfigSpec.DoubleValue LEFT_ARM_POSITION_X_OFFSET;
    public static final ForgeConfigSpec.DoubleValue LEFT_ARM_POSITION_Y_OFFSET;
    public static final ForgeConfigSpec.DoubleValue LEFT_ARM_POSITION_Z_OFFSET;
    public static final ForgeConfigSpec.DoubleValue LEFT_ARM_LOOK_UP_Z_SHIFT;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("global");
        BODY_Y_OFFSET = builder.comment("Vertical body offset applied while crouching during first-person attack animations.")
                .defineInRange("bodyYOffset", DEFAULT_BODY_Y_OFFSET, -2.0, 2.0);
        BIAS_SMOOTHING_SPEED = builder.comment("Interpolation speed for arm pitch bias. 0 is frozen, 1 is instant.")
                .defineInRange("biasSmoothingSpeed", DEFAULT_BIAS_SMOOTHING_SPEED, 0.0, 1.0);
        IGNORED_ACCESSORY_SLOTS = builder.comment("Curios/Relics slot name fragments hidden while FPM renders the local first-person body.")
                .defineListAllowEmpty("ignoredAccessorySlots", DEFAULT_IGNORED_ACCESSORY_SLOTS, value -> value instanceof String);
        builder.pop();

        builder.push("rightArm");
        RIGHT_ARM_PITCH_MULTIPLIER = defineArmDouble(builder, "pitchMultiplier", DEFAULT_ARM_PITCH_MULTIPLIER, 0.0, 2.0);
        RIGHT_ARM_PITCH_OFFSET = defineArmDouble(builder, "pitchOffset", DEFAULT_ARM_PITCH_OFFSET, -2.0, 2.0);
        RIGHT_ARM_PITCH_CURVE = defineArmDouble(builder, "pitchCurve", DEFAULT_ARM_PITCH_CURVE, 0.0, 4.0);
        RIGHT_ARM_PITCH_CURVE_FLOOR = defineArmDouble(builder, "pitchCurveFloor", DEFAULT_ARM_PITCH_CURVE_FLOOR, 0.0, 1.0);
        RIGHT_ARM_UPPER_PITCH_BIAS = defineArmDouble(builder, "upperPitchBias", DEFAULT_ARM_UPPER_PITCH_BIAS, -2.0, 2.0);
        RIGHT_ARM_LOWER_PITCH_BIAS = defineArmDouble(builder, "lowerPitchBias", DEFAULT_ARM_LOWER_PITCH_BIAS, -2.0, 2.0);
        RIGHT_ARM_POSITION_X_OFFSET = defineArmDouble(builder, "positionXOffset", DEFAULT_ARM_POSITION_X_OFFSET, -4.0, 4.0);
        RIGHT_ARM_POSITION_Y_OFFSET = defineArmDouble(builder, "positionYOffset", DEFAULT_ARM_POSITION_Y_OFFSET, -4.0, 4.0);
        RIGHT_ARM_POSITION_Z_OFFSET = defineArmDouble(builder, "positionZOffset", DEFAULT_ARM_POSITION_Z_OFFSET, -4.0, 6.0);
        RIGHT_ARM_LOOK_UP_Z_SHIFT = defineArmDouble(builder, "lookUpZShift", DEFAULT_ARM_LOOK_UP_Z_SHIFT, -4.0, 6.0);
        builder.pop();

        builder.push("leftArm");
        LEFT_ARM_PITCH_MULTIPLIER = defineArmDouble(builder, "pitchMultiplier", DEFAULT_ARM_PITCH_MULTIPLIER, 0.0, 2.0);
        LEFT_ARM_PITCH_OFFSET = defineArmDouble(builder, "pitchOffset", DEFAULT_ARM_PITCH_OFFSET, -2.0, 2.0);
        LEFT_ARM_PITCH_CURVE = defineArmDouble(builder, "pitchCurve", DEFAULT_ARM_PITCH_CURVE, 0.0, 4.0);
        LEFT_ARM_PITCH_CURVE_FLOOR = defineArmDouble(builder, "pitchCurveFloor", DEFAULT_ARM_PITCH_CURVE_FLOOR, 0.0, 1.0);
        LEFT_ARM_UPPER_PITCH_BIAS = defineArmDouble(builder, "upperPitchBias", DEFAULT_ARM_UPPER_PITCH_BIAS, -2.0, 2.0);
        LEFT_ARM_LOWER_PITCH_BIAS = defineArmDouble(builder, "lowerPitchBias", DEFAULT_ARM_LOWER_PITCH_BIAS, -2.0, 2.0);
        LEFT_ARM_POSITION_X_OFFSET = defineArmDouble(builder, "positionXOffset", DEFAULT_ARM_POSITION_X_OFFSET, -4.0, 4.0);
        LEFT_ARM_POSITION_Y_OFFSET = defineArmDouble(builder, "positionYOffset", DEFAULT_ARM_POSITION_Y_OFFSET, -4.0, 4.0);
        LEFT_ARM_POSITION_Z_OFFSET = defineArmDouble(builder, "positionZOffset", DEFAULT_ARM_POSITION_Z_OFFSET, -4.0, 6.0);
        LEFT_ARM_LOOK_UP_Z_SHIFT = defineArmDouble(builder, "lookUpZShift", DEFAULT_ARM_LOOK_UP_Z_SHIFT, -4.0, 6.0);
        builder.pop();

        SPEC = builder.build();
    }

    private ForgeClientConfig() {
    }

    private static ForgeConfigSpec.DoubleValue defineArmDouble(ForgeConfigSpec.Builder builder, String name, double defaultValue, double min, double max) {
        return builder.defineInRange(name, defaultValue, min, max);
    }
}
