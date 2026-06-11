package com.goclean.app

/**
 * Desktop/Windows implementation of Platform interface
 * This is the "actual" implementation for Desktop (Windows/macOS/Linux)
 */
class DesktopPlatform : Platform {
    override val name: String = "Desktop (${System.getProperty("os.name")})"
    override val version: String = System.getProperty("os.version")
}

/**
 * Actual implementation for Desktop platform
 */
actual fun getPlatform(): Platform = DesktopPlatform()
