package com.example.geekgarden_attendance.ui.more

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.databinding.ActivityReportAttendanceBinding
import com.example.geekgarden_attendance.ui.navigation.NavigationViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class FormReportAttendanceActivity : AppCompatActivity() {

    private val viewModel: NavigationViewModel by viewModel()
    private var _binding: ActivityReportAttendanceBinding? = null
    private val binding get() = _binding!!
    private var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityReportAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolBar()
        setButtonAction()
    }

    private fun setToolBar() {
        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        toolBar?.title = "Form Melaporkan Absensi"
        setSupportActionBar(toolBar)
    }

    private fun setButtonAction() {
        binding.buttonSuratSakit.setOnClickListener {
            val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
            pdfIntent.type = "application/pdf"
            pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(pdfIntent, 12)
        }

        binding.datePicker.setOnClickListener {
            datePickerHandler()
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


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}