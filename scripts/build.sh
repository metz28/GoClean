#!/bin/bash
# Build script for GoClean project

set -e

echo "Building GoClean..."

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    exit 1
fi

# Build shared module
echo "Building shared module..."
./gradlew :shared:build

# Build Android app
echo "Building Android app..."
./gradlew :androidApp:assembleDebug

# Build Desktop app
echo "Building Desktop app..."
./gradlew :desktopApp:build

echo "Build completed successfully!"
echo ""
echo "Outputs:"
echo "- Android APK: androidApp/build/outputs/apk/debug/"
echo "- Desktop app: desktopApp/build/libs/"
