package com.marcoperini.sliceat.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import com.marcoperini.sliceat.R
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_back_button
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_button
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_title
import org.koin.android.ext.android.inject

class IntolleranceScreen : AppCompatActivity() {
    private lateinit var close: ImageButton

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, IntolleranceScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    private val navigator: Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intollerance_screen)

        setupView()
        clickListener()
    }

    private fun setupView() {
        close = findViewById(R.id.closeButton)
    }

    private fun clickListener() {
        close.setOnClickListener {
            navigator.goToSettingsScreen()
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigator.goToSettingsScreen()
    }
}


