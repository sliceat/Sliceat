package com.marcoperini.sliceat.ui.restaurants

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.filters.CardFilter
import com.marcoperini.sliceat.ui.filters.FiltersAdapter
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_title

class RestaurantsScreen : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var listPhotos: MutableList<CardRestaurantsPhoto>
    private lateinit var photosRecyclerView: RecyclerView
    private lateinit var photosAdapter: RestaurantsAdapter
    private lateinit var filterRecyclerView: RecyclerView
    private lateinit var filtersAdapterRestaurant: FiltersAdapterRestaurant
    private lateinit var listElementFilter: MutableList<CardFilter>

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, RestaurantsScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurants_screen)

        setupToolbar()
        setupRvPhoto()
        setupRvFilter()
    }

    private fun setupRvPhoto() {
        photosRecyclerView = findViewById(R.id.restaurants_photos)
        photosAdapter = RestaurantsAdapter(resources)

        photosRecyclerView.adapter = photosAdapter

        listPhotos = mutableListOf()

        listPhotos.add(CardRestaurantsPhoto("1", R.drawable.photo_restaurants))
        listPhotos.add(CardRestaurantsPhoto("2", R.drawable.photo_restaurants))
        listPhotos.add(CardRestaurantsPhoto("3", R.drawable.photo_restaurants))

        photosAdapter.submitList(listPhotos)
    }

    private fun setupRvFilter() {
        listElementFilter = mutableListOf()
        filterRecyclerView = findViewById(R.id.recyclerViewFilter)

        listElementFilter.add(CardFilter(R.drawable.solfiti, R.string.solfiti))
        listElementFilter.add(CardFilter(R.drawable.icon_disabili, R.string.disabili))
        listElementFilter.add(CardFilter(R.drawable.icon_vini, R.string.carta_dei_vini))

        filterRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@RestaurantsScreen)
            adapter = FiltersAdapter(listElementFilter, resources)
        }
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.include_custom_toolbar)
        toolbar.toolbar_title.text = resources.getString(R.string.empty)
    }
}
