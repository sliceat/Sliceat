package com.marcoperini.sliceat.ui.authentication.signIn

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.TextUtils
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

class SignInScreen : AppCompatActivity() {

    private val navigator: Navigator by inject()
    private val signInViewModel: SignInViewModel by inject()

    private lateinit var firstName: EditText
    private lateinit var checkName: TextView
    private lateinit var lastName: EditText
    private lateinit var email: EditText
    private lateinit var user: UsersTable

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, SignInScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_screen)

        setupView()
        saveUser()
        observer()
    }

    private fun setupView() {
        firstName = findViewById(R.id.first_name)
        lastName = findViewById(R.id.last_name)
        email = findViewById(R.id.e_mail)
        checkName = findViewById(R.id.check_name)
    }

    private fun saveUser() {
        user = UsersTable(
            firstName.text.toString(),
            lastName.text.toString(),
            email.text.toString()
        )
        signInViewModel.send(SignInEvent.Name(user))
    }

    @ExperimentalCoroutinesApi
    private fun observer() {
        signInViewModel.observe(lifecycleScope) { state ->
            when (state) {
                is SignInState.CheckUserField -> validateInputData()
                SignInState.SaveUser -> TODO()
            }.exhaustive

        }
    }

    private fun validateInputData() {

        if (TextUtils.isEmpty(firstName.text.toString())) {
            checkName.text = "error"
        } else {
            checkName.text = "correct"
//            inputxt_notes.error = resources.getString(R.string.error_notes)
//            uiHelper.showSnackBar(rootView_add_birds, resources.getString(R.string.error_notes))
        }
    }
}
