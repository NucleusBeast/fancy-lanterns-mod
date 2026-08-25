# Fancy Lanterns

Fancy Lanterns is a NeoForge mod for Minecraft that turns lanterns into small, configurable area-of-effect beacons.
Place a lantern and it periodically applies its associated vanilla status effect to nearby players and shows a matching
particle effect.

## Version

- Mod version: `1.0`
- Minecraft: `1.21.0`
- NeoForge: `21.0.167`
- Java: `21`
- License: [MIT](LICENSE)

## What the mod adds

The mod adds 12 lantern variants:

| Lantern           | Effect                                                    |
|-------------------|-----------------------------------------------------------|
| Hasty Lantern     | Haste                                                     |
| Healthy Lantern   | Regeneration                                              |
| Absorby Lantern   | Absorption                                                |
| Saturaty Lantern  | Saturation                                                |
| Nighty Lantern    | Night Vision                                              |
| Lucky Lantern     | Luck                                                      |
| Jumpy Lantern     | Jump Boost                                                |
| Speedy Lantern    | Speed                                                     |
| Fiery Lantern     | Fire Resistance                                           |
| Strengthy Lantern | Strength                                                  |
| Breathy Lantern   | Water Breathing                                           |
| Murky Lantern     | Fizzled/inactive lantern used for crafting and relighting |

Each effect lantern has four levels. Higher levels increase the effect radius and can amplify the effect. By default,
levels 1–3 have limited uses; level 4 is permanent.

## How to use

1. Craft a **Murky Lantern** from a vanilla lantern and a water bucket.
2. Relight it by right-clicking the placed Murky Lantern with the required item:

    - Healthy: Golden Apple
    - Strengthy: Iron Sword
    - Absorby: Gold Block
    - Speedy: Iron Boots
    - Jumpy: Rabbit Hide
    - Nighty: Soul Torch
    - Lucky: Rabbit Foot
    - Hasty: Iron Pickaxe
    - Saturaty: Apple
    - Fiery: Flint and Steel
    - Breathy: Nautilus Shell

3. Place the resulting effect lantern. It periodically applies its effect to entities in range.
4. Upgrade a placed lantern by right-clicking it with one of the configured upgrade materials:

    - Level 1 → 2: Iron Ingot or Iron Block
    - Level 2 → 3: Gold Ingot or Iron Block
    - Level 3 → 4: Nether Star or Netherite Scrap

   One upgrade material is consumed per upgrade. Sneak-right-click a placed lantern with an empty hand to preview its
   effect radius for a few seconds.

### Muting a lantern

Right-click a placed lantern with any wool. The lantern becomes muted and its particle and sound effects are suppressed.
By default, muting does not disable the status effect itself. Wool is consumed when the lantern is muted.

### Fizzling and relighting

Levels 1–3 have a limited number of effect triggers. When their uses run out, they turn into a Murky Lantern. By
default, the lantern level is retained when it fizzles, and the lantern can be relit using the matching relight item
above.

## Configuration

The common configuration is generated at:

```text
config/fancy_lanterns-common.toml
```

The default values are:

| Setting                           |          Default |
|-----------------------------------|-----------------:|
| Regular effect range              |         2 blocks |
| Level 2 effect range              |         4 blocks |
| Level 3 effect range              |         8 blocks |
| Level 4 effect range              |        16 blocks |
| Uses for levels 1–3               | 32 triggers each |
| Effect duration values            |  16 seconds each |
| Lanterns fizzle out               |          Enabled |
| Retain level after fizzling       |          Enabled |
| Amplify effect by level           |          Enabled |
| Muting disables the status effect |         Disabled |
| Effect particles                  |          Enabled |
| Effect sounds                     |          Enabled |
| Sneak-click range preview         |          Enabled |

## Installation

1. Install Minecraft `1.21.0` and NeoForge `21.0.167`.
2. Download the Fancy Lanterns `.jar` from the repository's Releases page.
3. Place the `.jar` in your Minecraft `mods` folder.
4. Launch the NeoForge profile.

JEI is optional, but when installed the mod adds in-game information for relighting, upgrading, and muting lanterns.

## Building from source

This repository uses the Gradle wrapper:

```bash
./gradlew build
```

On Windows PowerShell:

```powershell
.\gradlew.bat build
```

The built mod jar is written to `build/libs/`.

## Mod ID

```text
fancy_lanterns
```

## License

Fancy Lanterns is licensed under the [MIT License](LICENSE).
