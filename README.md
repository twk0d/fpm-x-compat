# FPModel x Compat

A technical compatibility mod designed to fix model issues when using **First Person Model (FPM)** alongside mods like **Better Combat**, **Curios**, **accessories**, etc...

## 🎯 Our Mission
Initially born to bridge the gap within the **Better Combat** ecosystem, this project aims to evolve into a **universal integration hub** for **First Person Model**. 

We want to ensure that FPM works seamlessly with every major gameplay and animation mod in the Minecraft community. While our current focus is maintaining absolute stability for our existing integrations, we are eager to expand our horizons.

> **Have a mod you want to see integrated?**  
> Please [open an Issue](https://github.com/twk0d/firstpersonmodel-bettercombat-compat/issues) and tell us which mod integration you're looking for! We will evaluate and work on bringing it to life.

## 🤝 Compatibility & Integrations

This mod acts as a technical bridge, harmonizing interactions between several major mods. Below is the list of currently supported integrations and what we've implemented for each:

### ⚔️ Combat & Gameplay
*   **[Better Combat](https://modrinth.com/mod/better-combat)**
    *   **Kinematic Alignment:** Synchronizes arm rotations and body offsets with the camera pitch during complex attack animations.
    *   **Attack State Dispatcher:** Detects active combat phases to apply smooth rendering transitions.
*   **[Spell Engine](https://modrinth.com/mod/spell-engine)**
    *   **Casting Synchronization:** Extends kinematic fixes to spell-casting poses, ensuring your hands and staves point where you look.
*   **[Combat Roll](https://modrinth.com/mod/combat-roll)**
    *   **Movement Stabilization:** Smooths out body rendering during rolls to prevent camera clipping and jitter.

### 🎒 Accessories & Equipment
*   **[Accessories](https://modrinth.com/mod/accessories) & [Curios API](https://modrinth.com/mod/curios)**
    *   **Universal Slot Hiding:** Automatically hides head-related accessory slots (Head, Hat, Face, etc.) in first-person  while showing your accessories to other players.
    *   **Configurable Filter:** Includes an `ignoredAccessorySlots` option to allow users to customize which equipment slots are hidden in first person.
*   **[Relics](https://modrinth.com/mod/relics-mod)**
    *   **Custom Renderer Support:** Specific fixes for Relics items that use unique rendering systems, such as the **Piglin Mask** and **Chef's Hat**, ensuring they don't block the player's vision.
*   **[Artifacts](https://modrinth.com/mod/artifacts)**
    *   **Scarf Visibility Logic:** Implementation of a specialized texture-based filter to hide **Scarves** in first-person while allowing other necklaces and amulets to render normally.

## 💡 Compatibility Recommendations
If you are experiencing animation conflicts when using **Better Combat** alongside **EMF**-based texture packs (like *Fresh Animations Player* or *Fresh Moves*), we strongly recommend installing the **[Entity Player Compat](https://www.curseforge.com/minecraft/mc-mods/entity-player-compat)** mod. This bridge mod effectively synchronizes animation states, preventing the visual "glitching" or "stuttering" of caused by conflicting animation systems.

## Key Features
*   **Kinematic Alignment:** Automatically syncs arm rotation with camera pitch during attacks.
*   **Intelligent Head Hiding:** Dynamically hides the local player's head and accessories in first-person while maintaining full visibility for other players.
*   **Dynamic Configuration:** Real-time adjustment of offsets via an in-game menu (Powered by Cloth Config).

---

## 🛠 Project Roadmap & Planning

| Priority   | Task                                                     | Status              |
|:-----------|:---------------------------------------------------------|:--------------------|
| **High**   | Multi-language support (I18n)                            | ⏳ To Do             |
| **Medium** | Port to Fabric/Quilt                                     | 🔍 Under Review     |
| **Low**    | Custom animation profiles per weapon type                | 💡 Planned          |
| **Low**    | Portability to other versions                            | 💡 Planned          |

---

## Contributions
Contributions are highly welcome! Whether it's a bug fix, a new mod integration, or improved math for the kinematics util, feel free to open an **Issue** or submit a **Pull Request**.

<details>
<summary><b>🛠 Technical Setup & Build Instructions</b></summary>

### Prerequisites
*   Java 21 JDK
*   Git

### Setting up the Workspace
1.  Clone the repository: `git clone https://github.com/twk0d/firstpersonmodel-bettercombat-compat.git`
2.  Import the project into your preferred IDE (IntelliJ IDEA or VS Code) as a Gradle project.
3.  Dependencies (Better Combat, FPM, etc.) are handled automatically via Maven repositories (Modrinth, KosmX, Shedaniel).

### Building the Mod
To generate the final mod JAR, run:
```bash
./gradlew build
```
The output will be located in `build/libs/`.
</details>

*Developed by Kaokod to Everyone ❤*
