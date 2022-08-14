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
import com.example.geekgarden_attendance.core.data.source.remote.network.State
import com.example.geekgarden_attendance.databinding.FragmentAttendanceBinding
import com.example.geekgarden_attendance.ui.login.LoginActivity
import com.example.geekgarden_attendance.ui.maps.MapsActivity
import com.example.geekgarden_attendance.ui.navigation.NavigationViewModel
import com.example.geekgarden_attendance.util.Prefs
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat
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
        setupButtonAction()
        getCurrentLocation()
        checkAbsensi()
        return root
    }

    override fun onResume() {
        super.onResume()
        getCurrentLocation()
        checkAbsensi()
        setupData()
    }

    fun setupData() {
        if (checkDistance() > 100) {
            binding.textViewLocation.text = "Diluar Kantor"
        } else {
            binding.textViewLocation.text = "Dikantor"
        }

        when(Prefs.getCheckAbsensi()?.jumlah_absen_hari_ini){
            1->{
                binding.infoAbsen.textViewJamHadir.text =
                    dateFormat(Prefs.getCheckAbsensi()?.jam_hadir?.tanggal)?: "-"
            }
            2->{
                binding.infoAbsen.apply {
                    textViewTotalJam.text =
                        totalJam(
                            Prefs.getCheckAbsensi()?.jam_hadir?.tanggal,
                            Prefs.getCheckAbsensi()?.jam_pulang?.tanggal
                        )
                    textViewJamHadir.text =
                        dateFormat(Prefs.getCheckAbsensi()?.jam_hadir?.tanggal)?: "-"
                    textViewJamPulang.text =
                        dateFormat(Prefs.getCheckAbsensi()?.jam_pulang?.tanggal)?: "-"
                }
            }
            else->{
                binding.infoAbsen.apply {
                    textViewTotalJam.text ="-"
                    textViewJamHadir.text ="-"
                    textViewJamPulang.text ="-"
                }
            }
        }

        //Tanggal Dibawah JAM
        val formattedDatesString =
            SimpleDateFormat("EEE, dd MMM yyyy", Locale("in", "ID"))
            .format(Date())
        binding.textViewCurrentDate.text = formattedDatesString.toString()

    }

    @SuppressLint("SimpleDateFormat")
    fun dateFormat(date: String?): String?{
        if(date == null) return null

        val myFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
            Locale("in", "ID")
        ).parse(date)
        val formattedDatesString = myFormat?.let {
            SimpleDateFormat("hh:mm",
                Locale("in", "ID")
            ).format(it)
        }

        return formattedDatesString.toString()
    }

    @SuppressLint("SimpleDateFormat")
    fun totalJam(date: String?, date2: String?): String?{
        if(date == null) return null
        val myFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
            Locale("in", "ID")
        ).parse(date)
        val myFormat2 = date2?.let {
            SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
                Locale("in", "ID")
            ).parse(it)
        }

        val diff = (myFormat2?.time ?: 0) - (myFormat?.time ?: 0)
        val diffMinutes = diff / (60*1000)
        val diffHours: Double = diff.toDouble() / (60*60*1000)
        val df = DecimalFormat("#.##")

        if(diffHours>=1){
            return df.format(diffHours).toString()
        }else{
            if(diffMinutes.toString().length > 1) {
                return "00.${diffMinutes.toString().substring(0, 2)}"
            }else{
                return "00.$diffMinutes"
            }
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

        binding.swipe.setOnRefreshListener {
            Handler().postDelayed({
                getCurrentLocation()
                setupData()
                checkAbsensi()
                binding.swipe.isRefreshing = false
            }, 1000)
        }

        binding.buttonAttend.setOnClickListener {
            when(Prefs.getCheckAbsensi()?.jumlah_absen_hari_ini){
                0->{
                    val intent = Intent(requireContext(), FormAttendanceActivity::class.java)
                    startActivity(intent)
                }
                1->{
                    val intent = Intent(requireContext(), FormCompleteAttendanceActivity::class.java)
                    startActivity(intent)
                }
                2->{
                    Toast.makeText(
                        requireContext(), "Anda Sudah Melengkapi Absen, Kembali Lagi Besok",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else->{
                    Toast.makeText(
                        requireContext(), "Terjadi Kesalahan",
                        Toast.LENGTH_SHORT
                    ).show()
                }
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
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // request permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQ_CODE
            )
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                // getting the last known or current location
                if(location != null) {
                    Prefs.setLatitude(location.latitude.toString())
                    Prefs.setLongitude(location.longitude.toString())
                }
                setupData()
            }
            .addOnFailureListener {
                Toast.makeText(
                    requireContext(), "Failed on getting current location",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    fun checkAbsensi(){
        navigationViewModel.checkAbsensi().observe(requireActivity()) {
            when(it.state){
                State.SUCCES -> {
                    Log.d("Check Absensi", Prefs.getCheckAbsensi().toString())
//                    binding.progressBar.isVisible = false
                }
                State.ERROR -> {
                    val message = it.message.toString()
                    Log.d("ERR", message)
                    checkToken(message)
//                    binding.progressBar.isVisible = false
                }
                State.LOADING -> {
//                    binding.progressBar.isVisible = true
                }
            }

        }
    }

    fun checkToken(message: String){
        if(message == "Token is Expired" || message == "Authorization Token not found" || message == "Token is Invalid"){
            Toast.makeText(requireActivity(), "Sessi Telah Selesai, Silahkan Login Kembali", Toast.LENGTH_SHORT).show()
            Prefs.isLogin = false
            Prefs.clear()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }else{
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
        }
    }



    @Deprecated("Deprecated in Java")
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