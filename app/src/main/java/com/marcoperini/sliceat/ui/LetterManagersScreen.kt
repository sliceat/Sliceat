package com.marcoperini.sliceat.ui

import android.content.Context
import android.content.Intent
import android.drm.DrmStore
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.settings.SettingsScreen
import org.koin.android.ext.android.inject

class LetterManagersScreen : AppCompatActivity() {

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, LetterManagersScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    private val navigator: Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letter_managers_screen)

        setupToolbar()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupToolbar() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.include_custom_toolbar)
        toolbar.setNavigationIcon(R.drawable.icon_close)
//        toolbar.navigationIcon?.layoutDirection.
        setSupportActionBar(toolbar)
    }

    @Suppress("UseIfInsteadOfWhen")
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            navigator.goToSettingsScreen()
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
