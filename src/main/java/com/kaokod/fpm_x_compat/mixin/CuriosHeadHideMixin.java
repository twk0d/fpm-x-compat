package com.kaokod.fpm_x_compat.mixin;

import com.kaokod.fpm_x_compat.config.FpmXCompatConfig;
import com.kaokod.fpm_x_compat.integration.FirstPersonModelCompat;
import net.minecraft.core.NonNullList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.common.inventory.CurioStacksHandler;

/**
 * Mixin for CurioStacksHandler to hide all head-related Curios at the source.
 * This is much more stable than targeting render layers with lambdas.
 */
@Mixin(value = CurioStacksHandler.class, remap = false)
public abstract class CuriosHeadHideMixin {

    @Shadow
    public abstract String getIdentifier();

    @Inject(
        method = "getRenders",
        at = @At("RETURN"),
        cancellable = true
    )
    private void conditionallyModifyRenders(CallbackInfoReturnable<NonNullList<Boolean>> cir) {
        if (FirstPersonModelCompat.isRenderingPlayerBody()) {
            String slotName = this.getIdentifier().toLowerCase();
            
            for (String ignoredSlot : FpmXCompatConfig.CLIENT.ignoredAccessorySlots.get()) {
                if (slotName.contains(ignoredSlot.toLowerCase())) {
                    NonNullList<Boolean> original = cir.getReturnValue();
                    NonNullList<Boolean> hidden = NonNullList.withSize(original.size(), false);
                    cir.setReturnValue(hidden);
                    return;
                }
            }
        }
    }
}
