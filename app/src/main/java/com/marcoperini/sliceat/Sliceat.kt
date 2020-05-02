package com.marcoperini.sliceat

import androidx.multidex.MultiDexApplication
import com.marcoperini.sliceat.di.androidComponents
import com.marcoperini.sliceat.di.appComponents
import com.marcoperini.sliceat.di.databaseComponents
import com.marcoperini.sliceat.di.viewModels
import com.marcoperini.sliceat.di.volleyComponents
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

const val TAG_LOGGING = "SLICEAT"

class Sliceat : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        setupDI()
        setupLogging()
    }

    private fun setupDI() {
        startKoin {
            androidLogger()
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
