# Ninja Mod

A Fabric mod for Minecraft 26.2 that adds black ninja gear: armor, a jumping staff, throwing stars and a mini katana.

## Items

| Item | Recipe (crafting table) | What it does |
|---|---|---|
| **Ninja Hood, Jacket, Pants, Boots** | Iron armor shapes made of **black wool**, with **1 iron ingot** in the middle (boots: 2 wool on top, 2 iron ingots below) | Black armor. Chainmail durability, protection 13 (between chainmail 11 and iron 15). Wearing 2–3 pieces gives **Speed I**, all 4 give **Speed II**. Repair with black wool. |
| **Jumping Staff** | 2 sticks diagonally + 1 feather in the top right (spear shape) | Right-click to leap about 7 blocks up and 15 blocks forward. Must be on the ground or in water. No fall damage on landing. Cannot attack. Held like a spear. |
| **Throwing Star** (×4) | Iron ingot in the center, 4 iron nuggets around it | Right-click to throw. Flies and deals damage like an arrow from a fully drawn bow. 7-second cooldown. Can be picked up again. |
| **Mini Katana** | Iron ingot in the top right, stick in the bottom left | Same damage (6) and reach as an iron sword, attack speed 2. Slightly smaller in the hand. |

All recipes show up in the recipe book once you pick up the ingredients. In Creative mode, everything is in the *Combat* tab.

## Requirements

- Minecraft 26.2
- Fabric Loader 0.19.5 or newer
- Fabric API
- Java 25

## Build and run

Test in a development client:

```
.\gradlew runClient
```

Build the mod jar:

```
.\gradlew build
```

Then copy `build/libs/ninjamod-1.0.0.jar` into your `.minecraft/mods` folder and start the Fabric 26.2 profile.

## Tuning

| File | Values |
|---|---|
| `JumpStaffItem.java` | `JUMP_UP` (height) and `JUMP_FORWARD` (distance) |
| `ShurikenItem.java` | `COOLDOWN_TICKS` (20 ticks = 1 second) |
| `ModItems.java` | Armor protection and durability, katana damage and attack speed |
| `NinjaMod.java` | Speed effect from the armor |
