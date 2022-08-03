package com.example.geekgarden_attendance.ui.history

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.geekgarden_attendance.core.data.source.remote.network.State
import com.example.geekgarden_attendance.databinding.FragmentHistoryBinding
import com.example.geekgarden_attendance.ui.history.adapter.RiwayatAbsensiAdapter
import com.example.geekgarden_attendance.ui.login.LoginActivity
import com.example.geekgarden_attendance.ui.navigation.NavigationViewModel
import com.example.geekgarden_attendance.util.Prefs
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("DEPRECATION")
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val adapterRiwayatAbsensi = RiwayatAbsensiAdapter()
    private val binding get() = _binding!!
    private lateinit var historyViewModel: HistoryViewModel
    private val viewModel: NavigationViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupAdapter()
        setupButton()
        setupRiwayatAbsensi()
        return root
    }

    private fun setupAdapter(){
        binding.recyclerViewRiwayatAbsensi.adapter = adapterRiwayatAbsensi
    }

    private fun setupRiwayatAbsensi(){
        historyViewModel.listRiwayatAbsensi.observe(requireActivity()) {
            if(!it.isNullOrEmpty()) {
                adapterRiwayatAbsensi.addItems(it.sortedByDescending { it.id_absensi })
            }
        }
    }

    private fun setupButton(){
        binding.swipeHistory.setOnRefreshListener {
            Handler().postDelayed({
                riwayatAbsensi()
                setupRiwayatAbsensi()
                binding.swipeHistory.isRefreshing = false
            }, 1000)
        }
    }

    fun riwayatAbsensi(){
        viewModel.riwayatAbsensi().observe(requireActivity()) {
            when(it.state){
                State.SUCCES -> {
                    binding.progressBar.isVisible = false
                }
                State.ERROR -> {
                    val message = it.message.toString()
                    Log.d("ERR", message)
                    checkToken(message)
                    binding.progressBar.isVisible = false

                }
                State.LOADING -> {
                    binding.progressBar.isVisible = true
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}