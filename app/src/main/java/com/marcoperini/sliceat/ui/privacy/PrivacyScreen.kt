package com.marcoperini.sliceat.ui.privacy

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import org.koin.android.ext.android.inject

sealed class Links(val link: String) {
    object Privacy : Links("https://www.sliceat.it/privacy.php")
}

class PrivacyScreen : AppCompatActivity() {

    private lateinit var readMore: TextView
    private lateinit var readMoreText: SpannableString
    private lateinit var accessButton: Button

    private var navigator : Navigator by inject()
    private val privacyLink = Links.Privacy.link

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, PrivacyScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.privacy_screen)

        setupView()
    }

    private fun setupView() {

        readMore = findViewById(R.id.readMore)
        readMoreText = SpannableString(readMore.text)
        accessButton = findViewById(R.id.goOn)

        readMoreText.setSpan(object : URLSpan(privacyLink) {
            @RequiresApi(Build.VERSION_CODES.Q)
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.bgColor = 0
                ds.color = resources.getColor(R.color.black)
                ds.underlineColor = resources.getColor(R.color.black)
            }
        }, 0, readMore.text.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

        readMore.movementMethod = LinkMovementMethod.getInstance()
        readMore.text = readMoreText

        accessButton.setOnClickListener {
            navigator.goToProtectionScreen()
        }

    }
}
