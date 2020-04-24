package com.marcoperini.sliceat.ui.authentication.signin.signin1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.database.UsersTable
import com.marcoperini.sliceat.ui.Navigator
import com.marcoperini.sliceat.utils.exhaustive
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

class SignInScreen1 : AppCompatActivity() {

    private val navigator: Navigator by inject()
    private val signIn1ViewModel: SignInViewModel by inject()

    private lateinit var inserFirstName: EditText
    private lateinit var insertLastName: EditText
    private lateinit var user: UsersTable
    private lateinit var backButton: Button
    private lateinit var continua: Button

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, SignInScreen1::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_screen1)

        setupView()
        saveUser()
        setOnClickListener()
        observer()
    }

    private fun setupView() {
        inserFirstName = findViewById(R.id.insertName)
        insertLastName = findViewById(R.id.insertLastName)
        backButton = findViewById(R.id.backButton)
        continua = findViewById(R.id.continua)
    }

    private fun saveUser() {
        user = UsersTable(
            inserFirstName.text.toString(),
            insertLastName.text.toString(),
            "prova@gmail.com"
        )
        signIn1ViewModel.send(SignIn1Event.Name(user))
    }

    private fun setOnClickListener() {
        backButton.setOnClickListener {
            navigator.goToAuthenticationScreen()
        }
        continua.setOnClickListener {
            navigator.goToSignInScreen2()
        }
    }

    @ExperimentalCoroutinesApi
    private fun observer() {
        signIn1ViewModel.observe(lifecycleScope) { state ->
            when (state) {
                is SignIn1State.CheckUserField -> validateInputData()
                SignIn1State.SaveUser -> TODO()
            }.exhaustive

        }
    }

    private fun validateInputData() {

//        if (TextUtils.isEmpty(firstName.text.toString())) {
//            checkName.text = "error"
//        } else {
//            checkName.text = "correct"
//            inputxt_notes.error = resources.getString(R.string.error_notes)
//            uiHelper.showSnackBar(rootView_add_birds, resources.getString(R.string.error_notes))
    }
}
