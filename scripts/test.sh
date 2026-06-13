#!/bin/bash
# Test script for GoClean project

set -e

echo "Running tests for GoClean..."

# Run shared module tests
echo "Testing shared module..."
./gradlew :shared:test

# Run Android unit tests
echo "Testing Android app..."
./gradlew :androidApp:testDebugUnitTest

# Run Desktop tests
echo "Testing Desktop app..."
./gradlew :desktopApp:test

echo "All tests passed successfully!"
