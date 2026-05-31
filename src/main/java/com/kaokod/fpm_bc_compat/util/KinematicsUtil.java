package com.kaokod.fpm_bc_compat.util;

/**
 * Utility class for specialized kinematic and trigonometric calculations
 * used to align first-person animations.
 */
public class KinematicsUtil {

    /**
     * Clamps the camera pitch to avoid mathematical singularities at exactly 90 degrees.
     * 1.56731 radians is approximately 89.8 degrees.
     */
    public static float clampPitch(float rawPitch) {
        return Math.max(-1.56731f, Math.min(1.56731f, rawPitch));
    }

    /**
     * Calculates a non-linear rotation factor based on the camera pitch.
     * Uses a cosine curve to slow down rotation at extreme vertical angles.
     * 
     * @param pitch Radians of the current camera look angle.
     * @param power The aggressiveness of the curve (from config).
     * @param floor The minimum multiplier at 90 degrees (from config).
     * @return A factor between [floor] and 1.0.
     */
    public static float calculateTrigCurveFactor(float pitch, float power, float floor) {
        float baseCurve = (float) Math.pow(Math.cos(pitch), power);
        // Remap from [0, 1] to [floor, 1]
        return baseCurve * (1.0f - floor) + floor;
    }

    /**
     * Linearly interpolates (Lerp) a value towards a target.
     * Used for smooth transitions between upper and lower vision biases.
     */
    public static float lerp(float current, float target, float speed) {
        return current + (target - current) * speed;
    }

    /**
     * Combines multiple factors to calculate the final rotation offset for an arm.
     * 
     * @param pitch Clamped camera pitch in radians.
     * @param multiplier Configured multiplier.
     * @param curveFactor Calculated trig curve factor.
     * @param fixedOffset Base offset.
     * @param activeBias Smoothed directional bias.
     * @return Total rotation adjustment on the X axis.
     */
    public static float calculateFinalArmRotationX(float pitch, float multiplier, float curveFactor, float fixedOffset, float activeBias) {
        return (pitch * multiplier * curveFactor) + fixedOffset + activeBias;
    }
}
