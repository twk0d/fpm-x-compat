# FPM x Better Combat Compatibility (NeoForge 1.21.1)

A technical compatibility mod designed to fix rendering alignment issues when using **First Person Model (FPM)** alongside **Better Combat**, **Spell Engine**, and **Combat Roll**.

## 🎯 Our Mission
Initially born to bridge the gap within the **Better Combat** ecosystem, this project aims to evolve into a **universal integration hub** for **First Person Model**. 

We want to ensure that FPM works seamlessly with every major gameplay and animation mod in the Minecraft community. While our current focus is maintaining absolute stability for our existing integrations, we are eager to expand our horizons.

> **Have a mod you want to see integrated?**  
> Please [open an Issue](https://github.com/twk0d/firstpersonmodel-bettercombat-compat/issues) and tell us which mod integration you're looking for! We will evaluate and work on bringing it to life.

## Key Features
*   **Kinematic Alignment:** Automatically syncs arm rotation with camera pitch during attacks.
*   **Dynamic Configuration:** Real-time adjustment of offsets via an in-game menu (Powered by Cloth Config).
*   **Individual Arm Offsets:** Separate settings for left and right arms to support dual-wielding or asymmetrical weapons.
*   **Smooth Transitions:** Trigonometric curvature and biased smoothing for natural movement at extreme look angles.

## Dependencies
*   **NeoForge 1.21.1**
*   **First Person Model**
*   **Better Combat**
*   **PlayerAnimator** (Required for Mixins)
*   **Cloth Config API** (Required for GUI)

---

## 🛠 Project Roadmap & Planning

| Priority   | Task                                      | Status              |
|:-----------|:------------------------------------------|:--------------------|
| **High**   | Multi-language support (I18n)             | ⏳ To Do             |
| **Medium** | Port to Fabric/Quilt                      | 🔍 Under Review     |
| **Medium** | Compatibility with EMF with Fresh Moves   | 🔍 Under Review     |
| **Low**    | Custom animation profiles per weapon type | 💡 Planned          |
| **Low**    | Portability to other versions             | 💡 Planned          |

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
