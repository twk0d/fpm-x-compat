package com.kaokod.fpm_x_compat.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;

/**
 * Utility class for fetching and analyzing vanilla Minecraft player data.
 * Simplifies access to the LocalPlayer and physical movement state.
 */
public class MinecraftPlayerUtil {

    /**
     * Safely retrieves the current client-side player instance.
     */
    public static LocalPlayer getClientPlayer() {
        Minecraft minecraftInstance = Minecraft.getInstance();
        return minecraftInstance == null ? null : minecraftInstance.player;
    }

    /**
     * Checks if the player is currently sneaking (crouching).
     */
    public static boolean isPlayerCrouching(net.minecraft.world.entity.player.Player player) {
        return player != null && player.isCrouching();
    }

    public static boolean isPlayerCrouching() {
        return isPlayerCrouching(getClientPlayer());
    }

    /**
     * Gets the camera pitch (vertical angle) in radians.
     */
    public static float getCameraPitchInRadians() {
        LocalPlayer player = getClientPlayer();
        if (player == null) {
            return 0.0f;
        }
        return (float) Math.toRadians(player.getXRot());
    }

    /**
     * Determines if the player has significant horizontal velocity.
     * Used to prevent leg animation glitches during static attacks.
     */
    public static boolean isPlayerInMotion(net.minecraft.world.entity.player.Player player) {
        if (player == null) {
            return false;
        }

        Vec3 movementDelta = player.getDeltaMovement();
        if (movementDelta == null) {
            return false;
        }

        // Horizontal velocity check with a small epsilon to ignore micro-movements
        return (movementDelta.x * movementDelta.x + movementDelta.z * movementDelta.z) > 1.0E-4;
    }

    public static boolean isPlayerInMotion() {
        return isPlayerInMotion(getClientPlayer());
    }
}
