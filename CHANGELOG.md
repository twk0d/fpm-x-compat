# Changelog

# 1.2.0

- **Punchy Compatibility:** Added optional integration for **Punchy** on Fabric and NeoForge.
- **First Person Model Priority:** When Punchy is disabled, its FPM activation handler no longer blocks First Person Model body rendering.
- **Animation State Restoration:** Restores Better Combat and Spell Engine first-person animation state after Punchy is disabled, preventing Punchy's already-registered hooks from keeping external animation mods suppressed.
- **FPM Hide Angle Support:** When Punchy's FPM hide angle is set to `never`, Punchy is virtually disabled through its `isModEnabled` boolean while in first person, letting FPM and Better Combat animation handling keep priority.
- **Mixin Safety:** Punchy mixins are gated through `CompatibilityRegistry`, use optional `@Pseudo` targets with `remap = false`, and avoid fragile Minecraft descriptors in injection annotations.

# 1.1.1

- **Artifacts Fabric Support:** Moved the Artifacts scarf-hiding integration to common code so it can run on both Fabric and NeoForge.

# 1.1.0

- **Essential Compatibility:** Added optional integration for **Essential** cosmetics on both Fabric and NeoForge.
- **First-Person Cosmetic Hiding:** Essential head-related cosmetic slots now respect `ignoredAccessorySlots`, hiding them only while FPM renders the local player's body in first person.
- **Integration Registry:** Centralized runtime bridge initialization and optional mixin detection in a shared `CompatibilityRegistry`, so startup diagnostics and mixin gating now use the same source of truth.
- **Detection Fixes:** Corrected Combat Roll package detection for current versions and improved optional mixin detection for loader-specific integrations and Essential's staged loader.
- **Mixin Stability:** Moved the Essential integration to the common mixin config with an optional `@Pseudo` target and `remap = false`, avoiding hard runtime/build dependencies when Essential is not installed.

# 1.0.0

- **Major Overhaul:** Complete rewrite of the mod to develop with Fabric and Neoforge. The new architecture allows a better development for both versions.
- **Versioning:** Patronized versioning to follow semantic versioning (MAJOR.MINOR.PATCH).

# 0.1.1
- **Configurable Accessory Hiding:** Added a new configuration option `ignoredAccessorySlots` in `fpm_bc_compat-client.toml`. Users can now customize which accessory slots (e.g., "head", "hat", "mask") are hidden in first-person mode to prevent camera obstruction.
- **Universal Curios Integration:** Implemented a source-level hiding mechanism for the **Curios API**. This ensures that all head-related accessories from any Curios-compatible mod are automatically hidden when the first-person body is rendered.
- **Relics Mod Compatibility:** Added specialized fixes for the **Relics** mod to hide items that use custom renderers, specifically targeting the **Piglin Mask** and **Chef's Hat**.
- **Artifacts Mod Compatibility:** Added a targeted fix for **Artifacts** to hide **Scarves** in first-person view. The logic uses texture-path detection to hide only the scarves while allowing other necklace-type items to remain visible.
- **Enhanced Hiding Logic:** Refactored accessory hiding to use more stable injection points, improving compatibility across different mod versions and reducing the risk of Mixin application errors.
- **Removed EMF Support:** Due to the existence of **Entity Player Compat** mod that solve the EMF compatibility issues.
- **Files Renamed:** Files renamed to reflect new project name.

# 0.1.0
- Updated README to reflect current mod compatibility.
- Update project directory structure / mod id / artifact
- changed neoforge version to 21.1.219 instead of 21.1.221 to support more clients.
- Updated README tasks board with a new task: Support Two hands weapons of bc with EMF models.

# 0.0.5
- **Intelligent Head Hiding:** Added a context-aware visibility system that hides the local player's head and accessories in first-person (supporting custom EMF bone names) while keeping them fully visible for other players and in third-person view.
- **Multiplayer Safety:** Refactored all rendering offsets (Torso Z-axis, leg stabilization) to apply exclusively to the local player in first-person, preventing visual glitches on other players.
- **Enhanced Logging System:** Added a structured startup banner and unified diagnostic prefixes (`[Bridge]`, `[Lifecycle]`) for better troubleshooting.
- **Improved Initialization:** Implemented explicit initialization for all compatibility bridges to provide immediate feedback on mod detection during startup.

# 0.0.4
- Integrated with Entity Model Features (EMF).
- Implemented a dynamic pause condition for EMF animations during combat actions (attack/roll).
- Broadened model part detection to handle both camelCase and snake_case naming conventions.
- Refactored Better Combat, Spell Engine, and Combat Roll bridges for universal player entity support.
- Optimized state detection logic to improve reliability across all rendered player models.
- Enhanced logging system with a structured startup banner and unified diagnostic prefixes (`[Bridge]`, `[Lifecycle]`).
- Implemented explicit initialization for all compatibility bridges to provide immediate feedback on mod detection.

# 0.0.3
- Compatibility with Accessories mod (Curious and Accessories head items).
- Dependency update to NeoForge 1.21.1 using Modrinth Maven repositories.
- Updated Pipeline to automatically download resource packs automatically.
- Changed default config values.

# 0.0.2
- Implementation of dynamic Z-axis scaling to prevent camera arm clipping.
- Optimization of reflection handles (Caching).
- Stability improvements and fine-tuning of default alignment values.
- fix visual bugs between curious/accessories head items rendering on head.

# 0.0.1
- Initial technical release.
- Implementation of dynamic kinematics for arm alignment.
- Added in-game configuration menu via Cloth Config.
- Individual arm offset support (Left/Right).
- Trigonometric curvature and biased smoothing for extreme look angles.
