# Quick Start for Java Developers

## TL;DR - Get Running Fast

```bash
# 1. Build the project (downloads dependencies, compiles)
./gradlew build

# 2. Run desktop app
./gradlew :desktopApp:run

# 3. Or open in Android Studio
# File → Open → Select D:\GoClean folder
```

---

## Essential File Map

| What | File | Maven Equivalent |
|------|------|------------------|
| Project structure | `settings.gradle.kts` | Parent pom.xml `<modules>` |
| Root config | `build.gradle.kts` | Parent pom.xml |
| Module config | `shared/build.gradle.kts` | Module pom.xml |
| Properties | `gradle.properties` | pom.xml `<properties>` |
| Dependencies | In `build.gradle.kts` files | `<dependencies>` in pom.xml |

---

## Kotlin Cheat Sheet (5 Minutes)

### Variables
```kotlin
val x = 1        // final int x = 1;
var y = 2        // int y = 2;
var z: Int = 3   // Explicit type (usually inferred)
```

### Null Safety
```kotlin
var name: String = "GoClean"   // Cannot be null
var name2: String? = null      // Can be null (notice ?)

name2?.length                  // Safe call (returns null if name2 is null)
name2?.length ?: 0             // Elvis operator (default if null)
```

### Functions
```kotlin
fun add(a: Int, b: Int): Int {
    return a + b
}

// One-liner
fun add(a: Int, b: Int): Int = a + b

// No return value (void)
fun printMessage(msg: String) {
    println(msg)
}
```

### Classes
```kotlin
// Java: 50+ lines of boilerplate
// Kotlin:
data class User(val name: String, val age: Int)
// Auto-generates: getters, setters, equals, hashCode, toString
```

### String Templates
```kotlin
val name = "GoClean"
val version = 1
println("Welcome to $name v$version")
println("Length: ${name.length}")
```

### Collections
```kotlin
val list = listOf(1, 2, 3, 4, 5)          // Immutable
val mutable = mutableListOf(1, 2, 3)      // Mutable

// Functional operations
list.filter { it > 3 }                     // [4, 5]
list.map { it * 2 }                        // [2, 4, 6, 8, 10]
list.forEach { println(it) }
```

---

## Gradle Commands

```bash
# Build
./gradlew build                           # mvn package
./gradlew clean build                     # mvn clean package
./gradlew :shared:build                   # Build specific module

# Run
./gradlew :desktopApp:run                 # Run desktop app
./gradlew :androidApp:installDebug        # Install Android app

# Info
./gradlew tasks                           # List all tasks
./gradlew :shared:dependencies            # mvn dependency:tree

# Refresh dependencies
./gradlew --refresh-dependencies
```

---

## Project Structure

```
GoClean/
│
├── shared/                         ← Shared business logic
│   └── src/
│       ├── commonMain/kotlin/      ← Code for ALL platforms
│       ├── androidMain/kotlin/     ← Android-specific code
│       ├── iosMain/kotlin/         ← iOS-specific code
│       └── desktopMain/kotlin/     ← Windows-specific code
│
├── androidApp/                     ← Android UI app
│   └── src/main/kotlin/
│
└── desktopApp/                     ← Desktop UI app
    └── src/jvmMain/kotlin/
```

**Rule of thumb:**
- Business logic → `shared/src/commonMain/`
- Platform features → `shared/src/[platform]Main/`
- UI → Platform app folders

---

## Adding Dependencies

Edit `shared/build.gradle.kts`:

```kotlin
sourceSets {
    val commonMain by getting {
        dependencies {
            // Maven: <dependency><groupId>org.jetbrains.kotlinx...
            // Gradle:
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
        }
    }

    val androidMain by getting {
        dependencies {
            implementation("androidx.core:core-ktx:1.12.0")
        }
    }
}
```

Then run: `./gradlew build` (auto-downloads dependencies)

---

## Development Workflow

### 1. Write Shared Code

Create `shared/src/commonMain/kotlin/com/goclean/app/model/BlockRule.kt`:

```kotlin
package com.goclean.app.model

data class BlockRule(
    val id: String,
    val platform: String,      // Instagram, TikTok, etc.
    val isEnabled: Boolean
)
```

### 2. Use in Android App

Edit `androidApp/src/main/kotlin/com/goclean/app/MainActivity.kt`:

```kotlin
import com.goclean.app.model.BlockRule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rule = BlockRule(
            id = "1",
            platform = "Instagram",
            isEnabled = true
        )

        setContent {
            Text("Blocking: ${rule.platform}")
        }
    }
}
```

### 3. Build & Run

```bash
./gradlew :androidApp:installDebug
# or
./gradlew :desktopApp:run
```

---

## Platform-Specific Code (Expect/Actual)

**When you need different implementations per platform:**

**Common code** (`shared/src/commonMain/kotlin/Platform.kt`):
```kotlin
// Declaration: each platform must implement this
expect fun getCurrentTime(): Long
```

**Android** (`shared/src/androidMain/kotlin/Platform.android.kt`):
```kotlin
actual fun getCurrentTime(): Long {
    return System.currentTimeMillis()
}
```

**iOS** (`shared/src/iosMain/kotlin/Platform.ios.kt`):
```kotlin
actual fun getCurrentTime(): Long {
    return (NSDate().timeIntervalSince1970 * 1000).toLong()
}
```

**Desktop** (`shared/src/desktopMain/kotlin/Platform.desktop.kt`):
```kotlin
actual fun getCurrentTime(): Long {
    return System.currentTimeMillis()
}
```

**Use anywhere** (works on all platforms):
```kotlin
val time = getCurrentTime()
```

---

## Next Steps

1. **Test the setup:**
   ```bash
   ./gradlew build
   ./gradlew :desktopApp:run
   ```

2. **Practice Kotlin:**
   - Modify `shared/src/commonMain/kotlin/com/goclean/app/Greeting.kt`
   - Add your own data class
   - See changes in the running app

3. **Read the full guide:**
   - `JAVA_TO_KOTLIN_GUIDE.md` - Comprehensive Java → Kotlin guide
   - `GETTING_STARTED.md` - Setup instructions

4. **Start building:**
   - Create `BlockRule` data class
   - Create blocking service interface
   - Implement Android AccessibilityService

---

## Common Gotchas for Java Devs

1. **No semicolons needed** (but won't error if you use them)
2. **`val` vs `var`** - Use `val` by default (immutable)
3. **Types come after** - `val name: String` not `String name`
4. **Null safety is strict** - `String?` vs `String`
5. **No `new` keyword** - `User()` not `new User()`
6. **`fun` not method** - Functions are declared with `fun`
7. **`when` not switch** - More powerful than Java switch

---

## Help & Resources

- Full guide: `JAVA_TO_KOTLIN_GUIDE.md`
- Kotlin docs: https://kotlinlang.org/docs/java-to-kotlin-idioms-strings.html
- File an issue if stuck!

Ready to code! 🚀
