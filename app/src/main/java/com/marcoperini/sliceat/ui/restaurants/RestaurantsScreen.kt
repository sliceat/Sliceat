package com.marcoperini.sliceat.ui.restaurants

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import com.marcoperini.sliceat.ui.filters.CardFilter
import com.marcoperini.sliceat.ui.maps.network.response.LocalsResponse
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_back_button
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_button
import kotlinx.android.synthetic.main.toolbar_with_indicator.view.toolbar_title
import org.koin.android.ext.android.inject

const val INFO_RESTAURANT = "info restaurant"
class RestaurantsScreen : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var listPhotos: MutableList<CardRestaurantsPhoto>
    private lateinit var photosRecyclerView: RecyclerView
    private lateinit var photosAdapter: RestaurantsAdapter
    private lateinit var filterRecyclerView: RecyclerView
    private lateinit var listElementFilter: MutableList<CardFilter>
    private lateinit var infoRestaurantResponse : LocalsResponse
    private lateinit var telephoneNumber : TextView
    private lateinit var nameRestaurant : TextView
    private lateinit var address : TextView

    private val navigator: Navigator by inject()
    private val gson: Gson by inject()

    companion object {
        fun getIntent(startingActivityContext: Context, infoRestaurant: String) = Intent(startingActivityContext, RestaurantsScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .putExtra(INFO_RESTAURANT, infoRestaurant)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.restaurant_screen)

        val infoRestaurant = intent.getStringExtra(INFO_RESTAURANT)
        infoRestaurantResponse = gson.fromJson(infoRestaurant, LocalsResponse::class.java)

        setupView()
        setupToolbar()
        setupRvPhoto()
        setupRvFilter()
        setupListener()
    }

    private fun setupView() {
        nameRestaurant = findViewById(R.id.name_restaurant)
        nameRestaurant.text = infoRestaurantResponse.nome
        telephoneNumber = findViewById(R.id.telephone_number)
        telephoneNumber.text = infoRestaurantResponse.telefono
        address = findViewById(R.id.address)
        address.text = ("${infoRestaurantResponse.via} ${infoRestaurantResponse.civico}, ${infoRestaurantResponse.citta}")
    }

    private fun setupListener() {
        toolbar.toolbar_button.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "market://details?id=$packageName")
            startActivity(Intent.createChooser(intent, resources.getString(R.string.invite_your_friends)), null)
        }
        toolbar.toolbar_back_button.setOnClickListener {
            navigator.goToMapsScreen(this)
            finish()
        }
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
            adapter = FiltersAdapterRestaurant(listElementFilter, resources)
        }
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.include_custom_toolbar)
        toolbar.toolbar_button.visibility = View.VISIBLE
        toolbar.toolbar_button.setImageDrawable(resources.getDrawable(R.drawable.icon_share, null))
        toolbar.toolbar_title.text = resources.getString(R.string.empty)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigator.goToMapsScreen(this)
    }
}
