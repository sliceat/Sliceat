package com.marcoperini.sliceat.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.marcoperini.sliceat.R
import org.koin.android.ext.android.inject

class SecureInfoScreen : AppCompatActivity() {

    private lateinit var close: ImageButton

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, SecureInfoScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    private val navigator: Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secure_info_screen)

        setupView()
        clickListener()
    }

    private fun setupView() {
        close = findViewById(R.id.closeButton)
    }

    private fun clickListener() {
        close.setOnClickListener {
            navigator.goToSettingsScreen(this)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigator.goToSettingsScreen(this)
    }
}
