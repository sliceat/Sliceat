package com.marcoperini.sliceat.ui.mail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_back_button
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_title
import org.koin.android.ext.android.inject

class MailsScreen : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var emptyMailIcon: ImageView
    private lateinit var emptyMailIconPlaceholder: TextView
    private lateinit var listElementsMail: MutableList<CardMail>
    private lateinit var recyclerView: RecyclerView

    private val navigator: Navigator by inject()

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, MailsScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mail_screen)

        setupView()
        setupToolbar()
//        clickListener()
    }

    private fun setupView() {
        recyclerView = findViewById(R.id.recyclerView)
        emptyMailIcon = findViewById(R.id.empty_mail)
        emptyMailIconPlaceholder = findViewById(R.id.empty_mail_placeholder)
        listElementsMail = mutableListOf()
//        listElementsMail.add(CardMail(R.drawable.image_mail, R.string.mail_description, R.string.data_mail))
//        listElementsMail.add(CardMail(R.drawable.image_mail, R.string.mail_description, R.string.data_mail))
//        listElementsMail.add(CardMail(R.drawable.image_mail, R.string.mail_description, R.string.data_mail))
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MailsScreen)
            adapter = MailsAdapter(listElementsMail, resources)
        }
        if (listElementsMail.isEmpty()) {
            emptyMailIcon.visibility = View.VISIBLE
            emptyMailIconPlaceholder.visibility = View.VISIBLE
        } else {
            emptyMailIcon.visibility = View.GONE
            emptyMailIconPlaceholder.visibility = View.GONE
        }
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.include_custom_toolbar)
        toolbar.toolbar_title.text = resources.getString(R.string.mail)
        toolbar.toolbar_back_button.setOnClickListener {
            navigator.goToSettingsScreen(this)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigator.goToSettingsScreen(this)
    }
}
