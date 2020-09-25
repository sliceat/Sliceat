package com.marcoperini.sliceat.ui

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
    fun goToMainScreen()
    fun goToOnBoarding()
    fun goToLaunchScreen2()
    fun goToAuthenticationScreen()
    fun goToAccessScreen()
    fun goToSignInScreen1()
    fun goToSignInScreen2()
    fun goToSignInScreen3()
    fun goToSignInScreen4()
    fun goToSignInScreen5()
    fun goToMapsScreen()
    fun goToSettingsScreen()
    fun goToLetterManagersScreen()
    fun goToFiltersScreen()
    fun goToMailScreen()
    fun goToRestaurantsScreen()
    fun goToSecureInfoScreen()
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

    override fun goToAuthenticationScreen() {
        ContextCompat.startActivity(
            context,
            Intent(context, FirstScreen::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK),
            null
        )
    }

    override fun goToAccessScreen() {
        ContextCompat.startActivity(context, AccessScreen.getIntent(context), null)
    }

    override fun goToSignInScreen1() {
        ContextCompat.startActivity(context, SignInScreen1.getIntent(context), null)
    }

    override fun goToSignInScreen2() {
        ContextCompat.startActivity(context, SignInScreen2.getIntent(context), null)
    }

    override fun goToSignInScreen3() {
        ContextCompat.startActivity(context, SignInScreen3.getIntent(context), null)
    }

    override fun goToSignInScreen4() {
        ContextCompat.startActivity(context, SignInScreen4.getIntent(context), null)
    }

    override fun goToSignInScreen5() {
        ContextCompat.startActivity(context, SignInScreen5.getIntent(context), null)
    }

    override fun goToMapsScreen() {
        ContextCompat.startActivity(context, MapsScreen.getIntent(context), null)
    }

    override fun goToSettingsScreen() {
        ContextCompat.startActivity(context, SettingsScreen.getIntent(context), null)
    }

    override fun goToLetterManagersScreen() {
        ContextCompat.startActivity(context, LetterManagersScreen.getIntent(context), null)
    }

    override fun goToFiltersScreen() {
        ContextCompat.startActivity(context, FiltersScreen.getIntent(context), null)
    }

    override fun goToMailScreen() {
        ContextCompat.startActivity(context, MailsScreen.getIntent(context), null)
    }

    override fun goToRestaurantsScreen() {
        ContextCompat.startActivity(context, RestaurantsScreen.getIntent(context), null)
    }

    override fun goToSecureInfoScreen() {
        ContextCompat.startActivity(context, SecureInfoScreen.getIntent(context), null)
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
            Intent(context, OnboardingScreen::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK),
            null
        )
    }
}
