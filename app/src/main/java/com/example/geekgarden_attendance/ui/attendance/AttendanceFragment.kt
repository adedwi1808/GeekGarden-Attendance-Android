package com.example.geekgarden_attendance.ui.attendance

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.geekgarden_attendance.databinding.FragmentAttendanceBinding
import com.example.geekgarden_attendance.ui.maps.MapsActivity
import com.example.geekgarden_attendance.ui.navigation.NavigationViewModel
import com.example.geekgarden_attendance.util.Prefs
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class AttendanceFragment : Fragment() {

    private var _binding: FragmentAttendanceBinding? = null
    private val binding get() = _binding!!
    private val navigationViewModel: NavigationViewModel by viewModel()
    private val LOCATION_PERMISSION_REQ_CODE = 1000
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ViewModelProvider(this).get(AttendanceViewModel::class.java)
        _binding = FragmentAttendanceBinding.inflate(inflater, container, false)
        val root: View = binding.root
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        binding.swipe.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                getCurrentLocation()
                setupData()
                binding.swipe.isRefreshing = false
            }, 1000)
        }
        setupButtonAction()
        getCurrentLocation()


        return root
    }

    override fun onResume() {
        super.onResume()
        getCurrentLocation()
        Log.d("ADE MANTAP", Prefs.getAttendance().toString())
        setupData()
    }

    fun setupData() {
        if (checkDistance() > 100) {
            binding.textViewLocation.text = "DiLuar Kantor"
        } else {
            binding.textViewLocation.text = "Di Area Kantor"
        }

        //Tanggal Dibawah JAM
        val formattedDatesString = SimpleDateFormat("EEE, dd MMM yyyy", Locale("in", "ID")).format(Date()).toString()
        binding.textViewCurrentDate.text = formattedDatesString

        if (Prefs.getAttendance() != null) {
            val formattedDatesString =
                SimpleDateFormat("hh.mm", Locale("in", "ID")).format(Date()).toString()
            binding.infoAbsen.textViewJamHadir.text = formattedDatesString
        }
    }



    fun checkDistance(): Double {
        val startPoint = Location("locationA")
        startPoint.latitude = Prefs.getLatitude().toDouble()
        startPoint.longitude = Prefs.getLongitude().toDouble()

        val endPoint = Location("locationA")
        endPoint.latitude = -7.745356
        endPoint.longitude = 110.362758

        return startPoint.distanceTo(endPoint).toDouble()
    }

    fun setupButtonAction() {
        binding.buttonAttend.setOnClickListener {
            if (Prefs.userDidNotFinishAttendance) {
                val intent = Intent(requireContext(), FormCompleteAttendanceActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(requireContext(), FormAttendanceActivity::class.java)
                startActivity(intent)
                binding.infoAbsen.textViewJamHadir.text = SimpleDateFormat("hh.mm", Locale("in", "ID")).format(Date()).toString()
            }
        }

        binding.buttonLocationInformation.setOnClickListener {
            val intent = Intent(this@AttendanceFragment.requireContext(), MapsActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        // checking location permission
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // request permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQ_CODE
            );
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                // getting the last known or current location
                Prefs.setLatitude(location.latitude.toString())
                Prefs.setLongitude(location.longitude.toString())
                setupData()
            }
            .addOnFailureListener {
                Toast.makeText(
                    requireContext(), "Failed on getting current location",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQ_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // permission granted
                } else {
                    // permission denied
                    Toast.makeText(
                        requireContext(), "You need to grant permission to access location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}