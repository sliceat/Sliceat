package com.marcoperini.sliceat.ui.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.hellmund.viewpager2indicator.ViewPager2Indicator
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import kotlinx.android.synthetic.main.onboarding_container.view_pager2
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_back_button
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_title
import org.koin.android.ext.android.inject

class SettingsScreen : AppCompatActivity() {
    private val navigator: Navigator by inject()
//    private val prefs: KeyValueStorage by inject()

    private lateinit var pageIndicator: ViewPager2Indicator
    private lateinit var shareSliceat: ImageView
    private lateinit var termsAndCondition: ImageView
    private lateinit var secureInformations: ImageView
    private lateinit var privacyPolicy: ImageView
    private lateinit var mail: ImageView
//    private lateinit var toolbarBack: ImageView

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, SettingsScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_screen)

        view_pager2.adapter = SettingsAdapter(this, navigator, this@SettingsScreen)

        setupToolbar()
        setupView()
        setupListener()

        view_pager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (view_pager2.currentItem == 2) {

                } else {

                }
            }
        })
    }

    private fun setupView() {
        pageIndicator = findViewById(R.id.indicator)
        pageIndicator.attachTo(view_pager2)
        mail = findViewById(R.id.mail_icon)
        secureInformations = findViewById(R.id.icon_secure_informations)
        shareSliceat = findViewById(R.id.share_sliceat_icon)
        termsAndCondition = findViewById(R.id.term_and_condition_icon)
        privacyPolicy = findViewById(R.id.privacy_policy_icon)
    }

    private fun setupToolbar() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.include_custom_toolbar)
        toolbar.toolbar_title.text = resources.getString(R.string.setting)

        toolbar.toolbar_back_button.setOnClickListener {
            navigator.goToMapsScreen()
            finish()
        }
    }

    private fun setupListener() {
        mail.setOnClickListener {
            navigator.goToMailScreen()
            finish()
        }

        secureInformations.setOnClickListener {
            navigator.goToSecureInfoScreen()
            finish()
        }

        shareSliceat.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "market://details?id=$packageName")
            startActivity(Intent.createChooser(intent, resources.getString(R.string.invite_your_friends)), null)
        }

        termsAndCondition.setOnClickListener {
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(resources.getString(R.string.terms_link_it)))
            startActivity(intent)
        }

        privacyPolicy.setOnClickListener {
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(resources.getString(R.string.privacy_link_it)))
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigator.goToMapsScreen()
    }
}
