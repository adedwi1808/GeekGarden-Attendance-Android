package com.example.geekgarden_attendance.ui.navigation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.databinding.ActivityNavigationBinding
import com.example.geekgarden_attendance.ui.login.LoginActivity
import com.example.geekgarden_attendance.util.Prefs
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_navigation)

        navView.setupWithNavController(navController)
        //Memeriksa apakah user sudah login/tidak
        checkSignIn()
    }


    private fun checkSignIn(){
        if (!Prefs.isLogin){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}