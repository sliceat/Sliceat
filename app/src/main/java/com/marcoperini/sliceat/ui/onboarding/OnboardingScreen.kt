package com.marcoperini.sliceat.ui.onboarding

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.hellmund.viewpager2indicator.ViewPager2Indicator
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import kotlinx.android.synthetic.main.onboarding_container.view_pager2
import org.koin.android.ext.android.inject

class OnboardingScreen : AppCompatActivity() {

    private val navigator: Navigator by inject()

    private lateinit var skipButton: Button
    private lateinit var pageIndicator: ViewPager2Indicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.onboarding_container)

        view_pager2.adapter = OnboardingAdapter(this)
        skipButton = findViewById(R.id.skipButton)
        pageIndicator = findViewById(R.id.indicator)
        pageIndicator.attachTo(view_pager2)
        setOnClickListeners()

        view_pager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (view_pager2.currentItem == 2) {
                    skipButton.isClickable = false
                    skipButton.setTextColor(resources.getColor(R.color.white))
                } else {
                    skipButton.isClickable = true
                    skipButton.setTextColor(resources.getColor(R.color.orange))
                }
            }
        })
//        val dotsIndicator = findViewById<WormDotsIndicator>(R.id.dots_indicator)
//        dotsIndicator.setViewPager2(view_pager2)
    }

    fun hideButtonSkip() {
        skipButton.visibility = View.GONE
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
