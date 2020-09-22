package com.marcoperini.sliceat.ui.onboarding

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.hellmund.viewpager2indicator.ViewPager2Indicator
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.FIRST_START
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorage
import kotlinx.android.synthetic.main.onboarding_container.view_pager2
import org.koin.android.ext.android.inject

class OnboardingScreen : AppCompatActivity() {

    private val navigator: Navigator by inject()
    private val prefs: KeyValueStorage by inject()

    private lateinit var skipButton: Button
    private lateinit var entryButton: Button
    private lateinit var pageIndicator: ViewPager2Indicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.onboarding_container)

        view_pager2.adapter = OnboardingAdapter(this)

        setupView()
        setOnClickListeners()

        view_pager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (view_pager2.currentItem == 2) {
                    skipButton.visibility = View.GONE
                    entryButton.visibility = View.VISIBLE
                    entryButton.setTextColor(ContextCompat.getColor(this@OnboardingScreen, R.color.black80))
                } else {
                    skipButton.visibility = View.VISIBLE
                    entryButton.visibility = View.GONE

                    skipButton.setTextColor(ContextCompat.getColor(this@OnboardingScreen, R.color.white))
                }
            }
        })
    }

    private fun setupView() {
        skipButton = findViewById(R.id.skipButton)
        entryButton = findViewById(R.id.start)
        pageIndicator = findViewById(R.id.indicator)
        pageIndicator.attachTo(view_pager2)
    }

    private fun setOnClickListeners() {
        entryButton.setOnClickListener {
            exit()
        }
        skipButton.setOnClickListener {
            exit()
        }
    }

    private fun exit() {
//        navigator.goToAuthenticationScreen()
        navigator.goToMapsScreen()
        prefs.putBoolean(FIRST_START, false)
        finish()
    }
}
