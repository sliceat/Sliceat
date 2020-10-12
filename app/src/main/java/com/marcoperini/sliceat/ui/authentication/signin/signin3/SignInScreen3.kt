package com.marcoperini.sliceat.ui.authentication.signin.signin3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import com.marcoperini.sliceat.ui.authentication.signin.signin1.DELAY_HIDE_ERROR
import com.marcoperini.sliceat.utils.Constants.Companion.PASSWORD_PATTERN
import com.marcoperini.sliceat.utils.exhaustive
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignInScreen3 : AppCompatActivity() {

    private val navigator: Navigator by inject()
    private val signIn3ViewModel: SignIn3ViewModel by inject()

    private lateinit var insertPassword: EditText
    private lateinit var repeatPassword: EditText
    private lateinit var backButton: Button
    private lateinit var continua: Button
    private lateinit var specialCharacter: TextView
    private lateinit var samePassword: TextView

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, SignInScreen3::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_screen3)

        setupView()
        setOnClickListener()
        observer()
    }

    private fun setupView() {
        insertPassword = findViewById(R.id.insertPassword)
        repeatPassword = findViewById(R.id.repeatPassword)
        backButton = findViewById(R.id.backButton)
        continua = findViewById(R.id.continua)
        specialCharacter = findViewById(R.id.spacial_character)
        samePassword = findViewById(R.id.same_password)
    }

    private fun setOnClickListener() {
        backButton.setOnClickListener {
            navigator.goToSignInScreen2(this)
        }
        continua.setOnClickListener {
            validateInputData()
        }
    }

    private fun observer() {
        signIn3ViewModel.observe(lifecycleScope) { state ->
            when (state) {
                is SignIn3State.SavedPassword -> navigator.goToSignInScreen4(this)
            }.exhaustive
        }
    }

    private fun validateInputData() {
        val password = insertPassword.text.toString()
        Timber.d("password %s", password.length)
        if (password.length < 8 || !isValidPassword(password)) {
            specialCharacter.visibility = View.VISIBLE
            Handler().postDelayed({ specialCharacter.visibility = View.GONE }, DELAY_HIDE_ERROR)
        } else {
            val repeatPassword = repeatPassword.text.toString()
            if (password != repeatPassword) {
                samePassword.visibility = View.VISIBLE
                Handler().postDelayed({ samePassword.visibility = View.GONE }, DELAY_HIDE_ERROR)
            } else {
                signIn3ViewModel.send(SignIn3Event.SavePassword(password))
            }
        }
    }

    private fun isValidPassword(password: String?): Boolean {
        val matcher: Matcher
        val pattern: Pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password.toString())
        return matcher.matches()
    }
}
