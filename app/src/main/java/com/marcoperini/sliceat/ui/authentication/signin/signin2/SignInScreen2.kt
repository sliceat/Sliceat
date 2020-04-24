package com.marcoperini.sliceat.ui.authentication.signin.signin2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import com.marcoperini.sliceat.ui.authentication.signin.signin1.SignIn2ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

class SignInScreen2 : AppCompatActivity() {

    private val navigator: Navigator by inject()
    private val signIn2ViewModel: SignIn2ViewModel by inject()

    private lateinit var insertEmail: EditText
    private lateinit var backButton: Button
    private lateinit var continua: Button

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
        continua = findViewById(R.id.continua)
    }

    private fun setOnClickListener() {
        backButton.setOnClickListener {
            navigator.goToSignInScreen1()
        }
        continua.setOnClickListener {
            navigator.goToSignInScreen3()
        }
    }

    @ExperimentalCoroutinesApi
    private fun observer() {
//        signIn2ViewModel.observe(lifecycleScope) { state ->
//            when (state) {
//
//            }.exhaustive
//        }
    }
}
