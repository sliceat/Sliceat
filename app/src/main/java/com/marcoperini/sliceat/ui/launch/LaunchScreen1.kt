package com.marcoperini.sliceat.ui.launch

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import com.marcoperini.sliceat.utils.Constants.Companion.DELAY_START_SCREEN
import org.koin.android.ext.android.inject

class LaunchScreen1 : AppCompatActivity() {

    private val navigator: Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launch_screen1)

        goToLaunchScreen2()
    }

    private fun goToLaunchScreen2() {
        val goToLaunchScreen2 = Runnable {
            navigator.goToLaunchScreen2()
            finish()
        }
        Handler().postDelayed(goToLaunchScreen2, DELAY_START_SCREEN.toLong())
    }
}
