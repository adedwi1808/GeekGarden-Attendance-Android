package com.example.geekgarden_attendance.ui.history

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
import com.example.geekgarden_attendance.ui.navigation.NavigationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

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
        setupButton()
        setupAdapter()
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
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    Log.d("ERR", it.message.toString())
                    binding.progressBar.isVisible = false

                }
                State.LOADING -> {
                    binding.progressBar.isVisible = true
                }
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}