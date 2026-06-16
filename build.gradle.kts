// Root build file - configure plugins and versions here
plugins {
    // Kotlin Multiplatform plugin
    kotlin("multiplatform") version "1.9.22" apply false

    // Android plugin
    id("com.android.application") version "9.2.1" apply false
    id("com.android.library") version "9.2.1" apply false

    // Compose Multiplatform plugin
    id("org.jetbrains.compose") version "1.5.12" apply false
}

// Clean task for the root project
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
