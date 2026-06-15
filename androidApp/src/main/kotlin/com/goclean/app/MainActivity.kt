package com.goclean.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goclean.app.service.BlockingAccessibilityService
import com.goclean.app.util.UsageStatsHelper

/**
 * Main Activity for GoClean Android app
 * Entry point for the Android application
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoCleanTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun GoCleanTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        content()
    }
}

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    var isAccessibilityEnabled by remember { mutableStateOf(false) }
    var hasUsageStatsPermission by remember { mutableStateOf(false) }
    var screenTimeToday by remember { mutableStateOf("") }

    // Check permissions on composition
    LaunchedEffect(Unit) {
        isAccessibilityEnabled = isAccessibilityServiceEnabled(context)
        val usageHelper = UsageStatsHelper(context)
        hasUsageStatsPermission = usageHelper.hasUsageStatsPermission()
        if (hasUsageStatsPermission) {
            val timeMs = usageHelper.getTodayScreenTime()
            screenTimeToday = usageHelper.formatTime(timeMs)
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "GoClean",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Stay focused, stay productive",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Accessibility Service Status
            PermissionCard(
                title = "Accessibility Service",
                isGranted = isAccessibilityEnabled,
                onEnableClick = {
                    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                    context.startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Usage Stats Permission
            PermissionCard(
                title = "Usage Stats",
                isGranted = hasUsageStatsPermission,
                onEnableClick = {
                    val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                    context.startActivity(intent)
                }
            )

            if (hasUsageStatsPermission && screenTimeToday.isNotEmpty()) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Screen time today: $screenTimeToday",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun PermissionCard(
    title: String,
    isGranted: Boolean,
    onEnableClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (isGranted) {
            Text(
                text = "✓ Enabled",
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            Button(onClick = onEnableClick) {
                Text("Enable")
            }
        }
    }
}

/**
 * Check if the BlockingAccessibilityService is enabled
 */
fun isAccessibilityServiceEnabled(context: Context): Boolean {
    val expectedComponentName = "${context.packageName}/${BlockingAccessibilityService::class.java.name}"
    val enabledServicesSetting = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
    ) ?: return false

    return enabledServicesSetting.contains(expectedComponentName)
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    GoCleanTheme {
        HomeScreen()
    }
}
