package com.marcoperini.sliceat.ui.authentication.signIn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import org.koin.android.ext.android.inject

class SignInScreen : AppCompatActivity() {

    private val navigator: Navigator by inject()
    private val signInViewModel: SignInViewModel by inject()

    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var email: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_screen)

        setupView()
        sendEvents()
    }

    private fun setupView() {
        firstName = findViewById(R.id.first_name)
        lastName = findViewById(R.id.last_name)
        email = findViewById(R.id.e_mail)
    }

    private fun sendEvents() {
        signInViewModel.send(SignInEvent.Name(
            firstName.text.toString(),
            lastName.text.toString(),
            email.text.toString()))
    }
}
