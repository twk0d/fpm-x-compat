package com.kaokod.fpm_x_compat.util;

/**
 * Utility class for specialized kinematic and trigonometric calculations
 * used to align first-person animations.
 */
public class KinematicsUtil {

    private static final float HALF_PI = (float) (Math.PI / 2.0);

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
     */
    public static float calculateTrigCurveFactor(float pitch, float power, float floor) {
        float baseCurve = (float) Math.pow(Math.cos(pitch), power);
        return baseCurve * (1.0f - floor) + floor;
    }

    /**
     * Linearly interpolates (Lerp) a value towards a target.
     */
    public static float lerp(float current, float target, float speed) {
        return current + (target - current) * speed;
    }

    /**
     * Calculates dynamic depth (Z) based on how much the player is looking UP.
     * Scales linearly from 0 at the horizon to the full [lookUpZShift] at -90 degrees.
     * 
     * @param pitch Radians of the current camera look angle.
     * @param baseZ Initial depth offset from config.
     * @param lookUpZShift Maximum extra depth to apply at -90 degrees.
     * @return The dynamic Z coordinate.
     */
    public static float calculateDynamicZOffset(float pitch, float baseZ, float lookUpZShift) {
        // We only care about looking UP (negative pitch)
        if (pitch >= 0) return baseZ;
        
        // Scale factor: 0.0 at horizon, 1.0 at -90 degrees
        float scale = Math.abs(pitch) / HALF_PI;
        return baseZ + (lookUpZShift * scale);
    }

    /**
     * Combines multiple factors to calculate the final rotation offset for an arm.
     */
    public static float calculateFinalArmRotationX(float pitch, float multiplier, float curveFactor, float fixedOffset, float activeBias) {
        return (pitch * multiplier * curveFactor) + fixedOffset + activeBias;
    }
}
