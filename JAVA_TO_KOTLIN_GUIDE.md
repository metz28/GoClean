# Java Developer's Guide to GoClean (Kotlin + Gradle)

## Table of Contents
1. [Maven vs Gradle](#maven-vs-gradle)
2. [Java vs Kotlin](#java-vs-kotlin)
3. [Understanding the Project Structure](#understanding-the-project-structure)
4. [How Dependencies Work](#how-dependencies-work)
5. [Building and Running](#building-and-running)
6. [Development Workflow](#development-workflow)
7. [Next Steps](#next-steps)

---

## Maven vs Gradle

### Key Differences

| Maven | Gradle |
|-------|--------|
| `pom.xml` | `build.gradle.kts` |
| XML-based | Kotlin DSL (or Groovy) |
| Declarative | Programmatic + Declarative |
| `<dependencies>` | `dependencies { }` block |
| `mvn clean install` | `./gradlew build` |
| `<modules>` | `include(":moduleName")` in `settings.gradle.kts` |

### File Mapping

**Maven:**
```
project/
├── pom.xml                    # Project config
├── settings.xml               # Maven settings
└── module1/
    └── pom.xml               # Module config
```

**Gradle (this project):**
```
GoClean/
├── build.gradle.kts          # Root build config (like parent pom.xml)
├── settings.gradle.kts       # Project structure (defines modules)
├── gradle.properties         # Properties (like Maven properties)
└── shared/
    └── build.gradle.kts      # Module config (like module pom.xml)
```

### Common Commands

| Maven | Gradle | Description |
|-------|--------|-------------|
| `mvn clean` | `./gradlew clean` | Clean build artifacts |
| `mvn compile` | `./gradlew compileKotlin` | Compile code |
| `mvn test` | `./gradlew test` | Run tests |
| `mvn package` | `./gradlew build` | Build the project |
| `mvn install` | `./gradlew publishToMavenLocal` | Install to local repo |
| `mvn dependency:tree` | `./gradlew dependencies` | Show dependency tree |

---

## Java vs Kotlin

### Syntax Comparison

#### 1. **Variable Declaration**

**Java:**
```java
String name = "GoClean";
final String appId = "com.goclean.app";
int version = 1;
```

**Kotlin:**
```kotlin
var name = "GoClean"           // Mutable (like non-final)
val appId = "com.goclean.app"  // Immutable (like final)
var version: Int = 1           // Explicit type (optional)
```

#### 2. **Class Declaration**

**Java:**
```java
public class BlockRule {
    private String platform;
    private boolean enabled;

    public BlockRule(String platform, boolean enabled) {
        this.platform = platform;
        this.enabled = enabled;
    }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    @Override
    public boolean equals(Object o) { /* ... */ }
    @Override
    public int hashCode() { /* ... */ }
    @Override
    public String toString() { /* ... */ }
}
```

**Kotlin (data class):**
```kotlin
data class BlockRule(
    val platform: String,
    val enabled: Boolean
)
// Auto-generates: equals(), hashCode(), toString(), copy(), getters/setters
```

#### 3. **Null Safety**

**Java:**
```java
String name = null;  // Can cause NullPointerException
if (name != null) {
    System.out.println(name.length());
}
```

**Kotlin:**
```kotlin
var name: String = "GoClean"   // Cannot be null
var name2: String? = null      // Can be null (notice the ?)

// Safe call operator
println(name2?.length)         // Prints null if name2 is null

// Elvis operator (default value)
val length = name2?.length ?: 0  // Returns 0 if name2 is null

// Non-null assertion (use carefully!)
println(name2!!.length)        // Throws exception if name2 is null
```

#### 4. **Functions**

**Java:**
```java
public int add(int a, int b) {
    return a + b;
}

public void printMessage(String msg) {
    System.out.println(msg);
}
```

**Kotlin:**
```kotlin
fun add(a: Int, b: Int): Int {
    return a + b
}

// Expression body (single expression)
fun add(a: Int, b: Int): Int = a + b

// Unit return type (like void) - can be omitted
fun printMessage(msg: String): Unit {
    println(msg)
}

// Simplified
fun printMessage(msg: String) {
    println(msg)
}
```

#### 5. **Collections**

**Java:**
```java
List<String> apps = new ArrayList<>();
apps.add("Instagram");
apps.add("TikTok");

for (String app : apps) {
    System.out.println(app);
}
```

**Kotlin:**
```kotlin
val apps = mutableListOf<String>()
apps.add("Instagram")
apps.add("TikTok")

// Or create directly
val apps2 = listOf("Instagram", "TikTok")  // Immutable list

// Iteration
for (app in apps) {
    println(app)
}

// Functional style
apps.forEach { app -> println(app) }

// Even shorter
apps.forEach { println(it) }  // 'it' is the implicit parameter
```

#### 6. **String Templates**

**Java:**
```java
String name = "GoClean";
int version = 1;
String message = "Welcome to " + name + " v" + version;
```

**Kotlin:**
```kotlin
val name = "GoClean"
val version = 1
val message = "Welcome to $name v$version"

// Expressions
val message2 = "Name length: ${name.length}"
```

#### 7. **When Expression (switch on steroids)**

**Java:**
```java
switch (platform) {
    case "Instagram":
        return "Block Reels";
    case "TikTok":
        return "Block Feed";
    default:
        return "Unknown";
}
```

**Kotlin:**
```kotlin
val action = when (platform) {
    "Instagram" -> "Block Reels"
    "TikTok" -> "Block Feed"
    else -> "Unknown"
}

// Multiple conditions
when (platform) {
    "Instagram", "TikTok", "YouTube" -> "Block shorts"
    else -> "Unknown"
}

// Type checking
when (obj) {
    is String -> println("It's a string: $obj")
    is Int -> println("It's an int: $obj")
    else -> println("Unknown type")
}
```

#### 8. **Extension Functions**

**Java:**
```java
// Have to create a utility class
public class StringUtils {
    public static boolean isValidUrl(String str) {
        return str.startsWith("http");
    }
}
// Usage: StringUtils.isValidUrl(url)
```

**Kotlin:**
```kotlin
// Add methods to existing classes!
fun String.isValidUrl(): Boolean {
    return this.startsWith("http")
}

// Usage (looks like a regular method):
val url = "https://example.com"
println(url.isValidUrl())  // true
```

---

## Understanding the Project Structure

### Multi-Module Project (like Maven Multi-Module)

```
GoClean/
│
├── settings.gradle.kts          # Defines which modules to include
│                                 # Like Maven's parent pom.xml <modules> section
│
├── build.gradle.kts             # Root build configuration
│                                 # Like Maven's parent pom.xml
│                                 # Defines plugins and versions for all modules
│
├── gradle.properties            # Configuration properties
│                                 # Like Maven's <properties> section
│
├── shared/                      # Shared KMP module (like a Maven module)
│   ├── build.gradle.kts         # Module-specific build config
│   └── src/
│       ├── commonMain/          # Platform-independent code
│       │   └── kotlin/          # Code that works on ALL platforms
│       │
│       ├── androidMain/         # Android-specific code
│       │   └── kotlin/          # Android implementations
│       │
│       ├── iosMain/             # iOS-specific code
│       │   └── kotlin/          # iOS implementations
│       │
│       └── desktopMain/         # Desktop/Windows-specific code
│           └── kotlin/          # Desktop implementations
│
├── androidApp/                  # Android application module
│   ├── build.gradle.kts
│   └── src/main/
│
└── desktopApp/                  # Desktop application module
    ├── build.gradle.kts
    └── src/jvmMain/
```

### Source Set Concept (New for Maven users)

In Maven, you typically have:
- `src/main/java` - Main code
- `src/test/java` - Test code

In Kotlin Multiplatform, you have **multiple source sets**:

```kotlin
sourceSets {
    // Common code - runs on ALL platforms
    val commonMain by getting {
        dependencies {
            // Dependencies available on ALL platforms
        }
    }

    // Android-specific code
    val androidMain by getting {
        dependencies {
            // Android-only dependencies
        }
    }

    // iOS-specific code
    val iosMain by getting {
        dependencies {
            // iOS-only dependencies
        }
    }

    // Desktop-specific code
    val desktopMain by getting {
        dependencies {
            // Desktop-only dependencies
        }
    }
}
```

### Expect/Actual Pattern (Platform-Specific Code)

This is **unique to Kotlin Multiplatform** - no direct Maven equivalent.

**Problem:** You want to write code once, but some parts need platform-specific implementations.

**Solution:** `expect` declarations in common code, `actual` implementations per platform.

**Example:**

**File: `shared/src/commonMain/kotlin/Platform.kt`**
```kotlin
// Declaration: "I expect each platform to provide this"
expect fun getCurrentTime(): Long
```

**File: `shared/src/androidMain/kotlin/Platform.android.kt`**
```kotlin
// Android implementation
actual fun getCurrentTime(): Long {
    return System.currentTimeMillis()
}
```

**File: `shared/src/iosMain/kotlin/Platform.ios.kt`**
```kotlin
// iOS implementation
import platform.Foundation.NSDate

actual fun getCurrentTime(): Long {
    return (NSDate().timeIntervalSince1970 * 1000).toLong()
}
```

**File: `shared/src/desktopMain/kotlin/Platform.desktop.kt`**
```kotlin
// Desktop implementation
actual fun getCurrentTime(): Long {
    return System.currentTimeMillis()
}
```

**Usage in common code:**
```kotlin
// This works on ALL platforms!
val time = getCurrentTime()
println("Current time: $time")
```

---

## How Dependencies Work

### In Maven (pom.xml):
```xml
<dependencies>
    <dependency>
        <groupId>com.squareup.okhttp3</groupId>
        <artifactId>okhttp</artifactId>
        <version>4.12.0</version>
    </dependency>
</dependencies>
```

### In Gradle (build.gradle.kts):
```kotlin
dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
}
```

### Dependency Scopes

| Maven | Gradle | Usage |
|-------|--------|-------|
| `compile` | `implementation` | Main code dependency |
| `provided` | `compileOnly` | Compile-time only |
| `runtime` | `runtimeOnly` | Runtime only |
| `test` | `testImplementation` | Test code only |
| N/A | `api` | Exposes to consumers (like compile in old Maven) |

### Adding Dependencies to GoClean

**Example: Add JSON serialization to the shared module**

Edit `shared/build.gradle.kts`:

```kotlin
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                // Add Kotlin Serialization (works on ALL platforms)
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
            }
        }

        val androidMain by getting {
            dependencies {
                // Android-specific dependency
                implementation("androidx.core:core-ktx:1.12.0")
            }
        }

        val desktopMain by getting {
            dependencies {
                // Desktop-specific dependency
                implementation("org.slf4j:slf4j-simple:2.0.9")
            }
        }
    }
}
```

### Common Dependencies You'll Need

```kotlin
val commonMain by getting {
    dependencies {
        // Coroutines (async/concurrency)
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

        // Date/Time
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")

        // JSON serialization
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

        // Logging
        implementation("io.github.aakira:napier:2.6.1")
    }
}

val androidMain by getting {
    dependencies {
        // AndroidX libraries
        implementation("androidx.core:core-ktx:1.12.0")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

        // Compose (UI)
        implementation("androidx.activity:activity-compose:1.8.2")
    }
}
```

---

## Building and Running

### Build Commands

```bash
# Clean the project
./gradlew clean

# Build everything
./gradlew build

# Build specific module
./gradlew :shared:build
./gradlew :androidApp:build
./gradlew :desktopApp:build

# See all tasks
./gradlew tasks

# See dependency tree (like mvn dependency:tree)
./gradlew :shared:dependencies
```

### Running the Apps

#### Android
```bash
# Build debug APK
./gradlew :androidApp:assembleDebug

# Install on connected device/emulator
./gradlew :androidApp:installDebug

# Build and install
./gradlew :androidApp:installDebug --info

# APK location: androidApp/build/outputs/apk/debug/androidApp-debug.apk
```

#### Desktop (Windows)
```bash
# Run directly
./gradlew :desktopApp:run

# Create distributable package
./gradlew :desktopApp:packageDistributionForCurrentOS

# Output: desktopApp/build/compose/binaries/main/
```

### Using Android Studio

1. Open Android Studio
2. File → Open → Select `D:\GoClean`
3. Wait for Gradle sync (first time takes a while)
4. To run:
   - Android: Select "androidApp" config → Run
   - Desktop: Select "desktopApp" config → Run

---

## Development Workflow

### 1. **Where to Write Code**

**For shared logic (blocking rules, data models, etc.):**
- Write in `shared/src/commonMain/kotlin/`
- This code works on ALL platforms

**For platform-specific features:**
- Android: `shared/src/androidMain/kotlin/`
- iOS: `shared/src/iosMain/kotlin/`
- Desktop: `shared/src/desktopMain/kotlin/`

### 2. **Creating a New Feature**

Let's add a blocking rule system:

**Step 1: Create data model in common code**

`shared/src/commonMain/kotlin/com/goclean/app/model/BlockRule.kt`
```kotlin
package com.goclean.app.model

data class BlockRule(
    val id: String,
    val platform: String,        // "Instagram", "TikTok", etc.
    val featureType: String,     // "Reels", "Shorts", "Feed"
    val isEnabled: Boolean = true
)
```

**Step 2: Create interface for platform-specific blocker**

`shared/src/commonMain/kotlin/com/goclean/app/blocker/ContentBlocker.kt`
```kotlin
package com.goclean.app.blocker

import com.goclean.app.model.BlockRule

// Expect declaration - each platform provides implementation
expect class ContentBlocker() {
    fun applyRule(rule: BlockRule): Boolean
    fun removeRule(ruleId: String): Boolean
    fun isBlocking(platform: String): Boolean
}
```

**Step 3: Android implementation**

`shared/src/androidMain/kotlin/com/goclean/app/blocker/ContentBlocker.android.kt`
```kotlin
package com.goclean.app.blocker

import com.goclean.app.model.BlockRule
import android.util.Log

actual class ContentBlocker {
    actual fun applyRule(rule: BlockRule): Boolean {
        Log.d("ContentBlocker", "Applying rule: ${rule.platform}")
        // TODO: Implement AccessibilityService logic
        return true
    }

    actual fun removeRule(ruleId: String): Boolean {
        // TODO: Remove blocking rule
        return true
    }

    actual fun isBlocking(platform: String): Boolean {
        // TODO: Check if platform is currently blocked
        return false
    }
}
```

**Step 4: Use in UI (Android app)**

`androidApp/src/main/kotlin/com/goclean/app/MainActivity.kt`
```kotlin
package com.goclean.app

import com.goclean.app.blocker.ContentBlocker
import com.goclean.app.model.BlockRule

class MainActivity : ComponentActivity() {
    private val blocker = ContentBlocker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Apply blocking rule
        val rule = BlockRule(
            id = "1",
            platform = "Instagram",
            featureType = "Reels",
            isEnabled = true
        )
        blocker.applyRule(rule)

        setContent {
            GoCleanTheme {
                HomeScreen()
            }
        }
    }
}
```

### 3. **Hot Reload / Incremental Build**

When you make changes:

**Android Studio:**
- Code changes auto-trigger recompilation
- For UI changes: Ctrl + F10 (Run to cursor) or use Compose Preview

**Command Line:**
```bash
# Gradle watches for changes and rebuilds
./gradlew --continuous :androidApp:installDebug
```

### 4. **Debugging**

**Kotlin debugging is like Java:**
- Set breakpoints (click line number gutter)
- Run in Debug mode
- Inspect variables
- Step through code

**Print debugging:**
```kotlin
println("Debug: $variable")  // Simple
Log.d("TAG", "Message")      // Android
```

---

## Next Steps

### Phase 1: Learn Kotlin Basics

**Create a practice file:** `shared/src/commonMain/kotlin/com/goclean/app/Practice.kt`

```kotlin
package com.goclean.app

// Data class (like Java POJO)
data class User(val name: String, val age: Int)

// Extension function
fun String.isValidEmail(): Boolean {
    return this.contains("@")
}

// Sealed class (restricted hierarchy)
sealed class Result {
    data class Success(val data: String) : Result()
    data class Error(val message: String) : Result()
    object Loading : Result()
}

// Practice function
fun kotlinBasics() {
    // Variables
    val immutable = "Can't change"
    var mutable = "Can change"

    // Null safety
    var nullable: String? = null
    println(nullable?.length ?: 0)

    // Collections
    val list = listOf(1, 2, 3, 4, 5)
    val filtered = list.filter { it > 3 }  // [4, 5]
    val mapped = list.map { it * 2 }       // [2, 4, 6, 8, 10]

    // When expression
    val result = when (mutable.length) {
        0 -> "Empty"
        in 1..10 -> "Short"
        else -> "Long"
    }

    // String templates
    println("Result: $result")

    // Extension function usage
    println("test@example.com".isValidEmail())  // true
}
```

### Phase 2: Build Core Features

1. **Create data models** (`shared/src/commonMain/kotlin/com/goclean/app/model/`)
   - `BlockRule.kt` - Blocking rules
   - `AppConfig.kt` - App configuration
   - `BlockStats.kt` - Usage statistics

2. **Create repository pattern** (`shared/src/commonMain/kotlin/com/goclean/app/data/`)
   - `BlockRuleRepository.kt` - CRUD for rules
   - Local storage interface (expect/actual for each platform)

3. **Create use cases** (`shared/src/commonMain/kotlin/com/goclean/app/usecase/`)
   - `ToggleBlockUseCase.kt` - Enable/disable blocking
   - `GetActiveRulesUseCase.kt` - Fetch active rules

### Phase 3: Platform-Specific Implementation

#### Android (AccessibilityService)
1. Create AccessibilityService in `androidApp`
2. Monitor app usage
3. Detect Instagram Reels, YouTube Shorts, etc.
4. Block by overlaying or closing

#### Windows (Desktop)
1. Monitor process names
2. Use hosts file or DNS blocking
3. Create background service

### Phase 4: UI Development

Use **Compose Multiplatform** (similar to Android Jetpack Compose)

```kotlin
@Composable
fun BlockingRulesScreen() {
    val rules = remember { mutableStateListOf<BlockRule>() }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Blocking Rules", style = MaterialTheme.typography.h4)

        rules.forEach { rule ->
            RuleCard(rule = rule)
        }

        Button(onClick = { /* Add new rule */ }) {
            Text("Add Rule")
        }
    }
}
```

### Recommended Learning Path

1. **Week 1: Kotlin Basics**
   - Variables, functions, classes
   - Null safety
   - Collections and lambdas

2. **Week 2: Kotlin OOP**
   - Data classes, sealed classes
   - Extension functions
   - Companion objects

3. **Week 3: Gradle & Project Structure**
   - Dependencies
   - Source sets
   - Expect/actual pattern

4. **Week 4: Start Implementation**
   - Create shared models
   - Implement Android blocker
   - Build basic UI

### Useful Resources

- **Kotlin Docs for Java Devs:** https://kotlinlang.org/docs/java-to-kotlin-idioms-strings.html
- **Gradle User Guide:** https://docs.gradle.org/current/userguide/userguide.html
- **KMP Docs:** https://kotlinlang.org/docs/multiplatform.html
- **Compose Multiplatform:** https://www.jetbrains.com/lp/compose-multiplatform/

### Quick Reference Card

```kotlin
// Java                          // Kotlin
public class Foo { }            class Foo { }
final int x = 1;                val x = 1
int y = 2;                      var y = 2
String s = null;                var s: String? = null
void method() { }               fun method() { }
int method() { return 1; }      fun method(): Int = 1
if (x != null) x.foo()          x?.foo()
x != null ? x : y               x ?: y
List<String> list = new...      val list = mutableListOf<String>()
```

---

## Questions?

Now that you understand the structure, try:
1. Build the project: `./gradlew build`
2. Run desktop app: `./gradlew :desktopApp:run`
3. Explore the example code
4. Modify `Greeting.kt` to practice

Happy coding! 🚀
