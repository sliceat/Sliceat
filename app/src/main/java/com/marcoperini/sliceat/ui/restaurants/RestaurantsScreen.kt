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
    private lateinit var photosRecyclerView: RecyclerView
    private lateinit var photosAdapter: RestaurantsAdapter

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
        photosRecyclerView = findViewById(R.id.restaurants_photos)
        photosAdapter = RestaurantsAdapter(resources)

        photosRecyclerView.adapter = photosAdapter

        listPhotos = mutableListOf()

        listPhotos.add(CardRestaurantsPhoto("1", R.drawable.photo_restaurants))
        listPhotos.add(CardRestaurantsPhoto("2", R.drawable.photo_restaurants))
        listPhotos.add(CardRestaurantsPhoto("3", R.drawable.photo_restaurants))

        photosAdapter.submitList(listPhotos)

    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.include_custom_toolbar)
        toolbar.toolbar_title.text = resources.getString(R.string.empty)
    }
}
