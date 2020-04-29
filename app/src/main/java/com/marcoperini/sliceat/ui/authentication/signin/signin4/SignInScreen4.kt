package com.marcoperini.sliceat.ui.authentication.signin.signin4

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
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

class SignInScreen4 : AppCompatActivity() {
    private val navigator: Navigator by inject()
    private val prefs: KeyValueStorage by inject()
    private val signIn4ViewModel: SignIn4ViewModel by inject()

    private lateinit var insertData: EditText
    private lateinit var backButton: Button
    private lateinit var continua: Button
    private lateinit var emptyData: TextView

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, SignInScreen4::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_screen4)

        EditTextDatePicker(this, R.id.insertData, prefs)

        setupView()
        setOnClickListener()
        observer()
    }

    private fun setupView() {
        insertData = findViewById(R.id.insertData)
        backButton = findViewById(R.id.backButton)
        continua = findViewById(R.id.continua)
        emptyData = findViewById(R.id.empty_data)
    }

    private fun setOnClickListener() {
        backButton.setOnClickListener {
            navigator.goToSignInScreen3()
        }
        continua.setOnClickListener {
            validateInputData()
        }
    }

    @ExperimentalCoroutinesApi
    private fun observer() {
        signIn4ViewModel.observe(lifecycleScope) { state ->
            when (state) {
                SignIn4State.SavedData -> navigator.goToSignInScreen5()
            }.exhaustive
        }
    }

    private fun validateInputData() {
        val data = insertData.text.toString()
        if (TextUtils.isEmpty(data)) {
            emptyData.visibility = View.VISIBLE
            Handler().postDelayed({ emptyData.visibility = View.GONE }, DELAY_HIDE_ERROR)
        } else {
            signIn4ViewModel.send(SignIn4Event.Data(data))
        }
    }
}
