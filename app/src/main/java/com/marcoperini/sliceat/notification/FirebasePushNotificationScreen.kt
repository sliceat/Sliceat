package com.marcoperini.sliceat.notification

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.Constants.MessagePayloadKeys.SENDER_ID
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.mail.MailsScreen
import timber.log.Timber
import java.io.IOException

class FirebasePushNotificationScreen : AppCompatActivity() {

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, FirebasePushNotificationScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_push_notification_screen)
        val intent = intent
        val message = intent.getStringExtra("message")
        if (!message.isNullOrEmpty()) {
            AlertDialog.Builder(this)
                .setTitle("Notification")
                .setMessage(message)
                .setPositiveButton("Ok") { _, _ -> }.show()
        }
    }
}
