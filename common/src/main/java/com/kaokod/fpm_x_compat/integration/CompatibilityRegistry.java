package com.kaokod.fpm_x_compat.integration;

import com.kaokod.fpm_x_compat.FpmXCompatMod;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Central registry for optional compatibility integrations.
 * Runtime bridges and mixin-only integrations are listed here so startup logs
 * and mixin gating use the same source of truth.
 */
public final class CompatibilityRegistry {
    private static final MixinIntegration[] MIXIN_INTEGRATIONS = new MixinIntegration[] {
            new MixinIntegration(
                    "Accessories",
                    LoaderScope.ANY,
                    new String[] { "accessories" },
                    new String[] { "com.kaokod.fpm_x_compat.mixin.AccessoriesHeadHideMixin" },
                    new String[] { "io.wispforest.accessories.client.AccessoriesRenderLayer" },
                    true,
                    false
            ),
            new MixinIntegration(
                    "Curios",
                    LoaderScope.NEOFORGE,
                    new String[] { "curios" },
                    new String[] { "com.kaokod.fpm_x_compat.neoforge.mixin.CuriosHeadHideMixin" },
                    new String[] { "top.theillusivec4.curios.common.inventory.CurioStacksHandler" },
                    true,
                    false
            ),
            new MixinIntegration(
                    "Artifacts",
                    LoaderScope.NEOFORGE,
                    new String[] { "artifacts" },
                    new String[] { "com.kaokod.fpm_x_compat.neoforge.mixin.ArtifactsHiderMixin" },
                    new String[] { "artifacts.client.item.renderer.GenericArtifactRenderer" },
                    true,
                    false
            ),
            new MixinIntegration(
                    "Relics",
                    LoaderScope.NEOFORGE,
                    new String[] { "relics" },
                    new String[] { "com.kaokod.fpm_x_compat.neoforge.mixin.RelicsHiderMixin" },
                    new String[] {
                            "it.hurts.sskirillss.relics.client.renderer.items.PiglinMaskRenderer",
                            "it.hurts.sskirillss.relics.client.renderer.items.ChefHatRenderer",
                            "top.theillusivec4.curios.api.SlotContext"
                    },
                    true,
                    false
            ),
            new MixinIntegration(
                    "Essential",
                    LoaderScope.ANY,
                    new String[] { "essential", "essential-loader" },
                    new String[] { "com.kaokod.fpm_x_compat.mixin.EssentialHeadHideMixin" },
                    new String[] {
                            "gg.essential.model.ModelInstance",
                            "gg.essential.network.cosmetics.Cosmetic",
                            "gg.essential.mod.cosmetics.CosmeticSlot"
                    },
                    true,
                    true
            ),
            new MixinIntegration(
                    "Player Animator",
                    LoaderScope.ANY,
                    new String[] { "playeranimator" },
                    new String[] { "com.kaokod.fpm_x_compat.mixin.PlayerAnimationProcessorMixin" },
                    new String[] { "dev.kosmx.playerAnim.core.impl.AnimationProcessor" },
                    false,
                    false
            ),
            new MixinIntegration(
                    "Player Animator",
                    LoaderScope.ANY,
                    new String[] { "playeranimator" },
                    new String[] { "com.kaokod.fpm_x_compat.mixin.PlayerAnimationApplierMixin" },
                    new String[] { "dev.kosmx.playerAnim.impl.animation.AnimationApplier" },
                    false,
                    false
            ),
            new MixinIntegration(
                    "First Person Model",
                    LoaderScope.ANY,
                    new String[] { "firstperson" },
                    new String[] { "com.kaokod.fpm_x_compat.mixin.FirstPersonLogicHandlerMixin" },
                    new String[] { "dev.tr7zw.firstperson.LogicHandler" },
                    false,
                    false
            )
    };

    private static final Map<String, MixinIntegration> INTEGRATIONS_BY_MIXIN = createMixinRequirements();
    private static final Set<String> AVAILABLE_CLASSES = ConcurrentHashMap.newKeySet();
    private static final Set<String> LOADED_MODS = ConcurrentHashMap.newKeySet();
    private static boolean mixinIntegrationsLogged = false;

    private CompatibilityRegistry() {
    }

    public static void initRuntimeBridges() {
        BetterCombatCompat.init();
        SpellEngineCompat.init();
        CombatRollCompat.init();
        FirstPersonModelCompat.init();
    }

    public static void logMixinOnlyIntegrations() {
        if (mixinIntegrationsLogged) return;
        mixinIntegrationsLogged = true;

        for (MixinIntegration integration : MIXIN_INTEGRATIONS) {
            if (!integration.logInStartup() || !integration.loaderScope().matches()) {
                continue;
            }

            if (areClassesAvailable(integration.requiredClasses())) {
                FpmXCompatMod.MOD_LOGGER.info("[Bridge] {} mixin integration available.", integration.displayName());
            } else if (integration.allowLoadedModFallback() && isAnyModLoaded(integration.modIds())) {
                FpmXCompatMod.MOD_LOGGER.info("[Bridge] {} detected; mixin target classes are not visible yet.", integration.displayName());
            } else {
                FpmXCompatMod.MOD_LOGGER.info("[Bridge] {} not detected, skipping mixin integration.", integration.displayName());
            }
        }
    }

    public static String[] requiredClassesForMixin(String mixinClassName) {
        MixinIntegration integration = INTEGRATIONS_BY_MIXIN.get(mixinClassName);
        return integration == null ? null : integration.requiredClasses();
    }

    public static boolean shouldApplyMixin(String mixinClassName) {
        MixinIntegration integration = INTEGRATIONS_BY_MIXIN.get(mixinClassName);
        if (integration == null) {
            return true;
        }

        if (!integration.loaderScope().matches()) {
            return false;
        }

        if (areClassesAvailable(integration.requiredClasses())) {
            return true;
        }

        return integration.allowLoadedModFallback() && isAnyModLoaded(integration.modIds());
    }

    public static boolean areClassesAvailable(String[] classNames) {
        for (String className : classNames) {
            if (!isClassAvailable(className)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isClassAvailable(String className) {
        if (AVAILABLE_CLASSES.contains(className)) {
            return true;
        }

        if (isResourceAvailable(className)) {
            AVAILABLE_CLASSES.add(className);
            return true;
        }

        return false;
    }

    private static boolean isResourceAvailable(String className) {
        String resourceName = className.replace('.', '/') + ".class";

        ClassLoader classLoader = CompatibilityRegistry.class.getClassLoader();
        if (classLoader != null && classLoader.getResource(resourceName) != null) {
            return true;
        }

        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        if (contextClassLoader != null && contextClassLoader.getResource(resourceName) != null) {
            return true;
        }

        return ClassLoader.getSystemResource(resourceName) != null;
    }

    private static boolean isAnyModLoaded(String[] modIds) {
        for (String modId : modIds) {
            if (isModLoaded(modId)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isModLoaded(String modId) {
        if (LOADED_MODS.contains(modId)) {
            return true;
        }

        if (isFabricModLoaded(modId) || isNeoForgeModLoaded(modId)) {
            LOADED_MODS.add(modId);
            return true;
        }

        return false;
    }

    private static boolean isFabricModLoaded(String modId) {
        try {
            Class<?> fabricLoaderClass = Class.forName("net.fabricmc.loader.api.FabricLoader");
            Object fabricLoader = fabricLoaderClass.getMethod("getInstance").invoke(null);
            return (Boolean) fabricLoaderClass.getMethod("isModLoaded", String.class).invoke(fabricLoader, modId);
        } catch (ReflectiveOperationException | LinkageError | RuntimeException ignored) {
            return false;
        }
    }

    private static boolean isNeoForgeModLoaded(String modId) {
        try {
            Class<?> modListClass = Class.forName("net.neoforged.fml.ModList");
            Object modList = modListClass.getMethod("get").invoke(null);
            return (Boolean) modListClass.getMethod("isLoaded", String.class).invoke(modList, modId);
        } catch (ReflectiveOperationException | LinkageError | RuntimeException ignored) {
            return false;
        }
    }

    private static Map<String, MixinIntegration> createMixinRequirements() {
        Map<String, MixinIntegration> requirements = new HashMap<>();
        for (MixinIntegration integration : MIXIN_INTEGRATIONS) {
            for (String mixinClassName : integration.mixinClassNames()) {
                requirements.put(mixinClassName, integration);
            }
        }
        return Map.copyOf(requirements);
    }

    private enum LoaderScope {
        ANY,
        FABRIC,
        NEOFORGE;

        private boolean matches() {
            return switch (this) {
                case ANY -> true;
                case FABRIC -> isClassAvailable("net.fabricmc.loader.api.FabricLoader");
                case NEOFORGE -> isClassAvailable("net.neoforged.fml.ModList");
            };
        }
    }

    private record MixinIntegration(
            String displayName,
            LoaderScope loaderScope,
            String[] modIds,
            String[] mixinClassNames,
            String[] requiredClasses,
            boolean logInStartup,
            boolean allowLoadedModFallback
    ) {
    }
}
