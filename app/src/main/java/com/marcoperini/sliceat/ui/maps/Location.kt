package com.marcoperini.sliceat.ui.maps

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.utils.Constants.Companion.TIME_UPDATE_LOCALIZATION
import com.marcoperini.sliceat.utils.Constants.Companion.TIME_UPDATE_LOCALIZATION_FAST
import java.util.*

class Location(private val context: Context) {
    interface LocationService {
        fun getLastLocationInterface()
    }

    // Called when user makes a long press gesture on the map.
    fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            // A Snippet is Additional text that's displayed below the title.
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                latLng.latitude,
                latLng.longitude
            )
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(context.getString(R.string.dropped_pin))
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )
        }
    }

    // Places a marker on the map and displays an info window that contains POI name.
    fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { poi ->
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
            )
            poiMarker.showInfoWindow()
        }
    }

    fun getCurrentLocation(callback: LocationService) {

        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = TIME_UPDATE_LOCALIZATION
        locationRequest.fastestInterval = TIME_UPDATE_LOCALIZATION_FAST

        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)
        val locationSettingsRequest = builder.build()

        val result = LocationServices.getSettingsClient(context).checkLocationSettings(locationSettingsRequest)
        result.addOnCompleteListener { task ->
            try {
                val response = task.getResult(ApiException::class.java)
                if (response!!.locationSettingsStates.isLocationPresent) {
                    callback.getLastLocationInterface()
                }
            } catch (exception: ApiException) {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        val resolvable = exception as ResolvableApiException
                        resolvable.startResolutionForResult(context as Activity?, MapsScreen.REQUEST_LOCATION_PERMISSION)
                    } catch (e: IntentSender.SendIntentException) { /*empty block*/
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    }
                }
            }
        }
    }
}
