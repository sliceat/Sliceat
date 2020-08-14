package com.marcoperini.sliceat.ui.mail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcoperini.sliceat.R
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_title

class MailsScreen : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var listElementsMail: MutableList<CardMail>
    private lateinit var recyclerView: RecyclerView

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
        listElementsMail = mutableListOf()
        listElementsMail.add(CardMail(R.drawable.image_mail, R.string.mail_description))
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MailsScreen)
            adapter = MailsAdapter(listElementsMail, resources)
        }
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.include_custom_toolbar)
        toolbar.toolbar_title.text = resources.getString(R.string.mail)
    }
}
