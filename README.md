# DimensionBlocker

**DimensionBlocker** is a server-side Minecraft mod that gives server owners and administrators control over access to **The Nether** and **The End**.

Lock dimensions until you're ready to open them, schedule automatic unlocks with real-world timers, and allow server staff to bypass locked dimensions.

The goal of DimensionBlocker is to support multiple Minecraft versions and mod loaders so server owners can use it across different types of servers.

---

## ✨ Features

* 🔒 Lock **The Nether**
* 🔒 Lock **The End**
* 🔓 Unlock dimensions at any time
* ⏱️ Set real-world unlock timers
* 📅 Supports days, hours, minutes, and seconds
* 🕐 Supports combined timers such as `2d12h30m`
* 💾 Timers and dimension states persist through server restarts
* ⚡ Dimensions automatically unlock when their timer expires
* 👑 Gamemasters/OPs can bypass dimension locks
* 💬 Players receive messages when attempting to enter a locked dimension
* ⏳ Players can see the remaining time until a timed unlock
* 🖥️ Designed for dedicated servers
* 🧩 Lightweight and focused on dimension access control
* 🔄 Multiple Minecraft versions planned
* 🛠️ Multiple mod loaders planned

---

# 📦 Minecraft Version Support

DimensionBlocker is being developed with support for multiple Minecraft versions.

## 🟢 Currently Supported

### NeoForge

| Minecraft Version | Mod Loader | Status       |
| ----------------- | ---------- | ------------ |
| **1.21.1**        | NeoForge   | 🟢 Supported |
| **1.21.2**        | NeoForge   | 🟢 Supported |
| **1.21.11**       | NeoForge   | 🟢 Supported |

These versions have been developed and tested for DimensionBlocker.

NeoForge has separate migration paths between Minecraft versions such as 1.21.1 → 1.21.2 and later 1.21.x versions, so each supported version may have its own build.

---

# 🚧 Coming Soon

The following Minecraft versions and mod loaders are planned for future DimensionBlocker releases.

## Forge

| Minecraft Version | Mod Loader | Status         |
| ----------------- | ---------- | -------------- |
| **1.20.1**        | Forge      | 🟡 Coming Soon |
| **1.21.1**        | Forge      | 🟡 Coming Soon |
| **More versions** | Forge      | 🟡 Planned     |

Forge 1.20.1 is specifically planned because it remains an important version for older modpacks and servers.

---

## Fabric

| Minecraft Version | Mod Loader | Status         |
| ----------------- | ---------- | -------------- |
| **1.20.1**        | Fabric     | 🟡 Coming Soon |
| **1.21.1**        | Fabric     | 🟡 Coming Soon |
| **More versions** | Fabric     | 🟡 Planned     |

Fabric support will allow DimensionBlocker to be used by servers running the Fabric ecosystem.

---

## neoforge

| Minecraft Version | Mod Loader | Status         |
| ----------------- | ---------- | -------------- |
| **1.20.1**        | neoforge     | 🟡 Coming Soon |
| **26.2**        | neoforge     | 🟡 Coming Soon |
| **More versions** | neoforge     | 🟡 Planned     |

Fabric support will allow DimensionBlocker to be used by servers running the Fabric ecosystem.

---

## 🔮 Future Version Support

More Minecraft versions will be added over time.

Possible future support includes:

* New Minecraft releases
* Additional 1.20.x versions
* Additional 1.21.x versions
* Future Minecraft versions
* More Forge versions
* More NeoForge versions
* More Fabric versions

The supported-version list will be updated as each version is developed and tested.

---

# 🎮 Commands

All DimensionBlocker commands require the appropriate server permissions.

## The Nether

### Lock The Nether

```text
/dimensionblocker nether off
```

### Unlock The Nether

```text
/dimensionblocker nether on
```

### Set a Timer

```text
/dimensionblocker nether timer 30s
```

---

## The End

### Lock The End

```text
/dimensionblocker end off
```

### Unlock The End

```text
/dimensionblocker end on
```

### Set a Timer

```text
/dimensionblocker end timer 2d
```

---

# ⏱️ Timer Formats

DimensionBlocker supports the following time units:

| Unit | Meaning | Example |
| ---- | ------- | ------- |
| `s`  | Seconds | `30s`   |
| `m`  | Minutes | `10m`   |
| `h`  | Hours   | `2h`    |
| `d`  | Days    | `2d`    |

You can combine multiple units.

### Examples

```text
30s
10m
2h
1d
3d12h
6h30m
2d12h30m
```

For example:

```text
/dimensionblocker end timer 2d12h30m
```

will keep The End locked for **2 days, 12 hours, and 30 minutes**.

Timers use **real-world elapsed time**, rather than Minecraft ticks.

---

# 🔒 Dimension Locking

When a dimension is locked, normal players cannot travel into that dimension.

For example:

```text
/dimensionblocker nether off
```

locks The Nether.

A player attempting to enter will receive a message explaining that the dimension is currently locked.

If a timer has been configured, the player will also see the remaining time.

Example:

```text
The Nether is currently locked.
Unlocks in 1 day, 4 hours, 32 minutes.
```

---

# 👑 Staff Bypass

Players with the required **Gamemaster/OP permission** can bypass DimensionBlocker's dimension locks.

This allows server administrators and staff to enter locked dimensions for:

* Server administration
* Testing
* Building
* Moderation
* World preparation
* Event setup

---

# 💾 Persistence

DimensionBlocker stores its dimension states and timers using Minecraft's server saved-data system.

This allows settings to survive:

* Server restarts
* Server shutdowns
* Server crashes followed by a restart

For example:

```text
/dimensionblocker end timer 2d
```

If the server is restarted several hours later, the timer continues using the remaining real-world time.

---

# 🛠️ Server Use Cases

DimensionBlocker is useful for servers that want controlled progression.

## Server Launch

Start your server with both dimensions locked:

```text
The Nether 🔒
The End 🔒
```

Players can explore the Overworld while the server develops.

## Nether Opening Event

Schedule the Nether to open later:

```text
/dimensionblocker nether timer 2d
```

The Nether will automatically unlock after two real-world days.

## End Game Progression

Keep The End locked until the server reaches a certain stage:

```text
/dimensionblocker end off
```

Then open it when you're ready:

```text
/dimensionblocker end on
```

---

# 📦 Installation

1. Install the required Minecraft version.
2. Install the appropriate supported mod loader.
3. Download the DimensionBlocker `.jar` for your Minecraft version and mod loader.
4. Place the `.jar` file into your server's `mods` folder.
5. Start or restart the server.

**Important:** Make sure you download the build that matches your Minecraft version and mod loader.

For example:

```text
Minecraft 1.21.1
NeoForge
DimensionBlocker 1.0.0
```

is different from:

```text
Minecraft 1.20.1
Forge
DimensionBlocker 1.0.0
```

---

# 🧱 Requirements

Requirements depend on the version you are using.

### NeoForge 1.21.1

* Minecraft 1.21.1
* NeoForge
* Java 21

### NeoForge 1.21.2

* Minecraft 1.21.2
* NeoForge
* Java 21

### NeoForge 1.21.11

* Minecraft 1.21.11
* NeoForge
* Java 21

NeoForge's 1.21.11 development environment officially uses Java 21.

### Forge / Fabric

Forge and Fabric requirements will be listed when those versions are released.

---

# 🛠️ Development

DimensionBlocker is developed using Java and Minecraft modding APIs.

The project is being maintained with multiple Minecraft versions and mod loaders in mind.

## Project Structure

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

---

# 📝 Changelog

## 1.0.0

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
* Added NeoForge 1.21.1 support
* Added NeoForge 1.21.2 support
* Added NeoForge 1.21.11 support

---

# 🚧 Version Roadmap

| Version / Loader    | Status         |
| ------------------- | -------------- |
| NeoForge 1.21.1     | 🟢 Supported   |
| NeoForge 1.21.2     | 🟢 Supported   |
| NeoForge 1.21.11    | 🟢 Supported   |
| Forge 1.20.1        | 🟡 Coming Soon |
| Forge 1.21.1        | 🟡 Coming Soon |
| Fabric 1.20.1       | 🟡 Coming Soon |
| Fabric 1.21.1       | 🟡 Coming Soon |
| Additional versions | 🔵 Planned     |

---

# 🐛 Bug Reports

If you find a bug, please open an issue on GitHub.

When reporting a bug, include:

* Minecraft version
* Mod loader
* Mod loader version
* DimensionBlocker version
* Server type
* The command you were using
* What you expected to happen
* What actually happened
* Relevant server logs

---

# 💡 Feature Requests

Have an idea for DimensionBlocker?

Open a GitHub issue and describe the feature you'd like to see added.

Possible future features include:

* Additional dimensions
* Scheduled dimension openings
* More administrator controls
* Status commands
* Configurable messages
* Advanced progression controls
* More timer options
* Automatic progression systems
* Support for additional mod loaders

---

# 📜 License

**All Rights Reserved**

See the repository license for the terms under which DimensionBlocker may be used and distributed.

---

# 🌐 Outback Servers

DimensionBlocker is developed by **Outback Servers**.

The goal is to create useful, reliable Minecraft server mods that work across multiple Minecraft versions and mod loaders.

**More versions and features are planned for the future.**
