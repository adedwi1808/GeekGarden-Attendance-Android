package com.example.geekgarden_attendance.ui.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.util.Prefs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


internal class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val geekGardenLoc = LatLng(-7.745356, 110.362758)
        val circleOptions = CircleOptions()
            .center(geekGardenLoc)
            .radius(20.0)
            .strokeWidth(3.7F)
            .strokeColor(R.color.colorPrimary)

        // Get back the mutable Circle
        mMap.addCircle(circleOptions)

        //Marker GeekGarden
        mMap.addMarker(
            MarkerOptions()
                .position(geekGardenLoc)
                .alpha(0.9F)
                .title("GeekGarden Office")
        )
        val user = LatLng(Prefs.getLatitude().toDouble(), Prefs.getLongitude().toDouble())

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user, 10f))
        mMap.animateCamera(CameraUpdateFactory.zoomIn())
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17f), 5000, null)

        enableMyLocation()
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (!::mMap.isInitialized) return
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            val perms = arrayOf("android.permission.ACCESS_FINE_LOCATION")
            // Permission to access the location is missing. Show rationale and request permission
            ActivityCompat.requestPermissions(this, perms, 200)
        }
    }

}

