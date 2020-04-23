package com.marcoperini.sliceat.ui.authentication.signIn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

class SignInScreen2 : AppCompatActivity() {

    private val navigator: Navigator by inject()
    private val signInViewModel2: SignInViewModel by inject()

    private lateinit var insertEmail: EditText
    private lateinit var backButton: Button
    private lateinit var continua2: Button

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, SignInScreen2::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_screen2)

        setupView()
        setOnClickListener()
        observer()
    }

    private fun setupView() {
        insertEmail = findViewById(R.id.insertEmail)
        backButton = findViewById(R.id.backButton)
        continua2 = findViewById(R.id.continua2)
    }

    private fun setOnClickListener() {
        backButton.setOnClickListener {
            navigator.goToSignInScreen1()
        }
        continua2.setOnClickListener {
//            navigator.goToSignInScreen3()
        }
    }

    @ExperimentalCoroutinesApi
    private fun observer() {
        signInViewModel2.observe(lifecycleScope) { state ->
//            when (state) {
//
//            }.exhaustive

        }
    }
}
