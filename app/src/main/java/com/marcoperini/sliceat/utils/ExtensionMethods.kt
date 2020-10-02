package com.marcoperini.sliceat.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.marcoperini.sliceat.R
import java.io.IOException
import java.util.*

val <T> T.exhaustive: T
    get() = this

fun ImageView.loadRoundImageFromUrl(imageUrl: String?) {
    imageUrl?.let {
        val httpsImageUrl = imageUrl.replace("http://", "https://")

        Glide.with(this.context)
            .load(httpsImageUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(this)
    }
}

fun ImageView.loadImageFromUrl(imageUrl: String?) {
    imageUrl?.let {
        val httpsImageUrl = imageUrl.replace("http://", "https://")

        Glide.with(this.context)
            .load(httpsImageUrl)
            .into(this)
    }
}

fun ImageView.transformImageToRoundImage(imageUrl: String?) {
    imageUrl?.let {

        Glide.with(this.context)
            .load(imageUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(this)
    }
}

fun getLastLocation(activity: Activity, fusedLocationProviderClient : FusedLocationProviderClient, googleMap: GoogleMap) {
    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }
    fusedLocationProviderClient.lastLocation.addOnCompleteListener(activity) { task ->
        if (task.isSuccessful && task.result != null) {
            val mLastLocation = task.result
            var address = "No known address"
            val gcd = Geocoder(activity, Locale.getDefault())
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
                .target(LatLng(mLastLocation.latitude, mLastLocation.longitude)).zoom(Constants.ZOOM_CAMERA).build()
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        } else {
            Toast.makeText(activity, "No current location found", Toast.LENGTH_LONG).show()
        }
    }
}


fun searchLocation(activity: Activity, searchView: SearchView, googleMap: GoogleMap): Boolean {
    val location = searchView.query.toString()

    if (location.isEmpty()) {
        return false
    }
    val geocoder = Geocoder(activity)

    try {
        val addressList = geocoder.getFromLocationName(location, 1)
        if (addressList.size == 0) {
            val toast: Toast = Toast.makeText(activity, "adresse not found", Toast.LENGTH_LONG)
            val view = toast.view
            view.background.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                ContextCompat.getColor(activity, R.color.black), BlendModeCompat.SRC_ATOP
            )
            val text: TextView = view.findViewById(android.R.id.message)
            text.setTextColor(ContextCompat.getColor(activity, R.color.white))
            toast.show()
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
