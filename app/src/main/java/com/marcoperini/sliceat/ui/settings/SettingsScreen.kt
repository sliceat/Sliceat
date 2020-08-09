package com.marcoperini.sliceat.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.hellmund.viewpager2indicator.ViewPager2Indicator
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.maps.MapsScreen
import com.marcoperini.sliceat.ui.Navigator
import com.marcoperini.sliceat.ui.onboarding.OnboardingAdapter
import com.marcoperini.sliceat.utils.sharedpreferences.Key
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorage
import kotlinx.android.synthetic.main.onboarding_container.view_pager2
import org.koin.android.ext.android.inject

class SettingsScreen : AppCompatActivity() {
    private val navigator: Navigator by inject()
    private val prefs: KeyValueStorage by inject()

    private lateinit var pageIndicator: ViewPager2Indicator

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, SettingsScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_screen)

        view_pager2.adapter = SettingsAdapter(this)

        setupView()
        setOnClickListeners()

        view_pager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (view_pager2.currentItem == 2) {

                } else {

                }
            }
        })
    }

    private fun setupView() {
        pageIndicator = findViewById(R.id.indicator)
        pageIndicator.attachTo(view_pager2)
    }

    private fun setOnClickListeners() {

    }

    private fun exit() {
//        navigator.goToAuthenticationScreen()
        navigator.goToMapsScreen()
        prefs.putBoolean(Key.FIRST_START, false)
        finish()
    }
}
