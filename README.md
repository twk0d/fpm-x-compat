# FPModel x Combat

A technical compatibility mod designed to fix rendering alignment issues when using **First Person Model (FPM)** alongside **Better Combat**, **Spell Engine**, **Combat Roll**, **EMF** and accessories.

## 🎯 Our Mission
Initially born to bridge the gap within the **Better Combat** ecosystem, this project aims to evolve into a **universal integration hub** for **First Person Model**. 

We want to ensure that FPM works seamlessly with every major gameplay and animation mod in the Minecraft community. While our current focus is maintaining absolute stability for our existing integrations, we are eager to expand our horizons.

> **Have a mod you want to see integrated?**  
> Please [open an Issue](https://github.com/twk0d/firstpersonmodel-bettercombat-compat/issues) and tell us which mod integration you're looking for! We will evaluate and work on bringing it to life.

## 🤝 Compatible Mods
This mod acts as a bridge, harmonizing the interactions between the following mods:
*   **[First Person Model (FPM)](https://modrinth.com/mod/first-person-model)** - The core rendering engine.
*   **[Better Combat](https://modrinth.com/mod/first-person-model)** - Dynamic attack animations.
*   **[Entity Model Features (EMF)](https://modrinth.com/mod/entity-model-features)** - Custom player models and animations (e.g., *Fresh Moves*).
*   **[Spell Engine](https://modrinth.com/mod/spell-engine)** - Casting and magic animations.
*   **[Combat Roll](https://modrinth.com/mod/combat-roll)** - Evasion and rolling maneuvers.
*   **[Accessories](https://modrinth.com/mod/accessories)** - Proper hiding of head-related accessories in first-person.

## Key Features
*   **Kinematic Alignment:** Automatically syncs arm rotation with camera pitch during attacks.
*   **Intelligent Head Hiding:** Dynamically hides the local player's head and accessories in first-person (including EMF custom models) while maintaining full visibility for other players.
*   **EMF Synchronization:** Pauses custom EMF animations during combat to ensure kinematic offsets take priority.
*   **Dynamic Configuration:** Real-time adjustment of offsets via an in-game menu (Powered by Cloth Config).

---

## 🛠 Project Roadmap & Planning

| Priority   | Task                                                     | Status              |
|:-----------|:---------------------------------------------------------|:--------------------|
| **High**   | Multi-language support (I18n)                            | ⏳ To Do             |
| **Medium** | Compatibility between two hand weapons bc hands with EMF | 🔍 Under Review     |
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
