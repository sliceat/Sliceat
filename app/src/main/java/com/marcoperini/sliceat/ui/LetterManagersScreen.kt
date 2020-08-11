package com.marcoperini.sliceat.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.marcoperini.sliceat.R
import kotlinx.android.synthetic.main.activity_filters_screen.view.include_custom_toolbar
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_back_button
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_close_button
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_title
import org.koin.android.ext.android.inject

class LetterManagersScreen : AppCompatActivity() {

    private lateinit var toolbar :Toolbar

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
        toolbar.toolbar_close_button.visibility = View.VISIBLE
    }
    
    private fun setupToolbar() {
        toolbar = findViewById(R.id.include_custom_toolbar)
        toolbar.toolbar_back_button.visibility = View.INVISIBLE
        toolbar.toolbar_close_button.visibility = View.VISIBLE
        toolbar.toolbar_close_button.setImageDrawable(resources.getDrawable(R.drawable.icon_close, null))
        toolbar.toolbar_title.text = resources.getString(R.string.empty)
    }

    private fun clickListener() {
        toolbar.toolbar_close_button.setOnClickListener {
            navigator.goToSettingsScreen()
            finish()
        }
    }
}
