# GoClean

Cross-platform distraction blocker app. Blocks Instagram Reels, YouTube Shorts, TikTok scroll feeds.

## Tech Stack

- **Kotlin Multiplatform (KMP)** — shared business logic
- **Compose Multiplatform** — Android + Windows UI
- **SwiftUI** — iOS + macOS UI
- **Targets**: Android, iOS, macOS, Windows

## Project Structure

```
GoClean/
├── shared/                    # Shared KMP module
│   ├── src/
│   │   ├── commonMain/       # Platform-agnostic code
│   │   ├── androidMain/      # Android-specific code
│   │   ├── iosMain/          # iOS-specific code
│   │   └── desktopMain/      # Desktop (Windows) code
│   └── build.gradle.kts
├── androidApp/               # Android application
│   └── src/main/
├── desktopApp/               # Desktop (Windows) application
│   └── src/jvmMain/
├── iosApp/                   # iOS application (requires Xcode)
├── build.gradle.kts          # Root build configuration
└── settings.gradle.kts       # Project modules
```

## Getting Started

### Prerequisites

- JDK 11 or higher
- Android Studio (for Android development)
- Xcode (for iOS/macOS development, macOS only)

### Building

#### Android
```bash
./gradlew :androidApp:assembleDebug
```

#### Desktop (Windows)
```bash
./gradlew :desktopApp:run
```

#### iOS
Open `iosApp/iosApp.xcodeproj` in Xcode and run.

## Current Status

**Android:** Working implementation with AccessibilityService for content blocking and usage stats tracking.

**Desktop/iOS:** Project structure exists but not yet implemented.

## Documentation

- **[Getting Started Guide](docs/GETTING_STARTED.md)** - Setup and initial configuration
- **[Quick Start](docs/QUICK_START.md)** - Fast reference for Java developers
- **[Java to Kotlin Guide](docs/JAVA_TO_KOTLIN_GUIDE.md)** - Comprehensive guide for Java developers

## Bundle ID
`com.goclean.app`

## Version
1.0.0
