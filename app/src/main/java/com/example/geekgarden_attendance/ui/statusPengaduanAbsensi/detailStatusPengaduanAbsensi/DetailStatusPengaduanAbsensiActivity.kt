package com.example.geekgarden_attendance.ui.statusPengaduanAbsensi.detailStatusPengaduanAbsensi

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.databinding.ActivityDetailStatusPengaduanAbsensiBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class DetailStatusPengaduanAbsensiActivity : AppCompatActivity() {

    private val viewModel: DetailStatusPengaduanAbsensiViewModel by viewModel()
    private var _binding: ActivityDetailStatusPengaduanAbsensiBinding? = null
    private val binding get() = _binding!!
    private var bundle: Bundle? = null
    private var tanggalAbsen: String? = null
    private var tanggalPengaduan: String? = null
    private var keteranganPengaduan: String? = null
    private var statusPengaduan: String? = null
    private var keteranganAdmin: String? = null
    private var konfirmator: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailStatusPengaduanAbsensiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bundle = intent.extras

        setupBundle()
        setupLayout()
        visivility()
    }

    private fun visivility(){
        if (statusPengaduan == "Diajukan"){
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
        tanggalAbsen = bundle!!.getString("tanggalAbsen")
        tanggalPengaduan = bundle!!.getString("tanggalPengaduan")
        keteranganPengaduan = bundle!!.getString("keteranganPengaduan")
        statusPengaduan = bundle!!.getString("statusPengaduan")
        keteranganAdmin = bundle!!.getString("keteranganAdmin")
        konfirmator = bundle!!.getString("konfirmator")
    }

    private fun setupLayout(){
        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        toolBar?.title = "Status Pengaduan"
        setSupportActionBar(toolBar)
        binding.textViewTanggalPengaduan.text = dateFormat(tanggalPengaduan)
        binding.textViewTanggalAbsen.text = dateFormat2(tanggalAbsen)
        binding.textViewKeteranganPengaduan.text = keteranganPengaduan
        binding.textViewStatusPengaduan.text = statusPengaduan
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
    private fun dateFormat2(date: String?): String?{
        if(date == null) return null
        val myFormat = SimpleDateFormat("yyyy-MM-dd", Locale("in", "ID")).parse(date)
        val formattedDatesString =
            myFormat?.let { SimpleDateFormat("EEE, dd MMM yyyy", Locale("in", "ID")).format(it) }
        return formattedDatesString.toString()
    }
}