package com.marcoperini.sliceat.ui.maps.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
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
import androidx.core.view.isNotEmpty
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.zxing.integration.android.IntentIntegrator
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import com.marcoperini.sliceat.ui.maps.Location
import com.marcoperini.sliceat.utils.CheckConnection
import com.marcoperini.sliceat.utils.Constants.Companion.ZOOM_CAMERA
import com.marcoperini.sliceat.utils.sharedpreferences.Key
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorage
import com.marcoperini.sliceat.utils.transformImageToRoundImage
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.io.IOException
import java.util.*

class MapsScreen : AppCompatActivity(), OnMapReadyCallback, PermissionListener/*, PlaceSelectionListener*/ {

    companion object {
        const val REQUEST_LOCATION_PERMISSION = 1
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, MapsScreen::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    private val prefs: KeyValueStorage by inject()
    private val navigator: Navigator by inject()
    private val mapsViewModel: MapsViewModel by inject()

    private lateinit var photo: ImageView
    private lateinit var mapView: View
    private lateinit var qrCode: ImageButton
    private lateinit var popupMenu: ImageButton
    private lateinit var roundIconPlus: ImageView
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

        if(!CheckConnection.isOnline(this))
            Toast.makeText(this@MapsScreen, "no connection", Toast.LENGTH_LONG).show()

        setupView()

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
        roundIconPlus = findViewById(R.id.round_icon_plus)
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

    private fun setupListener() {
        photo.setOnClickListener {
            navigator.goToSettingsScreen()
            finish()
        }
        roundIconPlus.setOnClickListener {
            navigator.goToRestaurantsScreen()
            finish()
        }
        filter.setOnClickListener {
            navigator.goToFiltersScreen()
            finish()
        }
        qrCode.setOnClickListener { performAction() }
        myLocation.setOnClickListener { getLastLocation() }
    }

    private fun searchQuery() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                val location = searchView.query.toString()

                if (location.isEmpty()) {
                    return false
                }
                val geocoder = Geocoder(this@MapsScreen)

                try {
                    val addressList = geocoder.getFromLocationName(location, 1)
                    if (addressList.size == 0) {
                        Toast.makeText(this@MapsScreen, "adresse not found", Toast.LENGTH_LONG).show()
                    } else if (addressList.size > 0) {
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
        qrScanIntegrator?.initiateScan()
    }

    private fun changeMapTypeView() {
        popupMenu.setOnClickListener {
            val popup = PopupMenu(this, popupMenu)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.map_options, popup.menu)
            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    // Change the map type based on the user's selection.
                    R.id.normal_map -> googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                    R.id.hybrid_map -> googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                    R.id.satellite_map -> googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                    R.id.terrain_map -> googleMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                }
                true
            })
            popup.show()
        }
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
                googleMap.addMarker(
                    MarkerOptions().position(LatLng(mLastLocation!!.latitude, mLastLocation.longitude)).title("Current Location").snippet(address)

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
                getLastLocation()
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
