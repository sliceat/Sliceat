package com.marcoperini.sliceat.ui.authentication.firstscreen

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import org.koin.android.ext.android.inject

class FirstScreen : AppCompatActivity() {

//    private val authenticationViewModel: AuthenticationViewModel by inject()
    private val navigator: Navigator by inject()

    private lateinit var access: Button
    private lateinit var signIn: Button
    private lateinit var goToMap: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authentication_screen)

        setupView()
        clickListner()
    }

    private fun setupView() {
        access = findViewById(R.id.access)
        signIn = findViewById(R.id.signIn)
        goToMap = findViewById(R.id.goToMap)
    }

    private fun clickListner() {
        access.setOnClickListener { navigator.goToAccessScreen() }
        signIn.setOnClickListener { navigator.goToSignInScreen1() }
        goToMap.setOnClickListener { navigator.goToMapsScreen() }
    }
}
