package com.example.geekgarden_attendance.ui.navigation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.databinding.ActivityNavigationBinding
import com.example.geekgarden_attendance.ui.login.LoginActivity
import com.example.geekgarden_attendance.util.Prefs
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_navigation)
        navView.setupWithNavController(navController)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        createLocationRequest()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if(locationResult.equals(null)) return
                for (location in locationResult.locations){
                    // Update UI with location data
                    // ...
                }
            }
        }
        //Memeriksa apakah user sudah login/tidak
        checkSignIn()
    }

    fun createLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }
    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }



    private fun checkSignIn(){
        if (!Prefs.isLogin){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}