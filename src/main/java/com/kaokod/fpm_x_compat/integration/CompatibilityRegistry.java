package com.kaokod.fpm_x_compat.integration;

import com.kaokod.fpm_x_compat.FpmXCompatMod;
import net.minecraftforge.fml.ModList;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class CompatibilityRegistry {
    private static final MixinIntegration[] MIXIN_INTEGRATIONS = new MixinIntegration[] {
            new MixinIntegration(
                    "Curios",
                    new String[] { "curios" },
                    new String[] { "com.kaokod.fpm_x_compat.mixin.CuriosHeadHideMixin" },
                    new String[] { "top.theillusivec4.curios.common.inventory.CurioStacksHandler" },
                    true,
                    false
            ),
            new MixinIntegration(
                    "Artifacts",
                    new String[] { "artifacts" },
                    new String[] { "com.kaokod.fpm_x_compat.mixin.ArtifactsHiderMixin" },
                    new String[] { "artifacts.client.item.renderer.GenericArtifactRenderer" },
                    true,
                    false
            ),
            new MixinIntegration(
                    "Relics",
                    new String[] { "relics" },
                    new String[] { "com.kaokod.fpm_x_compat.mixin.RelicsCurioRendererMixin" },
                    new String[] {
                            "it.hurts.sskirillss.relics.client.renderer.items.items.CurioRenderer",
                            "top.theillusivec4.curios.api.SlotContext"
                    },
                    true,
                    false
            ),
            new MixinIntegration(
                    "Punchy",
                    new String[] { "punchy" },
                    new String[] { "com.kaokod.fpm_x_compat.mixin.PunchyConfigMixin" },
                    new String[] { "punchy.config.PunchyConfig" },
                    true,
                    false
            ),
            new MixinIntegration(
                    "Player Animator",
                    new String[] { "playeranimator" },
                    new String[] { "com.kaokod.fpm_x_compat.mixin.PlayerAnimationProcessorMixin" },
                    new String[] { "dev.kosmx.playerAnim.core.impl.AnimationProcessor" },
                    false,
                    false
            ),
            new MixinIntegration(
                    "Player Animator",
                    new String[] { "playeranimator" },
                    new String[] { "com.kaokod.fpm_x_compat.mixin.PlayerAnimationApplierMixin" },
                    new String[] { "dev.kosmx.playerAnim.impl.animation.AnimationApplier" },
                    false,
                    false
            ),
            new MixinIntegration(
                    "First Person Model",
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
        CombatRollCompat.init();
        FirstPersonModelCompat.init();
    }

    public static void logMixinOnlyIntegrations() {
        if (mixinIntegrationsLogged) return;
        mixinIntegrationsLogged = true;

        for (MixinIntegration integration : MIXIN_INTEGRATIONS) {
            if (!integration.logInStartup) {
                continue;
            }

            if (areClassesAvailable(integration.requiredClasses)) {
                FpmXCompatMod.MOD_LOGGER.info("[Bridge] {} mixin integration available.", integration.displayName);
            } else if (integration.allowLoadedModFallback && isAnyModLoaded(integration.modIds)) {
                FpmXCompatMod.MOD_LOGGER.info("[Bridge] {} detected; mixin target classes are not visible yet.", integration.displayName);
            } else {
                FpmXCompatMod.MOD_LOGGER.info("[Bridge] {} not detected, skipping mixin integration.", integration.displayName);
            }
        }
    }

    public static boolean shouldApplyMixin(String mixinClassName) {
        MixinIntegration integration = INTEGRATIONS_BY_MIXIN.get(mixinClassName);
        if (integration == null) {
            return true;
        }

        if (areClassesAvailable(integration.requiredClasses)) {
            return true;
        }

        return integration.allowLoadedModFallback && isAnyModLoaded(integration.modIds);
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

        if (ModList.get().isLoaded(modId)) {
            LOADED_MODS.add(modId);
            return true;
        }

        return false;
    }

    private static Map<String, MixinIntegration> createMixinRequirements() {
        Map<String, MixinIntegration> requirements = new HashMap<>();
        for (MixinIntegration integration : MIXIN_INTEGRATIONS) {
            for (String mixinClassName : integration.mixinClassNames) {
                requirements.put(mixinClassName, integration);
            }
        }
        return new HashMap<>(requirements);
    }

    private static final class MixinIntegration {
        private final String displayName;
        private final String[] modIds;
        private final String[] mixinClassNames;
        private final String[] requiredClasses;
        private final boolean logInStartup;
        private final boolean allowLoadedModFallback;

        private MixinIntegration(
                String displayName,
                String[] modIds,
                String[] mixinClassNames,
                String[] requiredClasses,
                boolean logInStartup,
                boolean allowLoadedModFallback
        ) {
            this.displayName = displayName;
            this.modIds = modIds;
            this.mixinClassNames = mixinClassNames;
            this.requiredClasses = requiredClasses;
            this.logInStartup = logInStartup;
            this.allowLoadedModFallback = allowLoadedModFallback;
        }
    }
}
