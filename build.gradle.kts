// Root build file - configure plugins and versions here
plugins {
    // Kotlin Multiplatform plugin
    kotlin("multiplatform") version "2.4.0" apply false

    // Android plugin
    id("com.android.application") version "8.2.2" apply false
    id("com.android.library") version "8.2.2" apply false

    // Compose Multiplatform plugin
    id("org.jetbrains.compose") version "1.5.12" apply false
}

// Clean task for the root project
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
