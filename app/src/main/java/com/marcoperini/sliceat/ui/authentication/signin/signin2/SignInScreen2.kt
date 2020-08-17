package com.marcoperini.sliceat.ui.authentication.signin.signin2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import com.marcoperini.sliceat.ui.authentication.signin.signin1.DELAY_HIDE_ERROR
import com.marcoperini.sliceat.utils.exhaustive
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

class SignInScreen2 : AppCompatActivity() {

    private val navigator: Navigator by inject()
    private val signIn2ViewModel: SignIn2ViewModel by inject()

    private lateinit var insertEmail: EditText
    private lateinit var backButton: Button
    private lateinit var continua: Button
    private lateinit var wrongEmail: TextView
    private lateinit var emptyEmail: TextView

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, SignInScreen2::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    @ExperimentalCoroutinesApi
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
        wrongEmail = findViewById(R.id.wrong_email)
        emptyEmail = findViewById(R.id.empty_email)
    }

    private fun setOnClickListener() {
        backButton.setOnClickListener {
            navigator.goToSignInScreen1()
        }
        continua.setOnClickListener {
            validateInputData()
        }
    }

    @ExperimentalCoroutinesApi
    private fun observer() {
        signIn2ViewModel.observe(lifecycleScope) { state ->
            when (state) {
                is SignIn2State.SavedEmail -> navigator.goToSignInScreen3()
            }.exhaustive
        }
    }

    private fun validateInputData() {
        val email = insertEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            emptyEmail.visibility = View.VISIBLE
            Handler().postDelayed({ emptyEmail.visibility = View.GONE }, DELAY_HIDE_ERROR)
        } else if (!email.contains("@")) {
            wrongEmail.visibility = View.VISIBLE
            Handler().postDelayed({ wrongEmail.visibility = View.GONE }, DELAY_HIDE_ERROR)
        } else {
            signIn2ViewModel.send(SignIn2Event.SaveEmail(email))
        }
    }
}
