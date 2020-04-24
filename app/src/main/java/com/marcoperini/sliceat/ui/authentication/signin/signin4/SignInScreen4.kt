package com.marcoperini.sliceat.ui.authentication.signin.signin4

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

class SignInScreen4 : AppCompatActivity() {
    private val navigator: Navigator by inject()
    private val signIn4ViewModel: SignIn4ViewModel by inject()

    private lateinit var insertData: EditText
    private lateinit var backButton: Button
    private lateinit var continua: Button

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, SignInScreen4::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_screen4)

        setupView()
        setOnClickListener()
        observer()
    }

    private fun setupView() {
        insertData = findViewById(R.id.insertData)
        backButton = findViewById(R.id.backButton)
        continua = findViewById(R.id.continua)
    }

    private fun setOnClickListener() {
        backButton.setOnClickListener {
            navigator.goToSignInScreen3()
        }
        continua.setOnClickListener {
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
