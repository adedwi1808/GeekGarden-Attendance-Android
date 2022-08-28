package com.example.geekgarden_attendance.ui.statuspengajuanizin.detailpengajuanizin

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.databinding.ActivityDetailStatusPengajuanIzinBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class DetailStatusPengajuanIzinActivity : AppCompatActivity() {

    private val viewModel: DetailStatusPengajuanIzinViewModel by viewModel()
    private var _binding: ActivityDetailStatusPengajuanIzinBinding? = null
    private val binding get() = _binding!!
    private var bundle: Bundle? = null
    private var tanggalMulai: String? = null
    private var tanggalSelesai: String? = null
    private var tanggalMengajukan: String? = null
    private var alasanIzin: String? = null
    private var statusIzin: String? = null
    private var jenisIzin: String? = null
    private var suratIzin: String? = null
    private var keteranganAdmin: String? = null
    private var konfirmator: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailStatusPengajuanIzinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bundle = intent.extras

        setupBundle()
        setupLayout()
        visivility()
    }

    private fun visivility(){
        if (statusIzin == "Diajukan"){
            binding.labelKonfirmator.visibility = View.GONE
            binding.textViewKonfirmator.visibility = View.GONE
            binding.labelKeteranganAdmin.visibility = View.GONE
            binding.textViewKeteranganAdmin.visibility = View.GONE
            binding.cardKKonfirmator.visibility = View.GONE
            binding.cardKeteranganAdmin.visibility = View.GONE
        }else{
            binding.labelKonfirmator.visibility = View.VISIBLE
            binding.textViewKonfirmator.visibility = View.VISIBLE
            binding.labelKeteranganAdmin.visibility = View.VISIBLE
            binding.textViewKeteranganAdmin.visibility = View.VISIBLE
            binding.cardKKonfirmator.visibility = View.VISIBLE
            binding.cardKeteranganAdmin.visibility = View.VISIBLE
        }
    }

    private fun setupBundle(){
        tanggalMulai = bundle!!.getString("tanggalMulai")
        tanggalSelesai = bundle!!.getString("tanggalSelesai")
        tanggalMengajukan = bundle!!.getString("tanggalMengajukan")
        alasanIzin = bundle!!.getString("alasanIzin")
        statusIzin = bundle!!.getString("statusIzin")
        suratIzin = bundle!!.getString("suratIzin")
        jenisIzin = bundle!!.getString("jenisIzin")
        keteranganAdmin = bundle!!.getString("keteranganAdmin")
        konfirmator = bundle!!.getString("konfirmator")
    }

    private fun setupLayout(){
        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        toolBar?.title = "Status Pengajuan Izin"
        binding.textViewKeteranganAdmin.movementMethod = ScrollingMovementMethod()
        binding.textViewAlasanIzin.movementMethod = ScrollingMovementMethod()

        setSupportActionBar(toolBar)
        binding.textViewTanggalPengajuan.text = dateFormat(tanggalMengajukan)
        binding.textViewTanggalIzin.text = dateFormat2(tanggalMulai, tanggalSelesai)
        binding.textViewAlasanIzin.text = alasanIzin
        binding.textViewNamaSurat.text = suratIzin ?: "Tidak menyertakan surat"
        binding.textViewStatusIzin.text = statusIzin
        binding.textViewJenisIzin.text = jenisIzin
        binding.textViewKeteranganAdmin.text = keteranganAdmin
        binding.textViewKonfirmator.text = konfirmator
    }

    @SuppressLint("SimpleDateFormat")
    private fun dateFormat(date: String?): String?{
        if(date == null) return null
        val myFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale("in", "ID")).parse(date)
        val formattedDatesString =
            myFormat?.let { SimpleDateFormat("EEE, dd MMM yyyy", Locale("in", "ID")).format(it) }
        return formattedDatesString.toString()
    }

    @SuppressLint("SimpleDateFormat")
    private fun dateFormat2(date: String?, date2: String?): String?{
        if(date == null) return null
        val myFormat = SimpleDateFormat("yyyy-MM-dd", Locale("in", "ID")).parse(date)
        val myFormat2 = SimpleDateFormat("yyyy-MM-dd", Locale("in", "ID")).parse(date2)
        val formattedDatesString =
            myFormat?.let { SimpleDateFormat("EEE, dd MMM yyyy", Locale("in", "ID")).format(it) }

        val formattedDatesString2 =
            myFormat2?.let { SimpleDateFormat("EEE, dd MMM yyyy", Locale("in", "ID")).format(it) }
        return "$formattedDatesString - $formattedDatesString2"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}