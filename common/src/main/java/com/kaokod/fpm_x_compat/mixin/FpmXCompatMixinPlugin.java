package com.kaokod.fpm_x_compat.mixin;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FpmXCompatMixinPlugin implements IMixinConfigPlugin {
    private static final Map<String, String[]> REQUIRED_CLASSES = Map.of(
            "com.kaokod.fpm_x_compat.mixin.PlayerAnimationProcessorMixin",
            new String[] { "dev.kosmx.playerAnim.core.impl.AnimationProcessor" },
            "com.kaokod.fpm_x_compat.mixin.PlayerAnimationApplierMixin",
            new String[] { "dev.kosmx.playerAnim.impl.animation.AnimationApplier" },
            "com.kaokod.fpm_x_compat.mixin.FirstPersonLogicHandlerMixin",
            new String[] { "dev.tr7zw.firstperson.LogicHandler" },
            "com.kaokod.fpm_x_compat.mixin.AccessoriesHeadHideMixin",
            new String[] { "io.wispforest.accessories.client.AccessoriesRenderLayer" },
            "com.kaokod.fpm_x_compat.mixin.CuriosHeadHideMixin",
            new String[] { "top.theillusivec4.curios.common.inventory.CurioStacksHandler" },
            "com.kaokod.fpm_x_compat.mixin.ArtifactsHiderMixin",
            new String[] { "artifacts.client.item.renderer.GenericArtifactRenderer" },
            "com.kaokod.fpm_x_compat.mixin.RelicsHiderMixin",
            new String[] {
                    "it.hurts.sskirillss.relics.client.renderer.items.PiglinMaskRenderer",
                    "it.hurts.sskirillss.relics.client.renderer.items.ChefHatRenderer",
                    "top.theillusivec4.curios.api.SlotContext"
            }
    );

    private final Map<String, Boolean> classAvailability = new HashMap<>();

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        String[] requiredClasses = REQUIRED_CLASSES.get(mixinClassName);
        if (requiredClasses == null) {
            return true;
        }

        for (String requiredClass : requiredClasses) {
            if (!isClassAvailable(requiredClass)) {
                return false;
            }
        }
        return true;
    }

    private boolean isClassAvailable(String className) {
        return classAvailability.computeIfAbsent(className, key -> {
            String resourceName = key.replace('.', '/') + ".class";
            ClassLoader classLoader = FpmXCompatMixinPlugin.class.getClassLoader();
            if (classLoader != null && classLoader.getResource(resourceName) != null) {
                return true;
            }

            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            return contextClassLoader != null && contextClassLoader.getResource(resourceName) != null;
        });
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
