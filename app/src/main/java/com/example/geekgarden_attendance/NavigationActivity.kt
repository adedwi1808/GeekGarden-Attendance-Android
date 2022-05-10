package com.example.geekgarden_attendance

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.geekgarden_attendance.databinding.ActivityNavigationBinding
import com.example.geekgarden_attendance.ui.login.LoginActivity
import com.example.geekgarden_attendance.util.Prefs

class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_navigation)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_attendance,
                R.id.navigation_history, R.id.navigation_more
            )
        )
        navView.setupWithNavController(navController)
        //Memeriksa apakah user sudah login/tidak
        checkSignIn()
    }

    private fun checkSignIn(){
        if (!Prefs(this).getIsLogin(true)){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}