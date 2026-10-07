# DimensionBlocker

**DimensionBlocker** is a server-side NeoForge mod for Minecraft that gives server owners and administrators control over access to **The Nether** and **The End**.

Lock dimensions until you're ready to open them, schedule automatic unlocks with real-world timers, and allow server staff to bypass locked dimensions.

## ✨ Features

* 🔒 Lock **The Nether**
* 🔒 Lock **The End**
* 🔓 Unlock either dimension at any time
* ⏱️ Set real-world unlock timers
* 📅 Supports days, hours, minutes, and seconds
* 🕐 Supports combined timers such as `2d12h30m`
* 💾 Timers and dimension states persist through server restarts
* ⚡ Dimensions automatically unlock when their timer expires
* 👑 Gamemasters/OPs can bypass dimension locks
* 💬 Players receive a message when attempting to enter a locked dimension
* ⏳ Players can see how long remains until a timed unlock
* 🖥️ Designed for dedicated servers
* 🧩 Lightweight and focused on dimension access control

## 🎮 Commands

All DimensionBlocker commands require the appropriate server permissions.

### The Nether

Lock The Nether:

```text
/dimensionblocker nether off
```

Unlock The Nether:

```text
/dimensionblocker nether on
```

Set a timer:

```text
/dimensionblocker nether timer 30s
```

### The End

Lock The End:

```text
/dimensionblocker end off
```

Unlock The End:

```text
/dimensionblocker end on
```

Set a timer:

```text
/dimensionblocker end timer 2d
```

## ⏱️ Timer Formats

DimensionBlocker supports the following time units:

| Unit | Meaning | Example |
| ---- | ------- | ------- |
| `s`  | Seconds | `30s`   |
| `m`  | Minutes | `10m`   |
| `h`  | Hours   | `2h`    |
| `d`  | Days    | `2d`    |

You can combine multiple units:

```text
2d12h30m
```

Other examples:

```text
30s
10m
2h
1d
3d12h
6h30m
2d12h30m
```

Timers use **real-world elapsed time**, rather than Minecraft ticks.

## 🔒 How Dimension Locking Works

When a dimension is locked, normal players cannot travel into that dimension.

For example:

```text
/dimensionblocker nether off
```

will lock The Nether.

A player attempting to enter will receive a message explaining that the dimension is currently locked.

If a timer has been configured, the player will also see the remaining time.

Example:

```text
The Nether is currently locked.
Unlocks in 1 day, 4 hours, 32 minutes.
```

## 👑 Staff Bypass

Players with the required **Gamemaster/OP permission** can bypass DimensionBlocker's dimension locks.

This allows server administrators and staff to enter locked dimensions for:

* Server administration
* Testing
* Building
* Moderation
* World preparation
* Event setup

## 💾 Persistence

DimensionBlocker stores its dimension states and timers using Minecraft's server saved-data system.

This means settings survive:

* Server restarts
* Server shutdowns
* Crashes followed by a restart

For example, if you set:

```text
/dimensionblocker end timer 2d
```

and restart the server after several hours, the timer continues from the remaining real-world time.

## 🛠️ Server Use Cases

DimensionBlocker can be useful for servers that want controlled progression.

### Server Launch

Start the server with The Nether and The End locked:

```text
The Nether 🔒
The End 🔒
```

Players can explore the Overworld while the server develops.

### Nether Opening Event

Schedule the Nether to open later:

```text
/dimensionblocker nether timer 2d
```

The Nether automatically unlocks after two real-world days.

### End Game Progression

Keep The End locked until the server reaches a certain stage:

```text
/dimensionblocker end off
```

Then open it when you're ready:

```text
/dimensionblocker end on
```

## 📦 Installation

1. Install **Minecraft 1.21.11**.
2. Install the required **NeoForge** version.
3. Download the latest DimensionBlocker release.
4. Place the `.jar` file into your server's `mods` folder.
5. Start or restart the server.

DimensionBlocker is designed to run server-side.

## 🧱 Requirements

* Minecraft **1.21.11**
* NeoForge **21.11.x**
* Java **21**

No additional gameplay mods are required.

## 🔧 Development

DimensionBlocker is developed using Java and NeoForge.

### Project Structure

```text
DimensionBlocker/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── outbackservers/
│       │           └── dimensionblocker/
│       │               ├── DimensionBlocker.java
│       │               ├── command/
│       │               │   └── DimensionBlockerCommands.java
│       │               ├── data/
│       │               │   └── DimensionBlockerData.java
│       │               └── event/
│       │                   ├── DimensionBlockerEvents.java
│       │                   └── DimensionBlockerTeleport.java
│       └── resources/
│           └── META-INF/
│               └── neoforge.mods.toml
├── build.gradle
├── gradle.properties
└── settings.gradle
```

## 📝 Changelog

### 1.0.0

* Added The Nether locking
* Added The End locking
* Added dimension unlock commands
* Added real-world unlock timers
* Added seconds, minutes, hours, and days
* Added combined timer formats
* Added persistent dimension states
* Added persistent timers
* Added automatic timer expiration
* Added Gamemaster/OP bypass
* Added locked-dimension player messages
* Added remaining-time display
* Updated for Minecraft 1.21.11
* Updated for NeoForge 21.11.x

## 🐛 Bug Reports

If you find a bug, please open an issue on GitHub.

When reporting a bug, include:

* Minecraft version
* NeoForge version
* DimensionBlocker version
* Server type
* The command you were using
* What you expected to happen
* What actually happened
* Relevant server logs

## 💡 Feature Requests

Have an idea for DimensionBlocker?

Open a GitHub issue and describe the feature you'd like to see added.

Feature ideas may include:

* Additional dimensions
* Scheduled dimension openings
* More administrator controls
* Improved status commands
* Configurable messages
* More advanced progression controls

## 📜 License

**All Rights Reserved**

See the repository license for the terms under which DimensionBlocker may be used and distributed.

---

**DimensionBlocker**
Made for Minecraft server owners who want better control over dimension progression.
