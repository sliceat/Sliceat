package com.marcoperini.sliceat.ui

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.marcoperini.sliceat.ui.authentication.AuthenticationScreen
import com.marcoperini.sliceat.ui.launch.LaunchScreen2
import com.marcoperini.sliceat.ui.main.MainScreen
import com.marcoperini.sliceat.ui.onboarding.OnboardingScreen

interface Navigator {
    fun goToMainScreen()
    fun goToOnBoarding()
    fun goToLaunchScreen2()
    fun goToAuthenticationScreen1()
}

class AppNavigator(private val context: Context) : Navigator {

//    override fun goToSettings() {
//        ContextCompat.startActivity(context, SettingsActivity.getIntent(context), null)
//    }

    override fun goToLaunchScreen2() {
        ContextCompat.startActivity(
            context,
            Intent(context, LaunchScreen2::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            null
        )
    }

    override fun goToAuthenticationScreen1() {
        ContextCompat.startActivity(
            context,
            Intent(context, AuthenticationScreen::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK),
            null
        )
    }

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
            Intent(context, OnboardingScreen::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            null
        )
    }
}
