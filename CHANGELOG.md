# Changelog

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