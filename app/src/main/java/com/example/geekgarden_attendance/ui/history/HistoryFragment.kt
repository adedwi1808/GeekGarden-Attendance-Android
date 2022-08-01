package com.example.geekgarden_attendance.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.databinding.FragmentHistoryBinding
import com.example.geekgarden_attendance.ui.history.adapter.RiwayatAbsensiAdapter
import com.example.geekgarden_attendance.ui.home.HomeViewModel
import com.example.geekgarden_attendance.ui.home.adapter.MadingGeekGardenAdapter

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val adapterRiwayatAbsensi = RiwayatAbsensiAdapter()
    private val binding get() = _binding!!
    private lateinit var historyViewModel: HistoryViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}