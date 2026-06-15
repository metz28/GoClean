package com.goclean.app.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent

/**
 * AccessibilityService that detects and blocks short-form video content
 * (Instagram Reels, YouTube Shorts, TikTok)
 */
class BlockingAccessibilityService : AccessibilityService() {

    companion object {
        private const val TAG = "BlockingService"

        // Package names for apps to monitor
        private const val INSTAGRAM_PACKAGE = "com.instagram.android"
        private const val YOUTUBE_PACKAGE = "com.google.android.youtube"
        private const val TIKTOK_PACKAGE = "com.zhiliaoapp.musically" // TikTok

        // Activity/content identifiers for short-form video content
        private val REELS_IDENTIFIERS = setOf(
            "reel",
            "clips",
            "short"
        )
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d(TAG, "BlockingAccessibilityService connected")

        // Configure the service to monitor specific apps
        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED or
                    AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED

            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC

            // Monitor specific packages
            packageNames = arrayOf(
                INSTAGRAM_PACKAGE,
                YOUTUBE_PACKAGE,
                TIKTOK_PACKAGE
            )

            // Don't need to retrieve window content for basic detection
            flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS
        }

        serviceInfo = info
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event ?: return

        val packageName = event.packageName?.toString() ?: return
        val className = event.className?.toString() ?: return

        Log.d(TAG, "Event: package=$packageName, class=$className")

        // Detect short-form video content
        when (packageName) {
            INSTAGRAM_PACKAGE -> handleInstagram(className)
            YOUTUBE_PACKAGE -> handleYouTube(className)
            TIKTOK_PACKAGE -> handleTikTok(className)
        }
    }

    private fun handleInstagram(className: String) {
        // Check if user is viewing Reels
        val classNameLower = className.lowercase()
        if (REELS_IDENTIFIERS.any { classNameLower.contains(it) }) {
            Log.d(TAG, "Instagram Reels detected: $className")
            blockContent("Instagram Reels")
        }
    }

    private fun handleYouTube(className: String) {
        // Check if user is viewing Shorts
        val classNameLower = className.lowercase()
        if (classNameLower.contains("shorts") || classNameLower.contains("reel")) {
            Log.d(TAG, "YouTube Shorts detected: $className")
            blockContent("YouTube Shorts")
        }
    }

    private fun handleTikTok(className: String) {
        // TikTok is entirely short-form video content
        Log.d(TAG, "TikTok detected: $className")
        blockContent("TikTok")
    }

    private fun blockContent(contentType: String) {
        Log.d(TAG, "Blocking: $contentType")

        // Navigate back to home or show blocking screen
        performGlobalAction(GLOBAL_ACTION_HOME)

        // Could also show a custom blocking activity
        // val intent = Intent(this, BlockingActivity::class.java).apply {
        //     flags = Intent.FLAG_ACTIVITY_NEW_TASK
        //     putExtra("blocked_content", contentType)
        // }
        // startActivity(intent)
    }

    override fun onInterrupt() {
        Log.d(TAG, "BlockingAccessibilityService interrupted")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "BlockingAccessibilityService destroyed")
    }
}
