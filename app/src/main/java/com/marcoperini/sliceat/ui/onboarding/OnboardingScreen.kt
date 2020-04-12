package com.marcoperini.sliceat.ui.onboarding

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.jem.liquidswipe.LiquidSwipeViewPager
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import com.rd.PageIndicatorView
import org.koin.android.ext.android.inject

class OnboardingScreen : AppCompatActivity() {

    private val navigator: Navigator by inject()

    private lateinit var skipButton: Button
    private lateinit var onboardingAdapter: OnboardingAdapter
    private lateinit var onboardingViewPager: LiquidSwipeViewPager
    private lateinit var pageIndicator: PageIndicatorView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding_container)

        setViews()
        setOnClickListeners()

        onboardingViewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                if (onboardingViewPager.currentItem == 0) {
                    // If first item, set previousButton invisible and nextButton visible
                    skipButton.visibility = View.VISIBLE
                } else {
                    // Else both buttons visible
                    skipButton.visibility = View.VISIBLE
                }
            }
        })

        onboardingViewPager.setCurrentItem(0, true)
    }

    private fun setViews() {
        onboardingViewPager = findViewById(R.id.onboardingViewpager)
        onboardingAdapter = OnboardingAdapter(supportFragmentManager, resources.getStringArray(R.array.titleArray))
        onboardingViewPager.adapter = onboardingAdapter

        pageIndicator = findViewById(R.id.pageIndicatorView)
        pageIndicator.setViewPager(onboardingViewPager)

        skipButton = findViewById(R.id.skipButton)

    }

    private fun setOnClickListeners() {
        skipButton.setOnClickListener {
            exit()
        }
    }

    private fun exit() {
        navigator.goToMainScreen()
        finish()
    }

}
