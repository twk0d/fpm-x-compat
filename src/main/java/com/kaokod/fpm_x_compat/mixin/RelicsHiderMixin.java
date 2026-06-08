package com.kaokod.fpm_x_compat.mixin;

import com.kaokod.fpm_x_compat.integration.FirstPersonModelCompat;
import it.hurts.sskirillss.relics.client.renderer.items.ChefHatRenderer;
import it.hurts.sskirillss.relics.client.renderer.items.PiglinMaskRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.SlotContext;

/**
 * Specialized Mixin for Relics mod to hide head accessories that use custom renderers.
 */
@Mixin(value = {PiglinMaskRenderer.class, ChefHatRenderer.class}, remap = false)
public class RelicsHiderMixin {

    @Inject(
        method = "render",
        at = @At("HEAD"),
        cancellable = true
    )
    private <E extends LivingEntity, EM extends EntityModel<E>> void conditionallyHideRelic(
        ItemStack stack,
        SlotContext slotContext,
        PoseStack poseStack,
        RenderLayerParent<E, EM> parent,
        MultiBufferSource bufferSource,
        int light,
        float limbSwing,
        float limbSwingAmount,
        float partialTicks,
        float ageInTicks,
        float netHeadYaw,
        float headPitch,
        CallbackInfo ci
    ) {
        if (FirstPersonModelCompat.isRenderingPlayerBody()) {
            ci.cancel();
        }
    }
}
