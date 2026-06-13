# Contributing to GoClean

Thank you for your interest in contributing to GoClean!

## Development Setup

1. **Prerequisites**
   - JDK 11 or higher
   - Android Studio (for Android development)
   - Xcode (for iOS development, macOS only)

2. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd GoClean
   ```

3. **Build the project**
   ```bash
   ./gradlew build
   ```

## Code Style

- Follow the official [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use 4 spaces for indentation
- Maximum line length: 120 characters
- The project includes an `.editorconfig` file for consistent formatting

## Commit Message Format

We follow the conventional commits specification:

```
<type>(topic): message

Examples:
feat(topic): message
fix(topic): message
chore(topic): message
refactor(topic): message
test(topic): message
```

**Types:**
- `feat`: New feature
- `fix`: Bug fix
- `chore`: Maintenance tasks
- `refactor`: Code restructuring
- `test`: Adding or updating tests
- `docs`: Documentation changes

## Pull Request Process

1. Create a new branch from `main` for your feature or fix
2. Make your changes following the code style guidelines
3. Add tests for new functionality
4. Ensure all tests pass: `./gradlew test`
5. Update documentation if needed
6. Commit your changes with descriptive commit messages
7. Push to your fork and submit a pull request

## Testing

- Write tests for new features
- Ensure existing tests pass before submitting PR
- Run tests: `./gradlew test`
- Android tests: `./gradlew :androidApp:testDebugUnitTest`
- Shared module tests: `./gradlew :shared:testDebugUnitTest`

## Project Structure

```
GoClean/
├── shared/          # Shared KMP code
├── androidApp/      # Android application
├── desktopApp/      # Desktop application
└── iosApp/          # iOS application
```

## Questions?

Feel free to open an issue for questions or discussions.
