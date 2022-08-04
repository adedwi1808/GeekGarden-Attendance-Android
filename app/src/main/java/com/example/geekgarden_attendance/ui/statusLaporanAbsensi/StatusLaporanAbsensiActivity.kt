package com.example.geekgarden_attendance.ui.statusLaporanAbsensi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.databinding.ActivityStatusLaporanAbsensiBinding
import com.example.geekgarden_attendance.ui.history.adapter.StatusLaporanAbsensiAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatusLaporanAbsensiActivity : AppCompatActivity() {

    private val viewModel: StatusLaporanAbsensiViewModel by viewModel()
    private var _binding: ActivityStatusLaporanAbsensiBinding? = null
    private val adapterStatusLaporanAbsensi = StatusLaporanAbsensiAdapter()
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStatusLaporanAbsensiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolBar()
        setupAdapter()
        setupRiwayatAbsensi()
    }

    fun setToolBar(){
        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        toolBar?.title = "Status Laporan Absen"
        setSupportActionBar(toolBar)
    }

    private fun setupAdapter(){
        binding.recylerViewStatusLaporanAbsensi.adapter = adapterStatusLaporanAbsensi
    }

    private fun setupRiwayatAbsensi(){
        viewModel.listStatusLaporanAbsensi.observe(this) {
            if(!it.isNullOrEmpty()) {
                adapterStatusLaporanAbsensi.addItems(it.sortedByDescending { it.id_laporan_absensi })
            }
        }
    }
}