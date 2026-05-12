# Shadow Taxi

A 2D top-down taxi game built for **SWEN20003 Object Oriented Software Development** (University of Melbourne, Semester 2, 2024).

**Author:** Phuong Trang Tran

---

## Overview

Drive a taxi through traffic, pick up passengers, and drop them at their destinations before time runs out. Earn $500 to win. Avoid enemy cars and fireballs, collect power-ups, and manage the health of your taxi, driver, and passengers.

---

## Requirements

- Java 17+
- Maven 3.6+

---

## Build & Run

```bash
mvn compile
mvn exec:java -Dexec.mainClass="ShadowTaxi"
```

Or open the project in IntelliJ IDEA and run `ShadowTaxi.java` directly.

---

## Controls

| Key         | Action                          |
|-------------|---------------------------------|
| `ENTER`     | Advance screen / start game     |
| `SPACE`     | Return to home screen           |
| `UP`        | Drive taxi forward              |
| `LEFT`      | Move taxi left                  |
| `RIGHT`     | Move taxi right                 |
| `ESCAPE`    | Quit game                       |

---

## Gameplay

### Objective
Earn at least **$500** within **15,000 frames**.

### Screens
1. **Home Screen** — press `ENTER` to continue
2. **Player Info Screen** — type your name, press `ENTER` to start
3. **Game Screen** — main gameplay
4. **Game End Screen** — shows result and top 5 scores; press `SPACE` to restart

### Mechanics
- **Passengers** walk toward the taxi when it is nearby. Once picked up, a trip end flag appears — drive to it to complete the trip.
- **Earnings** depend on the passenger's priority level and the distance to the drop-off point. A penalty applies for taking too long.
- **Enemy cars** spawn randomly and shoot fireballs. Other cars also spawn and can collide with your taxi.
- **Taxi destruction** — when the taxi's health hits zero, the driver is ejected. You then control the driver on foot. Walk back to the taxi to re-enter and resume driving.
- **Weather** — rain reduces the priority of passengers who don't have umbrellas.

### Power-ups
| Power-up        | Effect                                              |
|-----------------|-----------------------------------------------------|
| Coin            | Reduces the current passenger's priority by 1 for 500 frames |
| Invincible      | Grants temporary invincibility to taxi and driver for 1000 frames |

### Health
Three health bars are displayed during gameplay:
- **Taxi** — destroyed by collisions and fireballs
- **Driver** — can be killed while ejected; ends the game
- **Passenger** — killed by collisions; ends the game

---

## Project Structure

```
ShadowTaxi_SWEN20003/
├── src/                  # Java source files
│   ├── ShadowTaxi.java   # Entry point and screen manager
│   ├── GameScreen.java   # Core game loop
│   ├── Taxi.java         # Player taxi logic
│   ├── Driver.java       # Driver entity
│   ├── Passenger.java    # Passenger entity and trip logic
│   ├── EnemyCar.java     # Enemy car with fireball shooting
│   ├── OtherCar.java     # Neutral traffic car
│   ├── Collision.java    # Collision detection handler
│   └── ...               # Other entities and utilities
├── res/                  # Game resources
│   ├── app.properties    # Game configuration
│   ├── message_en.properties  # UI text strings
│   ├── gameObjects.csv   # Initial positions of all game objects
│   ├── gameWeather.csv   # Weather schedule per frame
│   └── *.png             # Sprites
└── pom.xml               # Maven build configuration
```

---

## Configuration

All tunable values are in [res/app.properties](res/app.properties), including:

- Window size
- Taxi speed and health
- Trip earning rates and penalty rates
- Spawn rates for enemy and other cars
- Power-up durations
- Road lane positions

---

## Dependencies

- [Bagel](https://github.com/eleanor-em/bagel) `1.9.3` — Java game framework used in SWEN20003
- [LWJGL](https://www.lwjgl.org/) `3.3.1` — underlying OpenGL/audio bindings (managed by Maven)
