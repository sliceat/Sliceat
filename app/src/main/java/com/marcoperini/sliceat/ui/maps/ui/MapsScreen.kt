package com.marcoperini.sliceat.ui.maps.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.MenuInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.maps.android.SphericalUtil
import com.google.zxing.integration.android.IntentIntegrator
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import com.marcoperini.sliceat.ui.ScanCustomScreen
import com.marcoperini.sliceat.ui.maps.Location
import com.marcoperini.sliceat.ui.maps.OfflineScreenFragment
import com.marcoperini.sliceat.ui.maps.network.response.LocalsResponse
import com.marcoperini.sliceat.utils.CheckConnection
import com.marcoperini.sliceat.utils.exhaustive
import com.marcoperini.sliceat.utils.getLastLocation
import com.marcoperini.sliceat.utils.latLon
import com.marcoperini.sliceat.utils.searchLocation
import com.marcoperini.sliceat.utils.sharedpreferences.Key
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorage
import com.marcoperini.sliceat.utils.transformImageToRoundImage
import org.koin.android.ext.android.inject
import timber.log.Timber

class MapsScreen : AppCompatActivity(), OnMapReadyCallback, PermissionListener/*, PlaceSelectionListener*/ {

    companion object {
        const val REQUEST_LOCATION_PERMISSION = 1
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, MapsScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    private val prefs: KeyValueStorage by inject()
    private val navigator: Navigator by inject()
    private val mapsViewModel: MapsViewModel by inject()
    private val gson: Gson by inject()

    private lateinit var photo: ImageView
    private lateinit var mapView: View
    private lateinit var qrCode: ImageButton
    private lateinit var popupMenu: ImageButton
    private lateinit var filter: ImageView
    private lateinit var myLocation: ImageView
    private lateinit var googleMap: GoogleMap
    private lateinit var searchView: SearchView
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var location: Location
    private var qrScanIntegrator: IntentIntegrator? = null

    //    private lateinit var autocompleteFragment: AutocompleteSupportFragment

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_screen)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if (!CheckConnection.isOnline(this)) {
            val offlineScreenFragment = OfflineScreenFragment()
            offlineScreenFragment.show(supportFragmentManager, offlineScreenFragment.tag)
            mapsViewModel.send(MapsEvent.SearchLocalsDatabase("San Benedetto Po"))//todo move this in another screen
        }

        setupView()

        setupViewModel()

        mapsViewModel.send(MapsEvent.LoadLocals)
        mapsViewModel.send(MapsEvent.LoadAllergie)

        changeMapTypeView()
        setupListener()
        searchQuery()

//        setupAutocompleteSearch() need the credit card to autocomplete search
    }

    private fun setupView() {
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        location = Location(this)
        mapView = findViewById(R.id.map)
        searchView = findViewById(R.id.search_bar)
        photo = findViewById(R.id.profilePhoto)
        filter = findViewById(R.id.filter_icon)
        myLocation = findViewById(R.id.home_position)
        qrCode = findViewById(R.id.qr_code)
        popupMenu = findViewById(R.id.popup_menu)

        qrScanIntegrator = IntentIntegrator(this)

        // for upload profile image
        val uriPhoto = prefs.getString(Key.SAVE_URI_PHOTO, "")

        if (uriPhoto != "") {
            val uri = Uri.parse(uriPhoto)
            photo.transformImageToRoundImage(uriPhoto)
            photo.setImageURI(uri)
        } else {
            return
        }
    }

    private fun setupViewModel() {
        mapsViewModel.observe(lifecycleScope) {
            when (it) {
                is MapsState.InProgress -> Timber.i("loading restaurants")
                is MapsState.LoadedLocals -> addMarkerToRestaurants(it.restaurants)
                is MapsState.LoadedAllergie -> Timber.i("allergies loaded")
                is MapsState.Error -> showError(it.error)
            }.exhaustive
        }
    }

    private fun addMarkerToRestaurants(restaurants: List<LocalsResponse>) {
        val markerOptions = MarkerOptions()
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.home_pizza))
        if (latLon == null)
            latLon = LatLng(44.506, 11.308/*restaurant.lat.toDouble(), restaurant.lon.toDouble()*/)//defaultLocation first access
        getLastLocation(this@MapsScreen, fusedLocationProviderClient, googleMap)
        restaurants.forEach { restaurant ->
            val restaurantLatLon = LatLng(44.506, 11.308/*restaurant.lat.toDouble(), restaurant.lon.toDouble()*/)
            val distance = SphericalUtil.computeDistanceBetween(latLon, restaurantLatLon)
            if (distance < 10000) {
                markerOptions.position(restaurantLatLon)
                markerOptions.title(restaurant.nome)
                val locationMarker = googleMap.addMarker(markerOptions)
                locationMarker.showInfoWindow()
                googleMap.setOnMarkerClickListener {
                    val infoRestaurant = gson.toJson(restaurant)
                    navigator.goToRestaurantsScreen(this, infoRestaurant)
                    finish()
                    false
                }
            }
        }
    }

    private fun showError(error: Throwable) {
        Timber.w("error in loading restaurants: $error")
        Toast.makeText(this, "$error", Toast.LENGTH_SHORT).show()
    }

    private fun setupListener() {
        photo.setOnClickListener {
            navigator.goToSettingsScreen(this)
            finish()
        }
        filter.setOnClickListener {
            navigator.goToFiltersScreen(this)
            finish()
        }
        qrCode.setOnClickListener { performAction() }
        myLocation.setOnClickListener { getLastLocation(this@MapsScreen, fusedLocationProviderClient, googleMap) }
    }

    private fun searchQuery() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return searchLocation(this@MapsScreen, searchView, googleMap)
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank()) {
                    popupMenu.visibility = View.GONE
                } else {
                    popupMenu.visibility = View.VISIBLE
                }
                return false
            }
        })
    }

    private fun performAction() {
        qrScanIntegrator?.captureActivity = ScanCustomScreen::class.java
        qrScanIntegrator?.initiateScan()
    }

    private fun changeMapTypeView() {
        popupMenu.setOnClickListener {
            val popup = PopupMenu(this, popupMenu)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.map_options, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    // Change the map type based on the user's selection.
                    R.id.normal_map -> googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                    R.id.hybrid_map -> googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                    R.id.satellite_map -> googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                    R.id.terrain_map -> googleMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                }
                true
            }
            popup.show()
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map ?: return
        if (isPermissionGiven()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            googleMap.isMyLocationEnabled = true
            googleMap.uiSettings.isMyLocationButtonEnabled = false
            googleMap.uiSettings.isZoomControlsEnabled = false
            getLocation(location)
            location.setMapLongClick(googleMap)
            location.setPoiClick(googleMap)
        } else {
            givePermission()
        }
    }

    private fun isPermissionGiven(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun givePermission() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(this)
            .check()
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        getLocation(location)
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {
        token!!.continuePermissionRequest()
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "Permission required for showing location", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents != null) {
                // If QRCode contains data.
                if (Patterns.WEB_URL.matcher(result.contents).matches()) {
                    // Open URL
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(result.contents))
                    startActivity(browserIntent)
                } else {
                    // Show values in UI.
                    Toast.makeText(this, result.contents + " " + getString(R.string.result_not_found), Toast.LENGTH_LONG).show()
                }
                // Data not in the expected format. So, whole object as toast message.
            } else {
                Toast.makeText(this, getString(R.string.result_not_found), Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun getLocation(location: Location) {
        location.getCurrentLocation(object : Location.LocationService {
            override fun getLastLocationInterface() {
                getLastLocation(this@MapsScreen, fusedLocationProviderClient, googleMap)
            }
        })
    }
}
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//
//        when (requestCode) {
//            REQUEST_LOCATION_PERMISSION -> {
//                if (resultCode == Activity.RESULT_OK) {
//                    getLocation(location)
//                }
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }

//    private fun setupAutocompleteSearch() {
//        if (!Places.isInitialized()) {
//            Places.initialize(applicationContext,"AIzaSyAerZEakQJJI2afAIUQg9TaP_cHL3TkHy4")
//            Places.createClient(this)
//        }
//
//        autocompleteFragment = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
//
//        autocompleteFragment.setTypeFilter(TypeFilter.ESTABLISHMENT)
//        autocompleteFragment.setLocationBias(RectangularBounds.newInstance(LatLng(-33.880490, 151.184363),LatLng(-33.858754, 151.229596)))
//        autocompleteFragment.setCountries("IT")
//        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))
//
//        // Specify the types of place data to return.
//        autocompleteFragment.setOnPlaceSelectedListener(this)
//    }

//    @SuppressLint("BinaryOperationInTimber")
//    override fun onPlaceSelected(place: Place) {
//        Timber.i("Place Selected: %s", place.name + " " + place.id)
//        // Format the returned place's details and display them in the TextView.
//    }
//
//    override fun onError(status: Status) {
//        Timber.e("onError: Status = %s", status.toString())
//
//        Toast.makeText(this, "Place selection failed: " + status.statusMessage, Toast.LENGTH_SHORT).show();
//    }
