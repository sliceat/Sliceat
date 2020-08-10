package com.marcoperini.sliceat.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.hellmund.viewpager2indicator.ViewPager2Indicator
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorage
import kotlinx.android.synthetic.main.onboarding_container.view_pager2
import org.koin.android.ext.android.inject

class SettingsScreen : AppCompatActivity() {
    private val navigator: Navigator by inject()
    private val prefs: KeyValueStorage by inject()

    private lateinit var pageIndicator: ViewPager2Indicator
    private lateinit var toolbar: androidx.constraintlayout.widget.ConstraintLayout
    private lateinit var toolbarBack: ImageView

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
        toolbar = findViewById(R.id.include_custom_toolbar)
        pageIndicator = findViewById(R.id.indicator)
        pageIndicator.attachTo(view_pager2)
        toolbarBack = findViewById<ImageView>(R.id.toolbar_back_button)
    }

    private fun setOnClickListeners() {
        toolbarBack.setOnClickListener {
            navigator.goToMapsScreen()
        }
    }

}
