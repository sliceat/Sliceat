package com.marcoperini.sliceat.ui.onboarding

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.hellmund.viewpager2indicator.ViewPager2Indicator
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import com.marcoperini.sliceat.utils.sharedpreferences.FIRST_START
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorage
import kotlinx.android.synthetic.main.onboarding_container.view_pager2
import org.koin.android.ext.android.inject

class OnboardingScreen(private val prefs: KeyValueStorage) : AppCompatActivity() {

    private val navigator: Navigator by inject()

    private lateinit var skipButton: Button
    private lateinit var pageIndicator: ViewPager2Indicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.onboarding_container)

        view_pager2.adapter = OnboardingAdapter(this)

        setupView()
        setOnClickListeners()

        view_pager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (view_pager2.currentItem == 2) {
//                    skipButton.isClickable = false
                    skipButton.text = resources.getString(R.string.entry)
//                    skipButton.setTextColor(resources.getColor(R.color.white))
                } else {
                    skipButton.text = resources.getString(R.string.skip)
                    skipButton.isClickable = true
                    skipButton.setTextColor(resources.getColor(R.color.orange))
                }
            }
        })
    }

    private fun setupView() {
        skipButton = findViewById(R.id.skipButton)
        pageIndicator = findViewById(R.id.indicator)
        pageIndicator.attachTo(view_pager2)
    }

    private fun setOnClickListeners() {
        skipButton.setOnClickListener {
            exit()
        }
    }

    private fun exit() {
        navigator.goToMainScreen()
        prefs.putBoolean(FIRST_START, false)
        finish()
    }
}
