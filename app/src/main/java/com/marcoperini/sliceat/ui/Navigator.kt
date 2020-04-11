package com.marcoperini.sliceat.ui

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.marcoperini.sliceat.ui.main.MainScreen
import com.marcoperini.sliceat.ui.onboarding.OnBoardingScreen
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorage

interface Navigator {
    fun goToMainScreen()
    fun goToOnBoarding()
}

class AppNavigator(private val context: Context, private val prefs: KeyValueStorage, private val gson: Gson) : Navigator {

//    override fun goToSettings() {
//        ContextCompat.startActivity(context, SettingsActivity.getIntent(context), null)
//    }

    override fun goToMainScreen() {
        ContextCompat.startActivity(
            context,
            Intent(context, MainScreen::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK),
            null
        )
    }

    override fun goToOnBoarding() {
        ContextCompat.startActivity(
            context,
            Intent(context, OnBoardingScreen::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            null
        )
    }
}
