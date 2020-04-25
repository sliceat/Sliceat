package com.marcoperini.sliceat.ui.authentication.signin.signin1

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
import com.marcoperini.sliceat.database.UsersTable
import com.marcoperini.sliceat.ui.Navigator
import com.marcoperini.sliceat.utils.exhaustive
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

const val DELAY_HIDE_ERROR = 2000L
class SignInScreen1 : AppCompatActivity() {

    private val navigator: Navigator by inject()
    private val signIn1ViewModel: SignInViewModel by inject()

    private lateinit var insertFirstName: EditText
    private lateinit var insertLastName: EditText
    private lateinit var user: UsersTable
    private lateinit var backButton: Button
    private lateinit var continua: Button
    private lateinit var nameEmpty: TextView
    private lateinit var lastNameEmpty: TextView

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, SignInScreen1::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_screen1)

        setupView()
        setOnClickListener()
        observer()
    }

    private fun setupView() {
        insertFirstName = findViewById(R.id.insertName)
        insertLastName = findViewById(R.id.insertLastName)
        backButton = findViewById(R.id.backButton)
        continua = findViewById(R.id.continua)
        nameEmpty = findViewById(R.id.name_empty)
        lastNameEmpty = findViewById(R.id.last_name_empty)
    }

    private fun setOnClickListener() {
        backButton.setOnClickListener {
            navigator.goToAuthenticationScreen()
        }
        continua.setOnClickListener {
//            saveUser()
            validateInputData()
        }
    }

    private fun saveUser() {
        user = UsersTable(
            insertFirstName.text.toString(),
            insertLastName.text.toString(),
            "prova@gmail.com",
            "noPass",
            "31/08/1985",
            "CL",
            "noPhoto"
        )
        validateInputData()

    }

    @ExperimentalCoroutinesApi
    private fun observer() {
        signIn1ViewModel.observe(lifecycleScope) { state ->
            when (state) {
//                is SignIn1State.SaveUser -> navigator.goToSignInScreen2()
                is SignIn1State.SavedFirstAndLastName -> navigator.goToSignInScreen2()
            }.exhaustive

        }
    }

    private fun validateInputData() {
        val name = insertFirstName.text.toString()
        if (TextUtils.isEmpty(name)){
            nameEmpty.visibility = View.VISIBLE
            Handler().postDelayed({ nameEmpty.visibility = View.GONE }, DELAY_HIDE_ERROR)
        }


        else {
            val lastName = insertLastName.text.toString()
            if (TextUtils.isEmpty(lastName)) {
                lastNameEmpty.visibility = View.VISIBLE
                Handler().postDelayed({ lastNameEmpty.visibility = View.GONE }, DELAY_HIDE_ERROR)
            } else {
                signIn1ViewModel.send(SignIn1Event.SaveFirstAndLastName(name, lastName))
//                signIn1ViewModel.send(SignIn1Event.SaveUser(user))
            }
        }
    }
}