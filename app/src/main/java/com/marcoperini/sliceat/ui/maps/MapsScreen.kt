package com.marcoperini.sliceat.ui.maps

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import com.marcoperini.sliceat.utils.Constants.Companion.ZOOM_CAMERA
import com.marcoperini.sliceat.utils.sharedpreferences.Key
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorage
import com.marcoperini.sliceat.utils.transformImageToRoundImage
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.io.IOException
import java.util.*

class MapsScreen : AppCompatActivity(), OnMapReadyCallback, PermissionListener,PlaceSelectionListener {

    companion object {
        const val AUTOCOMPLETE_REQUEST_CODE = 1
        const val REQUEST_LOCATION_PERMISSION = 1
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, MapsScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    private val prefs: KeyValueStorage by inject()
    private val navigator: Navigator by inject()

    private lateinit var photo: ImageView
    private lateinit var filter: ImageView
    private lateinit var googleMap: GoogleMap
    private lateinit var searchView: SearchView
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var location: Location
    private lateinit var autocompleteFragment: AutocompleteSupportFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_screen)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setupView()

        clickListener()

        searchQuery()

//        setupAutocompleteSearch() need the credit card to autocomplete search
    }

    private fun searchQuery() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                val location = searchView.query.toString()

                if (location == "") {
                    return false
                }
                val geocoder = Geocoder(this@MapsScreen)

                try {
                    val addressList = geocoder.getFromLocationName(location, 1)
                    if (addressList.size == 0) {
                        Toast.makeText(this@MapsScreen, "adresse not found", Toast.LENGTH_LONG).show()
                    }

                    else if (addressList.size > 0) {
                        val address = addressList[0]
                        val latLng = LatLng(address.latitude, address.longitude)
                        googleMap.addMarker(latLng.let { MarkerOptions().position(it).title(location) })
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10F))
                    }

                } catch (e: IOException) {
                    e.printStackTrace()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setupAutocompleteSearch() {
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext,"AIzaSyAerZEakQJJI2afAIUQg9TaP_cHL3TkHy4")
            Places.createClient(this)
        }

        autocompleteFragment = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

        autocompleteFragment.setTypeFilter(TypeFilter.ESTABLISHMENT)
        autocompleteFragment.setLocationBias(RectangularBounds.newInstance(LatLng(-33.880490, 151.184363),LatLng(-33.858754, 151.229596)))
        autocompleteFragment.setCountries("IT")
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        // Specify the types of place data to return.
        autocompleteFragment.setOnPlaceSelectedListener(this)
    }

    private fun setupView() {
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        location = Location(this)
        searchView = findViewById(R.id.sv_location)
        photo = findViewById(R.id.profilePhoto)
        filter = findViewById(R.id.filter_icon)

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

    private fun clickListener() {
        photo.setOnClickListener {
            navigator.goToSettingsScreen()
        }
        filter.setOnClickListener {
            navigator.goToFiltersScreens()
        }
    }

    @SuppressLint("BinaryOperationInTimber")
    override fun onPlaceSelected(place: Place) {
        Timber.i("Place Selected: %s", place.name + " " + place.id)
        // Format the returned place's details and display them in the TextView.
    }

    override fun onError(status: Status) {
        Timber.e("onError: Status = %s", status.toString())

        Toast.makeText(this, "Place selection failed: " + status.statusMessage, Toast.LENGTH_SHORT).show();
    }

    // Initializes contents of Activity's standard options menu. Only called the first time options
    // menu is displayed.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.map_options, menu)
        return true
    }

    // Called whenever an item in your options menu is selected.
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        // Change the map type based on the user's selection.
        R.id.normal_map -> {
            googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            googleMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map ?: return
        if (isPermissionGiven()) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            googleMap.isMyLocationEnabled = true
            googleMap.uiSettings.isMyLocationButtonEnabled = true
            googleMap.uiSettings.isZoomControlsEnabled = true
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

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.lastLocation.addOnCompleteListener(this) { task ->
            if (task.isSuccessful && task.result != null) {
                val mLastLocation = task.result
                var address = "No known address"
                val gcd = Geocoder(this, Locale.getDefault())
                val addresses: List<Address>

                try {
                    addresses = gcd.getFromLocation(mLastLocation!!.latitude, mLastLocation.longitude, 1)
                    if (addresses.isNotEmpty()) {
                        address = addresses[0].getAddressLine(0)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                val icon = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(this.resources, R.drawable.ic_pickup))
                googleMap.addMarker(
                    MarkerOptions().position(LatLng(mLastLocation!!.latitude, mLastLocation.longitude)).title("Current Location").snippet(address)
                        .icon(icon)
                )
                val cameraPosition = CameraPosition.Builder()
                    .target(LatLng(mLastLocation.latitude, mLastLocation.longitude)).zoom(ZOOM_CAMERA).build()
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            } else {
                Toast.makeText(this, "No current location found", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                if (resultCode == Activity.RESULT_OK) {
                    getLocation(location)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getLocation(location: Location) {
        location.getCurrentLocation(object : Location.LocationService {
            override fun getLastLocationInterface() {
                getLastLocation()
            }
        })
    }
}