package com.kaokod.fpm_x_compat.neoforge.mixin;

import artifacts.client.item.renderer.GenericArtifactRenderer;
import com.kaokod.fpm_x_compat.integration.FirstPersonModelCompat;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.resources.ResourceLocation;

/**
 * Specialized Mixin for Artifacts mod to hide specific items like Scarves in first-person view.
 */
@Mixin(value = GenericArtifactRenderer.class, remap = false)
public abstract class ArtifactsHiderMixin {

    @Shadow
    protected abstract ResourceLocation getTexture();

    @Inject(
        method = "render",
        at = @At("HEAD"),
        cancellable = true
    )
    private void conditionallyHideArtifact(
        ItemStack stack,
        LivingEntity entity,
        int slotIndex,
        PoseStack poseStack,
        MultiBufferSource multiBufferSource,
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
            ResourceLocation texture = this.getTexture();
            if (texture != null && texture.getPath().contains("scarf")) {
                ci.cancel();
            }
        }
    }
}
