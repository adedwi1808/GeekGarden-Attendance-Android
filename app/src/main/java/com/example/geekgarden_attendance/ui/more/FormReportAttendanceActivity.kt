package com.example.geekgarden_attendance.ui.more

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.core.data.source.remote.network.State
import com.example.geekgarden_attendance.core.data.source.remote.request.AdukanAbsensiRequest
import com.example.geekgarden_attendance.databinding.ActivityReportAttendanceBinding
import com.example.geekgarden_attendance.ui.login.LoginActivity
import com.example.geekgarden_attendance.ui.navigation.NavigationViewModel
import com.example.geekgarden_attendance.util.Prefs
import com.google.android.material.datepicker.MaterialDatePicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class FormReportAttendanceActivity : AppCompatActivity() {

    private val viewModel: NavigationViewModel by viewModel()
    private var _binding: ActivityReportAttendanceBinding? = null
    private val binding get() = _binding!!
    private var tanggalAbsensi: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityReportAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolBar()
        setButtonAction()
    }

    private fun setToolBar() {
        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        toolBar?.title = "Form Pengaduan Absensi"
        setSupportActionBar(toolBar)
    }

    private fun setButtonAction() {
        binding.datePicker.setOnClickListener {
            datePickerHandler()
        }

        binding.buttonSubmit.setOnClickListener {
            if (binding.keteranganPengaduan.length() < 1 || tanggalAbsensi.length < 1){
                Toast.makeText(this, "Silahkan Lengkapi Form", Toast.LENGTH_SHORT).show()
            }else {
                adukanAbsensi()
            }
        }
    }

    fun datePickerHandler() {
        val dateRangePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pilih Tanggal")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        dateRangePicker.show(supportFragmentManager, "DatePicker")

        // Setting up the event for when ok is clicked
        dateRangePicker.addOnPositiveButtonClickListener {
            // formatting date in dd-mm-yyyy format.
            val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
            val first = dateFormatter.format(Date(it))
            Toast.makeText(this, first, Toast.LENGTH_LONG).show()
            val dateFormmat = SimpleDateFormat("yyyy-MM-dd")
            tanggalAbsensi = dateFormmat.format((Date(it)))
            binding.datePicker.text = first
        }

        // Setting up the event for when cancelled is clicked
        dateRangePicker.addOnNegativeButtonClickListener {
            Toast.makeText(this, "Batal memilih tanggal", Toast.LENGTH_LONG).show()
        }

        // Setting up the event for when back button is pressed
        dateRangePicker.addOnCancelListener {
            Toast.makeText(this, "Batal memilih tanggal", Toast.LENGTH_LONG).show()
        }
    }

    private fun adukanAbsensi() {
        val body = AdukanAbsensiRequest(
            tanggal_absen = tanggalAbsensi,
            keterangan_pengaduan = binding.keteranganPengaduan.text.toString()
        )
        viewModel.adukanAbsensi(body).observe(this) {
            when(it.state){
                State.SUCCES -> {
                    Toast.makeText(this, "Berhasil Mengadukan Absensi", Toast.LENGTH_SHORT)
                        .show()
                    binding.progressBar.isVisible = false
                    onBackPressed()
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}