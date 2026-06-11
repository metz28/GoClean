package com.goclean.app

import platform.UIKit.UIDevice

/**
 * iOS implementation of Platform interface
 * This is the "actual" implementation for iOS/macOS
 */
class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.model
    override val version: String = UIDevice.currentDevice.systemVersion
}

/**
 * Actual implementation for iOS platform
 */
actual fun getPlatform(): Platform = IOSPlatform()
