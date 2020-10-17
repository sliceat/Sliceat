package com.marcoperini.sliceat.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.marcoperini.sliceat.ui.maps.ui.MapsScreen
import com.marcoperini.sliceat.ui.authentication.access.AccessScreen
import com.marcoperini.sliceat.ui.authentication.firstscreen.FirstScreen
import com.marcoperini.sliceat.ui.authentication.signin.signin4.SignInScreen4
import com.marcoperini.sliceat.ui.authentication.signin.signin1.SignInScreen1
import com.marcoperini.sliceat.ui.authentication.signin.signin2.SignInScreen2
import com.marcoperini.sliceat.ui.authentication.signin.signin3.SignInScreen3
import com.marcoperini.sliceat.ui.authentication.signin.signin5.SignInScreen5
import com.marcoperini.sliceat.ui.filters.FiltersScreen
import com.marcoperini.sliceat.ui.launch.LaunchScreen2
import com.marcoperini.sliceat.ui.mail.MailsScreen
import com.marcoperini.sliceat.ui.main.MainScreen
import com.marcoperini.sliceat.ui.onboarding.OnboardingScreen
import com.marcoperini.sliceat.ui.restaurants.RestaurantsScreen
import com.marcoperini.sliceat.ui.settings.SettingsScreen

interface Navigator {
    fun goToMainScreen(activity: Activity)
    fun goToOnBoarding(activity: Activity)
    fun goToLaunchScreen2(activity: Activity)
    fun goToAuthenticationScreen(activity: Activity)
    fun goToAccessScreen(activity: Activity)
    fun goToSignInScreen1(activity: Activity)
    fun goToSignInScreen2(activity: Activity)
    fun goToSignInScreen3(activity: Activity)
    fun goToSignInScreen4(activity: Activity)
    fun goToSignInScreen5(activity: Activity)
    fun goToMapsScreen(activity: Activity)
    fun goToSettingsScreen(activity: Activity)
    fun goToLetterManagersScreen(activity: Activity)
    fun goToFiltersScreen(activity: Activity)
    fun goToMailScreen(activity: Activity)
    fun goToRestaurantsScreen(activity: Activity, infoRestaurant: String)
    fun goToSecureInfoScreen(activity: Activity)
    fun goToIntolleranceScreen(activity: Activity)
}

class AppNavigator() : Navigator {

    override fun goToSettings() {
        ContextCompat.startActivity(context, SettingsActivity.getIntent(context), null)
    }

    override fun goToLaunchScreen2(activity: Activity) {
        ContextCompat.startActivity(
            activity,
            Intent(activity, LaunchScreen2::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            null
        )
    }

    override fun goToAuthenticationScreen(activity: Activity) {
        ContextCompat.startActivity(
            activity,
            Intent(activity, FirstScreen::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK),
            null
        )
    }

    override fun goToAccessScreen(activity: Activity) {
        ContextCompat.startActivity(activity, AccessScreen.getIntent(activity), null)
    }

    override fun goToSignInScreen1(activity: Activity) {
        ContextCompat.startActivity(activity, SignInScreen1.getIntent(activity), null)
    }

    override fun goToSignInScreen2(activity: Activity) {
        ContextCompat.startActivity(activity, SignInScreen2.getIntent(activity), null)
    }

    override fun goToSignInScreen3(activity: Activity) {
        ContextCompat.startActivity(activity, SignInScreen3.getIntent(activity), null)
    }

    override fun goToSignInScreen4(activity: Activity) {
        ContextCompat.startActivity(activity, SignInScreen4.getIntent(activity), null)
    }

    override fun goToSignInScreen5(activity: Activity) {
        ContextCompat.startActivity(activity, SignInScreen5.getIntent(activity), null)
    }

    override fun goToMapsScreen(activity: Activity) {
        ContextCompat.startActivity(activity, MapsScreen.getIntent(activity), null)
    }

    override fun goToSettingsScreen(activity: Activity) {
        ContextCompat.startActivity(activity, SettingsScreen.getIntent(activity), null)
    }

    override fun goToLetterManagersScreen(activity: Activity) {
        ContextCompat.startActivity(activity, LetterManagersScreen.getIntent(activity), null)
    }

    override fun goToFiltersScreen(activity: Activity) {
        ContextCompat.startActivity(activity, FiltersScreen.getIntent(activity), null)
    }

    override fun goToMailScreen(activity: Activity) {
        ContextCompat.startActivity(activity, MailsScreen.getIntent(activity), null)
    }

    override fun goToRestaurantsScreen(activity: Activity, infoRestaurant: String) {
        ContextCompat.startActivity(activity, RestaurantsScreen.getIntent(activity, infoRestaurant), null)
    }

    override fun goToSecureInfoScreen(activity: Activity) {
        ContextCompat.startActivity(activity, SecureInfoScreen.getIntent(activity), null)
    }

    override fun goToIntolleranceScreen(activity: Activity) {
        ContextCompat.startActivity(activity, IntolleranceScreen.getIntent(activity), null)
    }

    override fun goToMainScreen(activity: Activity) {
        ContextCompat.startActivity(
            activity,
            Intent(activity, MainScreen::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK),
            null
        )
    }

    override fun goToOnBoarding(activity: Activity) {
        ContextCompat.startActivity(
            activity,
            Intent(activity, OnboardingScreen::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK),
            null
        )
    }
}
