package com.example.geekgarden_attendance.ui.attendance

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.geekgarden_attendance.core.data.source.remote.network.State
import com.example.geekgarden_attendance.core.data.source.remote.request.AttendanceRequest
import com.example.geekgarden_attendance.databinding.FragmentAttendanceBinding
import com.example.geekgarden_attendance.ui.maps.MapsActivity
import com.example.geekgarden_attendance.ui.navigation.NavigationViewModel
import com.example.geekgarden_attendance.ui.updateProfile.UpdateProfileActivity
import com.example.geekgarden_attendance.util.Prefs
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel

class AttendanceFragment : Fragment() {

    private var _binding: FragmentAttendanceBinding? = null
    private val binding get() = _binding!!
    private val navigationViewModel: NavigationViewModel by viewModel()
    private val LOCATION_PERMISSION_REQ_CODE = 1000;
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val attendanceViewModel = ViewModelProvider(this).get(AttendanceViewModel::class.java)
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



    fun setupData(){
        if (checkDistance() > 100) {
            binding.textViewLocation.text = "DiLuar Kantor"
        }else{
            binding.textViewLocation.text = "Di Area Kantor"
        }
    }
    fun checkDistance(): Double{
        val startPoint = Location("locationA")
        startPoint.latitude = Prefs.getLatitude().toDouble()
        startPoint.longitude = Prefs.getLongitude().toDouble()

        val endPoint = Location("locationA")
        endPoint.latitude = -7.745356
        endPoint.longitude = 110.362758

        return startPoint.distanceTo(endPoint).toDouble()
    }

    fun setupButtonAction(){
        binding.buttonAttend.setOnClickListener {
//            doAttendance()
            val intent = Intent(requireContext(), FormAttendanceActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLocationInformation.setOnClickListener {
            val intent = Intent(this@AttendanceFragment.requireContext(), MapsActivity::class.java)
            startActivity(intent)
        }
    }


    fun setData(){
    }

    fun doAttendance(){
//        if (binding.buttonLocationInformation.text!!.isEmpty()) binding.textInputEditEmail.setError("Harap Masukkan Email")

        val idUser = Prefs.getUser()?.id
        val body = AttendanceRequest(
            idUser ?: 0,
            tempat_absensi = binding.textViewLocation.text.toString(),
            status_absensi = "asdas",
            longitude = Prefs.getLongitude(),
            latitude = Prefs.getLatitude(),

        )

        navigationViewModel.doAttendance(body).observe(requireActivity()){
            when(it.state){
                State.SUCCES -> {
//                    Toast.makeText(this, "Selamat Datang ${it?.data?.name}", Toast.LENGTH_SHORT).show()
//                    binding.progressBar.isVisible = false
                }
                State.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
//                    binding.progressBar.isVisible = false

                }
                State.LOADING -> {
//                    binding.progressBar.isVisible = true
                }
            }
        }

    }


    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        // checking location permission
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // request permission
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQ_CODE);
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
                Toast.makeText(requireContext(), "Failed on getting current location",
                    Toast.LENGTH_SHORT).show()
            }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQ_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                } else {
                    // permission denied
                    Toast.makeText(requireContext(), "You need to grant permission to access location",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}