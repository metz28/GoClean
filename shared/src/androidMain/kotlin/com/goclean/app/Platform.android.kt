package com.goclean.app

import android.os.Build

/**
 * Android implementation of Platform interface
 * This is the "actual" implementation for Android
 */
class AndroidPlatform : Platform {
    override val name: String = "Android"
    override val version: String = "${Build.VERSION.SDK_INT}"
}

/**
 * Actual implementation for Android platform
 */
actual fun getPlatform(): Platform = AndroidPlatform()
