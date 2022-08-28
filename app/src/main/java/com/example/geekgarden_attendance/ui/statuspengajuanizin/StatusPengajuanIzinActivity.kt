package com.example.geekgarden_attendance.ui.statuspengajuanizin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.core.data.source.remote.network.State
import com.example.geekgarden_attendance.databinding.ActivityStatusPengajuanIzinBinding
import com.example.geekgarden_attendance.ui.history.adapter.StatusPengajuanIzinAdapter
import com.example.geekgarden_attendance.ui.login.LoginActivity
import com.example.geekgarden_attendance.ui.statuspengajuanizin.detailpengajuanizin.DetailStatusPengajuanIzinActivity
import com.example.geekgarden_attendance.util.Prefs
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatusPengajuanIzinActivity : AppCompatActivity() {

    private val viewModel: StatusPengajuanIzinViewModel by viewModel()
    private var _binding: ActivityStatusPengajuanIzinBinding? = null
    private val adapterStatusPengajuanIzin = StatusPengajuanIzinAdapter()
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStatusPengajuanIzinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolBar()
        riwayatPengajuanIzin()
        setupAdapter()
    }

    override fun onStart() {
        super.onStart()
        setupRiwayatPengajuanIzin()
    }

    fun setToolBar(){
        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        toolBar?.title = "Status Pengajuan Izin"
        setSupportActionBar(toolBar)
    }

    private fun setupAdapter(){
        binding.recylerViewStatusPengajuanIzin.adapter = adapterStatusPengajuanIzin
        adapterStatusPengajuanIzin.setOnItemClickListener(object : StatusPengajuanIzinAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@StatusPengajuanIzinActivity, DetailStatusPengajuanIzinActivity::class.java)
                intent.putExtra("konfirmator", Prefs.getRiwayatPengajuanIzin()!![position].admin?.nama)
                intent.putExtra("keteranganAdmin", Prefs.getRiwayatPengajuanIzin()!![position].keterangan_admin)

                intent.putExtra("tanggalMulai", Prefs.getRiwayatPengajuanIzin()!![position].tanggal_mulai_izin)
                intent.putExtra("tanggalSelesai", Prefs.getRiwayatPengajuanIzin()!![position].tanggal_selesai_izin)
                intent.putExtra("statusIzin", Prefs.getRiwayatPengajuanIzin()!![position].status_izin)
                intent.putExtra("suratIzin", Prefs.getRiwayatPengajuanIzin()!![position].surat_izin)
                intent.putExtra("alasanIzin", Prefs.getRiwayatPengajuanIzin()!![position].alasan_izin)
                intent.putExtra("jenisIzin", Prefs.getRiwayatPengajuanIzin()!![position].jenis_izin)
                intent.putExtra("tanggalMengajukan", Prefs.getRiwayatPengajuanIzin()!![position].tanggal_mengajukan_izin)
                startActivity(intent)
            }
        })
    }

    private fun setupRiwayatPengajuanIzin(){
        viewModel.listStatusPengajuanIzin.observe(this) {
            if(!it.isNullOrEmpty()) {
                adapterStatusPengajuanIzin.addItems(it)
            }
        }
    }

    fun riwayatPengajuanIzin(){
        viewModel.riwayatPengajuanIzin().observe(this) {
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