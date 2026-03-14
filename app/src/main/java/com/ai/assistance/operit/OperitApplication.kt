package com.ai.assistance.operit

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class OperitApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = listOf(
                NotificationChannel(
                    CHANNEL_AI_SERVICE,
                    "AI Service",
                    NotificationManager.IMPORTANCE_LOW
                ).apply {
                    description = "AI Assistant Service Notifications"
                },
                NotificationChannel(
                    CHANNEL_SPEECH_SERVICE,
                    "Speech Service",
                    NotificationManager.IMPORTANCE_LOW
                ).apply {
                    description = "Speech Recognition Service Notifications"
                },
                NotificationChannel(
                    CHANNEL_SCREEN_CAPTURE,
                    "Screen Capture",
                    NotificationManager.IMPORTANCE_LOW
                ).apply {
                    description = "Screen Capture Service Notifications"
                }
            )

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            channels.forEach { notificationManager.createNotificationChannel(it) }
        }
    }

    companion object {
        const val CHANNEL_AI_SERVICE = "ai_service"
        const val CHANNEL_SPEECH_SERVICE = "speech_service"
        const val CHANNEL_SCREEN_CAPTURE = "screen_capture"

        @Volatile
        private var instance: OperitApplication? = null

        fun getInstance(): OperitApplication {
            return instance ?: throw IllegalStateException("Application not initialized")
        }
    }
}
