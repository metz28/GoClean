# Getting Started with GoClean

## Initial Setup

### 1. Download Gradle Wrapper JAR
The project uses Gradle wrapper, but you need to download the wrapper JAR first:

**Option A: Using an existing Gradle installation**
```bash
gradle wrapper --gradle-version 8.5
```

**Option B: Download manually**
Download `gradle-wrapper.jar` from:
https://raw.githubusercontent.com/gradle/gradle/v8.5.0/gradle/wrapper/gradle-wrapper.jar

Place it in: `gradle/wrapper/gradle-wrapper.jar`

**Option C: Use the gradlew script (it will auto-download)**
```bash
# On Windows
gradlew.bat --version

# On macOS/Linux
./gradlew --version
```

### 2. Verify Java Installation
Make sure you have JDK 11 or higher installed:
```bash
java -version
```

If not installed, download from: https://adoptium.net/

### 3. Sync the Project

**Using Android Studio (Recommended for Android development):**
1. Open Android Studio
2. Click "Open an Existing Project"
3. Select the `D:\GoClean` folder
4. Wait for Gradle sync to complete

**Using Command Line:**
```bash
# Build all modules
gradlew.bat build

# Run Android app (requires Android device/emulator)
gradlew.bat :androidApp:installDebug

# Run Desktop app
gradlew.bat :desktopApp:run
```

## Project Structure Overview

```
GoClean/
├── shared/                           # Shared Kotlin code (all platforms)
│   └── src/
│       ├── commonMain/              # Common code (works everywhere)
│       │   └── kotlin/
│       │       └── com/goclean/app/
│       │           ├── Platform.kt   # Expect/actual demo
│       │           └── Greeting.kt   # Simple example class
│       ├── androidMain/             # Android-specific code
│       ├── iosMain/                 # iOS-specific code
│       └── desktopMain/             # Windows-specific code
│
├── androidApp/                      # Android application
│   └── src/main/
│       ├── kotlin/
│       │   └── MainActivity.kt      # Android entry point
│       └── AndroidManifest.xml
│
├── desktopApp/                      # Desktop (Windows) application
│   └── src/jvmMain/kotlin/
│       └── Main.kt                  # Desktop entry point
│
└── iosApp/                          # iOS app (requires Xcode on macOS)
```

## Understanding Expect/Actual Pattern

The `Platform.kt` files demonstrate KMP's expect/actual mechanism:

1. **commonMain/Platform.kt** - Declares what's needed (expect)
2. **androidMain/Platform.android.kt** - Android implementation (actual)
3. **iosMain/Platform.ios.kt** - iOS implementation (actual)
4. **desktopMain/Platform.desktop.kt** - Desktop implementation (actual)

This pattern allows you to write platform-specific code while keeping a common interface.

## Next Steps for Learning

### 1. Explore the Example Code
- Check `shared/src/commonMain/kotlin/com/goclean/app/Platform.kt`
- See how `actual` implementations work in each platform folder
- Run the apps to see "Hello from [Platform]" messages

### 2. Add Your First Feature
Try creating a simple data class in `commonMain`:

```kotlin
// shared/src/commonMain/kotlin/com/goclean/app/BlockRule.kt
data class BlockRule(
    val platform: String,      // e.g., "Instagram"
    val featureType: String,   // e.g., "Reels"
    val isEnabled: Boolean
)
```

### 3. Learn Key Kotlin Concepts
- **Data classes** - Simple POJOs with auto-generated equals/hashCode/toString
- **Extension functions** - Add methods to existing classes
- **Coroutines** - Asynchronous programming (similar to async/await)
- **Sealed classes** - Restricted class hierarchies (great for state management)
- **Companion objects** - Kotlin's alternative to static methods

### 4. Build for Different Platforms

**Android:**
```bash
gradlew.bat :androidApp:assembleDebug
# APK location: androidApp/build/outputs/apk/debug/
```

**Desktop:**
```bash
gradlew.bat :desktopApp:run
# Or create a distributable:
gradlew.bat :desktopApp:packageDistributionForCurrentOS
```

**iOS (macOS only):**
```bash
# Open in Xcode (when you create the iOS project)
open iosApp/iosApp.xcodeproj
```

## Helpful Resources

- **Kotlin Docs for Java Developers**: https://kotlinlang.org/docs/java-to-kotlin-idioms-strings.html
- **KMP Getting Started**: https://kotlinlang.org/docs/multiplatform.html
- **Compose Multiplatform**: https://www.jetbrains.com/lp/compose-multiplatform/

## Common Issues

**Gradle sync fails:**
- Ensure Java 11+ is installed
- Check internet connection (Gradle downloads dependencies)
- Try: `gradlew.bat clean build`

**Android build fails:**
- Install Android SDK via Android Studio
- Set ANDROID_HOME environment variable

**Desktop app won't run:**
- Verify JDK version: `java -version`
- Check that it's JDK, not just JRE

## Ready to Code!

Start by exploring the example code, then begin implementing the blocking features. The shared module is where you'll spend most of your time - that's the power of KMP!
