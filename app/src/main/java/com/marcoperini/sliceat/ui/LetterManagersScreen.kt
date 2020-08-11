package com.marcoperini.sliceat.ui

import android.content.Context
import android.content.Intent
import android.drm.DrmStore
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.settings.SettingsScreen
import kotlinx.android.synthetic.main.toolbar_with_indicator.toolbar_main
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_back_button
import org.koin.android.ext.android.inject

class LetterManagersScreen : AppCompatActivity() {

    private lateinit var toolbar :androidx.appcompat.widget.Toolbar
    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, LetterManagersScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    private val navigator: Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letter_managers_screen)

        setupToolbar()
        clickListener()
    }

    override fun onResume() {
        super.onResume()
        toolbar.toolbar_back_button.visibility = View.VISIBLE
    }
    
    private fun setupToolbar() {
        toolbar = findViewById(R.id.include_custom_toolbar)
        toolbar.toolbar_back_button.visibility = View.VISIBLE
        toolbar.title = resources.getString(R.string.empty)
        setSupportActionBar(toolbar)
    }

    private fun clickListener() {
        toolbar.toolbar_back_button.setOnClickListener {
            navigator.goToSettingsScreen()
            finish()
            toolbar.toolbar_back_button.visibility = View.GONE
        }
    }
}
