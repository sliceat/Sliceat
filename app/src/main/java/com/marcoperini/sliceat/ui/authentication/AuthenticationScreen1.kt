package com.marcoperini.sliceat.ui.authentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.main.MainViewModel
import org.koin.android.ext.android.inject

class AuthenticationScreen1 : AppCompatActivity() {

    private val mainViewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authentication_screen1)
    }
}
