package com.kaokod.fpm_bc_compat.mixin;

import com.kaokod.fpm_bc_compat.integration.FirstPersonModelCompat;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import io.wispforest.accessories.client.AccessoriesRenderLayer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Mixin for the Accessories mod's rendering layer.
 * Specifically redirects the render call to allow skipping items in head-related slots
 * during first-person rendering.
 */
@Mixin(value = AccessoriesRenderLayer.class, remap = false)
public class AccessoriesHeadHideMixin {

    /**
     * Redirects the call to AccessoryRenderer.render.
     * Skips rendering for accessories in 'head', 'hat', 'face', 'mask', or 'goggles' slots
     * when the First Person Model is rendering the body.
     * 
     * Target corrected to use proper internal descriptors (slashes instead of dots).
     */
    @Redirect(
        method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
        at = @At(
            value = "INVOKE", 
            target = "Lio/wispforest/accessories/api/client/AccessoryRenderer;render(Lnet/minecraft/world/item/ItemStack;Lio/wispforest/accessories/api/slot/SlotReference;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/model/EntityModel;Lnet/minecraft/client/renderer/MultiBufferSource;IFFFFFF)V"
        )
    )
    private void conditionallyRenderAccessory(
        AccessoryRenderer renderer, 
        ItemStack stack, 
        SlotReference reference, 
        PoseStack poseStack, 
        EntityModel<? extends LivingEntity> model, 
        MultiBufferSource multiBufferSource, 
        int light, 
        float limbSwing, 
        float limbSwingAmount, 
        float partialTicks, 
        float ageInTicks, 
        float netHeadYaw, 
        float headPitch
    ) {
        // If we are in first-person body mode, perform visibility checks
        if (FirstPersonModelCompat.isRenderingPlayerBody()) {
            
            if (reference != null) {
                String slotName = reference.slotName().toLowerCase();
                
                // Comprehensive check for any head-related slots
                boolean isHeadRelated = slotName.contains("head") || 
                                        slotName.contains("hat") || 
                                        slotName.contains("face") || 
                                        slotName.contains("mask") || 
                                        slotName.contains("goggle");

                if (isHeadRelated) {
                    return; // Skip rendering for this specific accessory
                }
            }
        }
        
        // Otherwise, proceed with normal rendering
        renderer.render(stack, reference, poseStack, model, multiBufferSource, light, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
    }
}
