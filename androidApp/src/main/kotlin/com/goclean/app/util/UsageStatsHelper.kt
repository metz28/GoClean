package com.goclean.app.util

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.util.Log
import java.util.Calendar

/**
 * Helper class for tracking app usage using UsageStatsManager
 */
class UsageStatsHelper(private val context: Context) {

    companion object {
        private const val TAG = "UsageStatsHelper"

        // Package names to monitor
        private const val INSTAGRAM_PACKAGE = "com.instagram.android"
        private const val YOUTUBE_PACKAGE = "com.google.android.youtube"
        private const val TIKTOK_PACKAGE = "com.zhiliaoapp.musically"
    }

    private val usageStatsManager: UsageStatsManager? by lazy {
        context.getSystemService(Context.USAGE_STATS_SERVICE) as? UsageStatsManager
    }

    /**
     * Get usage stats for monitored apps in the specified time range
     * @param intervalType One of UsageStatsManager.INTERVAL_* constants
     * @return Map of package name to total time spent (in milliseconds)
     */
    fun getMonitoredAppsUsage(intervalType: Int = UsageStatsManager.INTERVAL_DAILY): Map<String, Long> {
        val usageStats = getUsageStats(intervalType) ?: return emptyMap()

        val monitoredPackages = setOf(INSTAGRAM_PACKAGE, YOUTUBE_PACKAGE, TIKTOK_PACKAGE)

        return usageStats
            .filter { it.packageName in monitoredPackages }
            .associate { it.packageName to it.totalTimeInForeground }
    }

    /**
     * Get total screen time for today
     */
    fun getTodayScreenTime(): Long {
        val usageStats = getUsageStats(UsageStatsManager.INTERVAL_DAILY) ?: return 0L
        return usageStats.sumOf { it.totalTimeInForeground }
    }

    /**
     * Get screen time for a specific app today
     */
    fun getAppScreenTimeToday(packageName: String): Long {
        val usageStats = getUsageStats(UsageStatsManager.INTERVAL_DAILY) ?: return 0L
        return usageStats
            .firstOrNull { it.packageName == packageName }
            ?.totalTimeInForeground ?: 0L
    }

    /**
     * Get usage statistics for the current day
     */
    private fun getUsageStats(intervalType: Int): List<UsageStats>? {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val startTime = calendar.timeInMillis
        val endTime = System.currentTimeMillis()

        return try {
            usageStatsManager?.queryUsageStats(intervalType, startTime, endTime)
        } catch (e: Exception) {
            Log.e(TAG, "Error querying usage stats", e)
            null
        }
    }

    /**
     * Check if usage stats permission is granted
     */
    fun hasUsageStatsPermission(): Boolean {
        val usageStats = try {
            usageStatsManager?.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                System.currentTimeMillis() - 1000 * 60,
                System.currentTimeMillis()
            )
        } catch (e: Exception) {
            null
        }
        return !usageStats.isNullOrEmpty()
    }

    /**
     * Format milliseconds to human-readable time
     */
    fun formatTime(milliseconds: Long): String {
        val totalSeconds = milliseconds / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        return when {
            hours > 0 -> "${hours}h ${minutes}m"
            minutes > 0 -> "${minutes}m ${seconds}s"
            else -> "${seconds}s"
        }
    }
}
