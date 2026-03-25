# Golden-Dandelion-Backport

> *"I don't want to stop growing. I know I should, but I just — I can't."*
> — Every baby mob after being fed a Golden Dandelion

Backports the Golden Dandelion — a flower that **permanently freezes the age of baby mobs** when fed to them.
This backport targets **Minecraft 1.8.9** (Minecraft Forge `11.15.1.2318-1.8.9`).

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| 🌼 **Golden Dandelion Block** | A golden flower that grows on soil and grass |
| 🌼 **Golden Dandelion Item** | Right-click a baby mob to lock it in eternal youth |
| 🍼 **Eternal Youth** | Frozen baby mobs never grow up — Anakin would've traded his lightsaber for this |

---

## 🛸 The Code

All class and file names follow professional Minecraft Forge mod conventions.
All variable names, method names, and field names are Star Wars prequel memes references
(as mandated by the High Council of `r/prequelmemes`).

Sample highlights from the codebase:

```java
// From GoldenDandelionMod.java
public static final int midiChlorianCount = 20_000; // higher than Master Yoda's!

@EventHandler
public void helloThere(FMLInitializationEvent generalKenobi) { ... }

@EventHandler
public void itsOverAnakin(FMLPostInitializationEvent dontTryItAnakin) { ... }
```

```java
// From EventHandler.java
@SubscribeEvent
public void executeOrder66(LivingEvent.LivingUpdateEvent theCloneArmiesAdvanceOnTheJediTemple) { ... }
```

```java
// From GoldenDandelionItem.java
public static final int longHaveIWaited = -24_000; // perpetual baby age
private void freezeTheBabyForever(EntityAnimal padawanFrozenInTime) { ... }
```

---

## 🏗️ Building

Requires **Java 8** and a working internet connection to let Gradle download Forge.

```bash
./gradlew setupDecompWorkspace
./gradlew build
```

The compiled jar will appear in `build/libs/`.

---

## 📦 Structure

```
src/main/java/com/goldendandelion/
├── GoldenDandelionMod.java       ← Main mod class (@Mod)
├── blocks/
│   └── GoldenDandelionBlock.java ← The flower block
├── items/
│   └── GoldenDandelionItem.java  ← The item (feed to baby mobs)
├── handlers/
│   └── EventHandler.java         ← Tick-based age-freeze enforcement
└── proxy/
    ├── CommonProxy.java           ← Server-side proxy
    └── ClientProxy.java           ← Client-side proxy (model registration)
```

---

## ⚔️ May the Force (and the flower) be with you.
