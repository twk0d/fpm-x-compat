package com.kaokod.fpm_x_compat.mixin;

import com.kaokod.fpm_x_compat.config.ConfigRegistry;
import com.kaokod.fpm_x_compat.integration.FirstPersonModelCompat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

/**
 * Hides head-related Essential cosmetics while FPM renders the local player's
 * body in first person, without affecting third-person or other players.
 */
@Pseudo
@Mixin(targets = "gg.essential.model.ModelInstance", remap = false)
public abstract class EssentialHeadHideMixin {
    @Unique
    private static Method fpm_x_compat$getCosmeticMethod;
    @Unique
    private static Method fpm_x_compat$getSlotMethod;
    @Unique
    private static Method fpm_x_compat$getSlotIdMethod;
    @Unique
    private static boolean fpm_x_compat$missingCosmeticMethod;
    @Unique
    private static boolean fpm_x_compat$missingSlotMethod;
    @Unique
    private static boolean fpm_x_compat$missingSlotIdMethod;

    @Inject(
        method = "render",
        at = @At("HEAD"),
        cancellable = true,
        require = 0
    )
    private void fpm_x_compat$hideHeadCosmetic(
        @Coerce Object matrixStack,
        @Coerce Object queue,
        List<?> cubes,
        @Coerce Object metadata,
        CallbackInfo ci
    ) {
        if (!FirstPersonModelCompat.isRenderingPlayerBody()) {
            return;
        }

        String slotName = fpm_x_compat$getCosmeticSlotName(this);
        if (slotName != null && fpm_x_compat$isIgnoredSlot(slotName)) {
            ci.cancel();
        }
    }

    @Unique
    private static String fpm_x_compat$getCosmeticSlotName(Object model) {
        if (model == null) {
            return null;
        }

        Object cosmetic = fpm_x_compat$getCosmetic(model);
        if (cosmetic == null) {
            return null;
        }

        Object slot = fpm_x_compat$getSlot(cosmetic);
        if (slot == null) {
            return null;
        }

        Object slotId = fpm_x_compat$getSlotId(slot);
        if (slotId instanceof String value) {
            return value.toLowerCase(Locale.ROOT);
        }
        return null;
    }

    @Unique
    private static Object fpm_x_compat$getCosmetic(Object model) {
        Method method = fpm_x_compat$getCosmeticMethod;
        if (method == null && !fpm_x_compat$missingCosmeticMethod) {
            method = fpm_x_compat$findNoArgMethod(model.getClass(), "getCosmetic");
            if (method == null) {
                fpm_x_compat$missingCosmeticMethod = true;
                return null;
            }
            fpm_x_compat$getCosmeticMethod = method;
        }

        return fpm_x_compat$invokeNoArg(method, model);
    }

    @Unique
    private static Object fpm_x_compat$getSlot(Object cosmetic) {
        Method method = fpm_x_compat$getSlotMethod;
        if (method == null && !fpm_x_compat$missingSlotMethod) {
            method = fpm_x_compat$findNoArgMethod(cosmetic.getClass(), "getSlot");
            if (method == null) {
                fpm_x_compat$missingSlotMethod = true;
                return null;
            }
            fpm_x_compat$getSlotMethod = method;
        }

        return fpm_x_compat$invokeNoArg(method, cosmetic);
    }

    @Unique
    private static Object fpm_x_compat$getSlotId(Object slot) {
        Method method = fpm_x_compat$getSlotIdMethod;
        if (method == null && !fpm_x_compat$missingSlotIdMethod) {
            method = fpm_x_compat$findNoArgMethod(slot.getClass(), "getId");
            if (method == null) {
                fpm_x_compat$missingSlotIdMethod = true;
                return null;
            }
            fpm_x_compat$getSlotIdMethod = method;
        }

        return fpm_x_compat$invokeNoArg(method, slot);
    }

    @Unique
    private static boolean fpm_x_compat$isIgnoredSlot(String slotName) {
        for (String ignoredSlot : ConfigRegistry.getConfig().getIgnoredAccessorySlots()) {
            if (ignoredSlot != null && !ignoredSlot.isBlank() && slotName.contains(ignoredSlot.toLowerCase(Locale.ROOT))) {
                return true;
            }
        }
        return false;
    }

    @Unique
    private static Method fpm_x_compat$findNoArgMethod(Class<?> owner, String name) {
        try {
            return owner.getMethod(name);
        } catch (ReflectiveOperationException | SecurityException ignored) {
            return null;
        }
    }

    @Unique
    private static Object fpm_x_compat$invokeNoArg(Method method, Object target) {
        if (method == null) {
            return null;
        }

        try {
            return method.invoke(target);
        } catch (ReflectiveOperationException | RuntimeException ignored) {
            return null;
        }
    }
}
