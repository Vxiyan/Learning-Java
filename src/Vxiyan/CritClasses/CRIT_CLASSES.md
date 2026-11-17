# Vxiyan CritClasses Documentation

Documentation for the core classes in the `Vxiyan.CritClasses` package.

---

## Package Overview

| Class        | Type     | Purpose                                                                 |
|--------------|----------|-------------------------------------------------------------------------|
| `BaseEntity` | Abstract | Shared stats, health, combat & inventory for all game entities          |
| `Player`     | Concrete | Player character (extends `BaseEntity`) with special username handling  |
| `Event`      | Abstract | Base class for interactive events (choices, battles, shops)             |

---

## Class: `BaseEntity`

**File:** `~/Documents/Coding/Java/Learning Java/src/Vxiyan/CritClasses/BaseEntity.java`  
**Modifiers:** `public abstract`  
**Implements:** `Vxiyan.Inter.Entity`

### Description

Abstract foundation for every living (or combat-capable) entity in the game.  
Provides:

- Four core stats (`mana`, `strength`, `charisma`, `defense`)
- Health management (clamped between 0 and `maxHealth`)
- Simple attack helper
- Full inventory system (add / remove / use items)

### Core Fields

| Field            | Type                  | Visibility | Description                                      |
|------------------|-----------------------|------------|--------------------------------------------------|
| `mana`           | `double`              | protected  | Current mana                                     |
| `strength`       | `double`              | protected  | Current strength                                 |
| `charisma`       | `double`              | protected  | Current charisma                                 |
| `defense`        | `double`              | protected  | Current defense                                  |
| `healthPoints`   | `double`              | protected  | Current health (0 … maxHealth)                   |
| `maxHealth`      | `double`              | protected  | Maximum health                                   |
| `baseStat`       | `double`              | protected  | Value used to initialise the four core stats     |
| `inv`            | `Map<String, Integer>`| protected  | Inventory (item name → quantity)                 |

### ItemEffect Enum

| Constant            | Effect when used                     |
|---------------------|--------------------------------------|
| `HEAL`              | Restores health                      |
| `DAMAGE`            | Damages this entity                  |
| `STATUP_MANA`       | Increases mana                       |
| `STATUP_STRENGTH`   | Increases strength                   |
| `STATUP_CHARISMA`   | Increases charisma                   |
| `STATUP_DEFENSE`    | Increases defense                    |
| `DEFAULT`           | No effect (prints a message)         |

### Key Methods

| Method          | Signature                                                                 | Description |
|-----------------|---------------------------------------------------------------------------|-------------|
| Constructor     | `BaseEntity(double maxHealth, double baseStat)`                           | Full health + all stats = baseStat |
| `getHealthPoints()` | `double getHealthPoints()`                                            | Current health |
| `damage(double)`| `void damage(double dmg)`                                                 | Subtract health (min 0) |
| `heal(double)`  | `void heal(double hp)`                                                    | Add health (max = maxHealth) |
| `attack(...)`   | `void attack(double dmg, Entity target)`                                  | Deal damage to another entity |
| `itemGain(...)` | `void itemGain(String iName, int amt)`                                    | Add / increase item quantity |
| `itemLoss(...)` | `void itemLoss(String iName, int amt)`                                    | Remove quantity (throws if insufficient) |
| `getInventory()`| `Map<String, Integer> getInventory()`                                     | Live inventory map |
| `itemUse(...)`  | `void itemUse(HashMap<String, ItemEffect> map, String iName, int amtU, double amtG)` | Consume item + apply effect |
---

## Class: `Player`

**File:** `~/Documents/Coding/Java/Learning Java/src/Vxiyan/CritClasses/Player.java`  
**Modifiers:** `public`  
**Extends:** `BaseEntity`

### Description

Concrete player character.  
On creation it receives a username and is initialized with:

- `maxHealth = 100.0`
- `baseStat =` random integer in `[1, 100]` (via `Randy.randRange`)

Special usernames can override these values (useful for testing or easter eggs).

### Special Username Rules

| Username (case-insensitive) | Effect                                      |
|-----------------------------|---------------------------------------------|
| `null`                      | Treated as `"User"`                         |
| `"vxiyan"`                  | `maxHealth = 1000`, `baseStat = 1000`       |
| `"quinn"`                   | `maxHealth = 1`, `baseStat = -1`            |

### Fields

| Field   | Type     | Visibility | Description       |
|---------|----------|------------|-------------------|
| `Uname` | `String` | package    | Player's username |

### Constructor

```java
public Player(String Uname)
```

1. Calls `super(100.0, Randy.randRange(1, 100))`
2. Assigns the provided username
3. Applies special-case overrides if applicable
4. Logs creation via `Logger.println`

---

## Class: `Event`

**File:** `~/Documents/Coding/Java/Learning Java/src/Vxiyan/CritClasses/Event.java`  
**Modifiers:** `public abstract`

### Description

Abstract base class that models different kinds of interactive events.  
On construction it receives a name and an event type, then populates an internal `Map<String, Object>` with the appropriate data set for that type.

Subclasses must implement the abstract `handle()` method.

### Event Types (`Event.type`)

| Enum Constant   | Description                                      | Data Stored in `events` map                  |
|-----------------|--------------------------------------------------|----------------------------------------------|
| `BIPATH`        | Binary choice / correct-answer events            | `String` (the correct answer)                |
| `POLYPATH`      | Multi-option path / decision events              | `String[]` (descriptions of each option)     |
| `BASIC_BATTLE`  | Standard combat encounters                       | `String[]` of length 2 → `{hp, damage}`      |
| `ADV_BATTLE`    | Advanced combat encounters                       | `String[]` of length 3 → `{hp, damage, difficulty}` |
| `SHOP`          | Merchant / shop events                           | Currently only logs a message                |

### Key Fields

| Field         | Type                  | Visibility | Description                          |
|---------------|-----------------------|------------|--------------------------------------|
| `name`        | `String`              | protected  | Display name of the event instance   |
| `eventType`   | `Event.type`          | protected  | Category of this event               |
| `events`      | `Map<String, Object>` | protected  | Pre-loaded data for the event type   |

### Key Methods

| Method            | Signature                          | Description                                      |
|-------------------|------------------------------------|--------------------------------------------------|
| Constructor       | `Event(String name, type givenType)` | Initializes name, type, and populates `events` |
| `getName()`       | `String getName()`                 | Returns the event name                           |
| `getEventType()`  | `type getEventType()`              | Returns the event category                       |
| `handle()`        | `protected abstract void handle()` | **Must be implemented** by subclasses            |

### BIPATH Events (correct answers)

| Key                    | Correct Answer      |
|------------------------|---------------------|
| Wormhole               | jump over           |
| Crossroads             | dark                |
| Mimic_Chest            | open                |
| TrueMimicChest         | run away            |
| lever_wall             | pull                |
| eat_mushroom           | eat                 |
| rope_bridge            | find another way    |
| spaceship_ctrl_console | don't touch         |
| hooded_strangr_coin    | take                |
| large_red_buttn        | push                |
| Whirlpool              | jump in             |
| bright_berries         | don't eat           |
| unlocked_diary         | leave it            |
| glowing_crystal        | leave it            |
| mech_cat               | pet                 |
| ancient_totem          | grab                |
| portal                 | stay away           |
| snail                  | RUN                 |

### BASIC_BATTLE Stats

| Enemy            | HP  | Damage |
|------------------|-----|--------|
| goblin           | 50  | 10     |
| slime            | 30  | 5      |
| bandit           | 70  | 15     |
| wolf             | 40  | 12     |
| skeleton         | 45  | 14     |
| giant_spider     | 60  | 12     |
| zombie           | 55  | 8      |
| venomous_snake   | 25  | 18     |
| wild_boar        | 65  | 14     |
| rogue_knight     | 80  | 16     |

### ADV_BATTLE Stats

| Enemy             | HP  | Damage | Difficulty |
|-------------------|-----|--------|------------|
| dragon            | 200 | 35     | hard       |
| orc_warlord       | 150 | 25     | medium     |
| lich              | 120 | 40     | expert     |
| golem             | 250 | 20     | hard       |
| demon             | 180 | 30     | hard       |
| vampire_lord      | 160 | 32     | hard       |
| shadow_assassin   | 100 | 45     | expert     |
| minotaur          | 220 | 28     | medium     |
| hydra             | 300 | 38     | expert     |
| frost_giant       | 280 | 30     | hard       |
| goblin_scout      | 40  | 8      | easy       |
| young_wolf        | 35  | 6      | easy       |
| corrupted_slime   | 45  | 7      | easy       |
| bandit_recruit    | 50  | 10     | easy       |
| skeleton_guard    | 45  | 9      | easy       |

---

## Dependencies

- `Vxiyan.Inter.Entity` – interface implemented by `BaseEntity`
- `Vxiyan.Utils.Logger` – console output
- `Vxiyan.Utils.Randy` – random number generation (`randRange`)

---

## Notes / Future Work

- `Event.handle()` is still abstract and unfinished.
- Shop events currently only print a message.
- The `events` map in `Event` uses raw `Object` values; callers must cast based on `eventType`.
- `Player.Uname` is package-private; a public getter would be useful.
- `BaseEntity.itemUse` leaves zero-quantity entries in the inventory map (unlike `itemLoss`).
- Consider making `enteredPortal` private with a getter/setter.
