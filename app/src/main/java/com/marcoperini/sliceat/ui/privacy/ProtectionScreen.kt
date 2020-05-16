package com.marcoperini.sliceat.ui.privacy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.authentication.access.AccessScreen

class ProtectionScreen : AppCompatActivity() {

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, ProtectionScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.protection_screen)

    }
}
