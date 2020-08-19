package com.marcoperini.sliceat.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.marcoperini.sliceat.R
import timber.log.Timber

class NotificationService : FirebaseMessagingService() {

    private lateinit var notificationManager: NotificationManager
    val TAG = "Service"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("new token: $token")
    }

    @SuppressLint("TimberArgCount")
    override fun onMessageReceived(notification: RemoteMessage) {
        super.onMessageReceived(notification)

        notification.data.isNotEmpty().let {
            val entityType = notification.data["entityType"]
            val entityUUID = notification.data["entityUUID"]
            val parentUUID = notification.data["parentUUID"]
            val actionType = notification.data["actionType"]
            Timber.v("notification received, entityType: $entityType, uuid: $entityUUID, parentUUID: $parentUUID, actionType: $actionType")

            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            Timber.d("From: %s", notification.from)
            Timber.d(TAG, "Notification Message Body: %s", notification.notification?.body)
            sendNotification(notification)
            val intent = Intent(this@NotificationService, FirebasePushNotificationScreen::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("message", notification.notification?.body)
            startActivity(intent)

        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage?) {
        val intent = Intent(this, FirebasePushNotificationScreen::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT)
        val notificationBuilder = NotificationCompat.Builder(this)
            .setContentText(remoteMessage?.notification?.body)
            .setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}
