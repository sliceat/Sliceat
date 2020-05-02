package com.marcoperini.sliceat.ui.authentication.access

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import org.koin.android.ext.android.inject

class AccessScreen : AppCompatActivity() {

    private lateinit var backButton: Button
    private lateinit var email: EditText
    private lateinit var password: EditText

    private val navigator: Navigator by inject()

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, AccessScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.access_screen)

        setupView()
        setOnClickListener()
    }

    private fun setupView() {
        email = findViewById(R.id.insertEmail)
        backButton = findViewById(R.id.backButton)
        password = findViewById(R.id.insertPassword)
    }

    private fun setOnClickListener() {
        backButton.setOnClickListener {
            navigator.goToAuthenticationScreen()
        }
    }
}
