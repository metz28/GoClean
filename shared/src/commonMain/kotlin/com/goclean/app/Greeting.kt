package com.goclean.app

/**
 * Simple example class to verify KMP setup works
 * This runs on all platforms (Android, iOS, Desktop)
 */
class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello from ${platform.name} (${platform.version})!"
    }
}
