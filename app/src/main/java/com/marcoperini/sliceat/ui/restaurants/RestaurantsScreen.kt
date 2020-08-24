package com.marcoperini.sliceat.ui.restaurants

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.mail.CardMail
import com.marcoperini.sliceat.ui.mail.MailsAdapter
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_title

class RestaurantsScreen : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var listPhotos: MutableList<CardRestaurantsPhoto>
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, RestaurantsScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurants_screen)

        setupView()
        setupToolbar()
//        clickListener()
    }

    private fun setupView() {
        recyclerView = findViewById(R.id.restaurants_photos)
        listPhotos = mutableListOf()
        listPhotos.add(CardRestaurantsPhoto(R.drawable.photo_restaurants))
        listPhotos.add(CardRestaurantsPhoto(R.drawable.photo_restaurants))
        listPhotos.add(CardRestaurantsPhoto(R.drawable.photo_restaurants))
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@RestaurantsScreen)
            adapter = RestaurantsAdapter(listPhotos, resources)
        }
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.include_custom_toolbar)
        toolbar.toolbar_title.text = resources.getString(R.string.empty)
    }
}
