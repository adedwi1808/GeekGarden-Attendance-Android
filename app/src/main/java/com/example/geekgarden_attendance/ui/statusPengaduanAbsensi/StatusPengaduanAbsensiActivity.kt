package com.example.geekgarden_attendance.ui.statusPengaduanAbsensi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.core.data.source.remote.network.State
import com.example.geekgarden_attendance.databinding.ActivityStatusPengaduanAbsensiBinding
import com.example.geekgarden_attendance.ui.history.adapter.StatusPengaduanAbsensiAdapter
import com.example.geekgarden_attendance.ui.login.LoginActivity
import com.example.geekgarden_attendance.util.Prefs
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatusPengaduanAbsensiActivity : AppCompatActivity() {

    private val viewModel: StatusPengaduanAbsensiViewModel by viewModel()
    private var _binding: ActivityStatusPengaduanAbsensiBinding? = null
    private val adapterStatusLaporanAbsensi = StatusPengaduanAbsensiAdapter()
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStatusPengaduanAbsensiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolBar()
        riwayatLaporanAbsensi()
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
        viewModel.listStatusPengaduanAbsensi.observe(this) {
            if(!it.isNullOrEmpty()) {
                adapterStatusLaporanAbsensi.addItems(it.sortedByDescending { it.id_pengaduan_absensi })
            }
        }
    }

    fun riwayatLaporanAbsensi(){
        viewModel.riwayatLaporanAbsensi().observe(this) {
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
            Toast.makeText(this, "Sessi Telah Selesai, Silahkan Login Kembali", Toast.LENGTH_SHORT).show()
            Prefs.isLogin = false
            Prefs.clear()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }else{
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}