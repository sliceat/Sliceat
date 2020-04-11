package com.marcoperini.sliceat.ui.launch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.utils.exhaustive
import org.koin.android.ext.android.inject

class LaunchScreen : AppCompatActivity() {

    private val launchViewModel: LaunchViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launch_screen)

        loading()
    }

    private fun loading() {
        launchViewModel.observe(lifecycleScope) {LaunchState ->
            when(LaunchState) {
                com.marcoperini.sliceat.ui.launch.LaunchState.NewUser -> goToOnBoarding()
                com.marcoperini.sliceat.ui.launch.LaunchState.OldUser -> goToMainScreen()
            }.exhaustive
        }
    }

    private fun goToMainScreen() {

    }

    private fun goToOnBoarding() {

    }
}


