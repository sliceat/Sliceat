package com.marcoperini.sliceat.ui.authentication.signin.signin5

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import com.marcoperini.sliceat.ui.authentication.signin.signin4.SignInScreen4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

class SignInScreen5 : AppCompatActivity() {

    private val navigator: Navigator by inject()
    private val signIn5ViewModel: SignIn5ViewModel by inject()

    private lateinit var backButton: Button
    private lateinit var takeAPhoto: Button

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, SignInScreen4::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_screen5)

        setupView()
        setOnClickListener()
        observer()
    }

    private fun setupView() {
        backButton = findViewById(R.id.backButton)
        takeAPhoto = findViewById(R.id.continua)
    }

    private fun setOnClickListener() {
        backButton.setOnClickListener {
            navigator.goToSignInScreen4()
        }
        takeAPhoto.setOnClickListener {
//            navigator.goToSignInScreen4()
        }
    }

    @ExperimentalCoroutinesApi
    private fun observer() {
//        signIn3ViewModel.observe(lifecycleScope) { state ->
//            when (state) {
//
//            }.exhaustive
//        }
    }
}
