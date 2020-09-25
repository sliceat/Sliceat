package com.marcoperini.sliceat.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.marcoperini.sliceat.R
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_back_button
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_button
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_title
import org.koin.android.ext.android.inject

class SecureInfoScreen : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, SecureInfoScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    private val navigator: Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secure_info_screen)

        clickListener()
    }

    private fun clickListener() {
//        toolbar.toolbar_button.setOnClickListener {
//            navigator.goToSettingsScreen()
//            finish()
//        }
    }
}
