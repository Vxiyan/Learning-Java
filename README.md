# Learning-Java

A personal project for learning Java by building a small text-based RPG / adventure game system.

---

## Overview

This repository contains the core systems for a simple turn-based adventure game.  
The focus is on practicing object-oriented design, inheritance, interfaces, enums, collections, and basic game logic.

**Current status:** Early development. Core entity and event systems are in place. The `Monster` class and main game loop are still being worked on.

---

## Project Structure

```
src/
├── Main.java                     # Entry point (currently empty)
└── Vxiyan/
    ├── CritClasses/              # Core game classes
    │   ├── BaseEntity.java       # Abstract base for all living entities
    │   ├── Player.java           # Player character
    │   ├── Event.java            # Abstract event system (choices, battles, shop)
    │   ├── Monster.java          # (WIP) Enemy class
    │   └── CRIT_CLASSES.md       # Detailed documentation of the CritClasses
    ├── Inter/
    │   └── Entity.java           # Interface for entities that can fight / hold items
    └── Utils/
        ├── Randy.java            # Random number & event helpers
        └── Loggy.java            # Simple console logging wrapper
```

---

## Key Components

### `Entity` (interface)
Defines the contract for anything that has health, can take/deal damage, and manage an inventory.

### `BaseEntity`
Abstract implementation of `Entity`. Provides:
- Health management (clamped between 0 and maxHealth)
- Four core stats: `mana`, `strength`, `charisma`, `defense`
- Inventory system (`itemGain`, `itemLoss`, `itemUse`)
- Basic attack helper

### `Player`
Extends `BaseEntity`.  
Special username easter eggs:
- `"vxiyan"` → very high stats
- `"quinn"` → deliberately terrible stats
- `null` / blank → defaults to `"User"`

### `Event`
Abstract class that models different kinds of encounters:
| Type          | Description                          |
|---------------|--------------------------------------|
| `BIPATH`      | Binary choice with a correct answer  |
| `POLYPATH`    | Multi-option decision paths          |
| `BASIC_BATTLE`| Simple combat (HP + damage)          |
| `ADV_BATTLE`  | Advanced combat (HP + damage + difficulty) |
| `SHOP`        | Merchant encounter (stub)            |

Subclasses are expected to implement the abstract `handle()` method.

### `Monster` *(Work in Progress)*
Currently a thin wrapper around `BaseEntity`. More behavior will be added later.

### Utilities
- **Randy** – random range generation and random map entry selection
- **Loggy** – thin wrapper around `System.out` for consistent logging

---

## Getting Started

### Requirements
- Java 21 or newer (the project uses modern features such as the simplified `main` method)

### Running
1. Open the project in your IDE of choice (IntelliJ IDEA is already configured).
2. Run `src/Main.java`.

> Note: `Main` is currently empty — the game loop has not been implemented yet.

---

## Documentation

Detailed class-level documentation for the CritClasses can be found in:

```
src/Vxiyan/CritClasses/CRIT_CLASSES.md
```

---

## Learning Goals

This project is intentionally written while learning Java. Areas being practiced:

- Interfaces & abstract classes
- Inheritance and polymorphism
- Enums
- Collections (`Map`, `HashMap`)
- Basic encapsulation and method design
- Organizing code into packages

---

## Notes

- The `Monster` class is still under active development — please ignore it for now.
- Some methods in `BaseEntity` (especially inventory handling) may receive further refinement.
- This is a personal learning repository, not a production-ready game.

---

## Author

**Vxiyan**  
[GitHub Profile](https://github.com/Vxiyan)
