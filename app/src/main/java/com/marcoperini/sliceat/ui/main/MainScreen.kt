package com.marcoperini.sliceat.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marcoperini.sliceat.R
import org.koin.android.ext.android.inject

class MainScreen : AppCompatActivity() {

    private val mainViewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
