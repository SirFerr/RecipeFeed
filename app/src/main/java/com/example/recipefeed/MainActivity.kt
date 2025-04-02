package com.example.recipefeed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.work.*
import com.example.recipefeed.data.local.AppSettingsSharedPreferencesManager
import com.example.recipefeed.feature.navigation.Navigation
import com.example.recipefeed.ui.theme.RecipeFeedTheme
import com.example.recipefeed.workers.NotificationWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            var darkTheme by remember {
                mutableStateOf(AppSettingsSharedPreferencesManager(context = context).isThemeDark())
            }

            RecipeFeedTheme(darkTheme = darkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(onThemeUpdated = { darkTheme = !darkTheme })
                }
            }
        }

        // Настройка WorkManager для периодической проверки уведомлений
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "notification_check",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
    }
}