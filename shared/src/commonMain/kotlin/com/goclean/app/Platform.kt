package com.goclean.app

/**
 * Platform interface - demonstrates expect/actual pattern in KMP
 * Each platform will provide its own implementation
 */
interface Platform {
    val name: String
    val version: String
}

/**
 * Expect declaration - tells Kotlin to expect an implementation on each platform
 * Each platform module (androidMain, iosMain, desktopMain) must provide an actual implementation
 */
expect fun getPlatform(): Platform
