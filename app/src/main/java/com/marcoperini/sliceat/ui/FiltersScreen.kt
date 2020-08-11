package com.marcoperini.sliceat.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marcoperini.sliceat.R

class FiltersScreen : AppCompatActivity() {

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, FiltersScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filters_screen)
    }
}
