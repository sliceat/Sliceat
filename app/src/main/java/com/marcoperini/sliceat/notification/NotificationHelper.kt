package com.marcoperini.sliceat.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.res.Resources
import android.os.Build
import com.marcoperini.sliceat.R
import timber.log.Timber

class NotificationHelper private constructor() {
    companion object {
        fun registerDefaultNotificationChannel(resources: Resources, notificationManager: NotificationManager) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Timber.v("creating default notification channel")

                val name = "sliceat"
                val descriptionText = "notifiche"
                val defaultChannelId = resources.getString(R.string.notification_channel_id_default)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(defaultChannelId, name, importance)
                channel.description = descriptionText

                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}
