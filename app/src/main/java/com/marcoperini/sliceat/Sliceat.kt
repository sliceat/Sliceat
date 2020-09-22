package com.marcoperini.sliceat

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.widget.Toast
import androidx.multidex.MultiDexApplication
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.marcoperini.sliceat.di.androidComponents
import com.marcoperini.sliceat.di.appComponents
import com.marcoperini.sliceat.di.databaseComponents
import com.marcoperini.sliceat.di.viewModels
import com.marcoperini.sliceat.di.volleyComponents
import com.marcoperini.sliceat.notification.NotificationHelper
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import timber.log.Timber

const val TAG_LOGGING = "SLICEAT"

class Sliceat : MultiDexApplication() {

    @SuppressLint("StringFormatInvalid")
    override fun onCreate() {
        super.onCreate()
        setupDI()
        setupLogging()

        if (checkGooglePlayServices()) {
            registerTokenFirebase()
        }

        NotificationHelper.registerDefaultNotificationChannel(
            resources,
            getSystemService(FirebaseMessagingService.NOTIFICATION_SERVICE) as NotificationManager
        )
    }

    private fun checkGooglePlayServices(): Boolean {
        // 1
        val status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        // 2
        return if (status != ConnectionResult.SUCCESS) {
            Timber.e("Error")
            // ask user to update google play services and manage the error.
            false
        } else {
            // 3
            Timber.i("Google play services updated")
            true
        }
    }

    private fun registerTokenFirebase() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                // 2
                if (!task.isSuccessful) {
                    Timber.w("getInstanceId failed $task.exception")
                    return@OnCompleteListener
                }
                // 3
                val token = task.result?.token

                // 4
                Timber.d(token)
//                Toast.makeText(baseContext, token, Toast.LENGTH_LONG).show()
            })
    }

    private fun setupDI() {
        startKoin {
            androidLogger(Level.NONE)
//            androidLogger()
            androidContext(this@Sliceat)

            val appSetupModule = module {
                single { BuildConfig.DEBUG }
            }

            modules(
                listOf(
                    appSetupModule,
                    androidComponents,
                    appComponents,
                    databaseComponents,
                    viewModels,
                    volleyComponents
                )
            )
        }
    }

    private fun setupLogging() {
        Timber.plant(Timber.DebugTree())
        Timber.tag(TAG_LOGGING)
    }
}
