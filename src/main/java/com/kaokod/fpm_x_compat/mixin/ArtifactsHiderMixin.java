package com.kaokod.fpm_x_compat.mixin;

import com.kaokod.fpm_x_compat.integration.FirstPersonModelCompat;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Hides scarf-style Artifacts renderers in first-person view.
 */
@Pseudo
@Mixin(targets = "artifacts.client.item.renderer.GenericArtifactRenderer", remap = false)
public abstract class ArtifactsHiderMixin {

    @Shadow
    protected abstract ResourceLocation getTexture();

    @Inject(
        method = "render",
        at = @At("HEAD"),
        cancellable = true,
        require = 0
    )
    private void fpm_x_compat$conditionallyHideArtifact(
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
