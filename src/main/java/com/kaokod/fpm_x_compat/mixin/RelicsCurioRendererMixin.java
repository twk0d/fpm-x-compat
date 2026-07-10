package com.kaokod.fpm_x_compat.mixin;

import com.kaokod.fpm_x_compat.config.ConfigRegistry;
import com.kaokod.fpm_x_compat.integration.FirstPersonModelCompat;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.SlotContext;

import java.util.Locale;

@Pseudo
@Mixin(targets = "it.hurts.sskirillss.relics.client.renderer.items.items.CurioRenderer", remap = false)
public class RelicsCurioRendererMixin {
    @Inject(
        method = "render",
        at = @At("HEAD"),
        cancellable = true,
        require = 0
    )
    private <T extends LivingEntity, M extends EntityModel<T>> void fpm_x_compat$hideHeadRelic(
        ItemStack stack,
        SlotContext slotContext,
        PoseStack matrixStack,
        RenderLayerParent<T, M> renderLayerParent,
        MultiBufferSource renderTypeBuffer,
        int light,
        float limbSwing,
        float limbSwingAmount,
        float partialTicks,
        float ageInTicks,
        float netHeadYaw,
        float headPitch,
        CallbackInfo ci
    ) {
        if (!FirstPersonModelCompat.isRenderingPlayerBody() || slotContext == null) {
            return;
        }

        String slotName = slotContext.identifier();
        if (slotName == null) {
            return;
        }

        String normalizedSlotName = slotName.toLowerCase(Locale.ROOT);
        for (String ignoredSlot : ConfigRegistry.getConfig().getIgnoredAccessorySlots()) {
            if (ignoredSlot != null && !ignoredSlot.isBlank() && normalizedSlotName.contains(ignoredSlot.toLowerCase(Locale.ROOT))) {
                ci.cancel();
                return;
            }
        }
    }
}
