package com.example.geekgarden_attendance.ui.attendance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.geekgarden_attendance.core.data.source.remote.network.State
import com.example.geekgarden_attendance.core.data.source.remote.request.AttendanceRequest
import com.example.geekgarden_attendance.core.data.source.remote.request.UpdateProfileRequest
import com.example.geekgarden_attendance.databinding.FragmentAttendanceBinding
import com.example.geekgarden_attendance.ui.navigation.NavigationViewModel
import com.example.geekgarden_attendance.util.Prefs
import org.koin.androidx.viewmodel.ext.android.viewModel

class AttendanceFragment : Fragment() {

    private var _binding: FragmentAttendanceBinding? = null
    private val binding get() = _binding!!
    private val navigationViewModel: NavigationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val attendanceViewModel = ViewModelProvider(this).get(AttendanceViewModel::class.java)
        _binding = FragmentAttendanceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupButtonAction()
        return root
    }

    fun setupButtonAction(){
        binding.buttonAttend.setOnClickListener {
            doAttendance()
        }

        binding.buttonLocationInformation.setOnClickListener {

        }
    }


    fun setData(){
    }

    fun doAttendance(){
//        if (binding.buttonLocationInformation.text!!.isEmpty()) binding.textInputEditEmail.setError("Harap Masukkan Email")

        val idUser = Prefs.getUser()?.id
        val body = AttendanceRequest(
            idUser ?: 0,
            tempat_absensi = "adsa",
            status_absensi = "asdas",
            longitude = "adsada",
            latitude = "adsada"
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}