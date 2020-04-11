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

class OnboardingScreen : AppCompatActivity(){

    private val navigator: Navigator by inject()

    private lateinit var homeButton: TextView
    private lateinit var nextButton: Button
    private lateinit var onboardingAdapter: OnboardingAdapter
    private lateinit var onboardingViewPager: LiquidSwipeViewPager
    private lateinit var pageIndicator: PageIndicatorView
    private lateinit var previousButton: TextView

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
                    previousButton.visibility = View.INVISIBLE
                    nextButton.visibility = View.VISIBLE
                } else {
                    // Else both buttons visible
                    previousButton.visibility = View.VISIBLE
                    nextButton.visibility = View.VISIBLE
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

        homeButton = findViewById(R.id.onboardingSkip)
        previousButton = findViewById(R.id.previous)
        nextButton = findViewById(R.id.nextButton)

        previousButton.visibility = View.INVISIBLE
        homeButton.visibility = View.VISIBLE
    }

    private fun setOnClickListeners() {
        previousButton.setOnClickListener { onboardingViewPager.currentItem -= 1 }
        homeButton.setOnClickListener {
            analyticsService.trackEvent(TrackingEvent.GameRulesSkip)
            exit()
        }
        nextButton.setOnClickListener {
            if (onboardingViewPager.currentItem == onboardingAdapter.count - 1) {
                analyticsService.trackEvent(TrackingEvent.GameRulesEnd)
                exit()
            } else
                onboardingViewPager.currentItem += 1
        }
    }

    private fun exit() {
        navigator.goToTutorial()
        finish()
    }

}
