package com.example.geekgarden_attendance.ui.more

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.databinding.ActivityWorkPermitBinding
import com.example.geekgarden_attendance.ui.navigation.NavigationViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class FormWorkPermitActivity : AppCompatActivity() {

    private val viewModel: NavigationViewModel by viewModel()
    private var _binding: ActivityWorkPermitBinding? = null
    private val binding get() = _binding!!
    private lateinit var pdfUri: Uri
    private var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWorkPermitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolBar()
        setSpinner()
        setButtonAction()
    }

    private fun setToolBar() {
        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        toolBar?.title = "Form Mengajukan Izin"
        setSupportActionBar(toolBar)
    }

    private fun setSpinner() {
        val jenisIzin = resources.getStringArray(R.array.jenisIzin)
        val adapterSpinner = ArrayAdapter(this, R.layout.spinner_item, jenisIzin)
        adapterSpinner.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        binding.spinner.adapter = adapterSpinner
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
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Pilih Tanggal")
                .setSelection(
                    androidx.core.util.Pair(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds(),
                    )
                )
                .build()
        dateRangePicker.show(supportFragmentManager, "DatePicker")

        // Setting up the event for when ok is clicked
        dateRangePicker.addOnPositiveButtonClickListener {
            // formatting date in dd-mm-yyyy format.
            val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
            val first = dateFormatter.format(Date(it.first))
            val second = dateFormatter.format(Date(it.second))
            Toast.makeText(this, "$first - $second", Toast.LENGTH_LONG).show()

            binding.datePicker.text = "$first - $second"
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // For loading PDF
        when (requestCode) {
            12 -> if (resultCode == RESULT_OK) {
                pdfUri = data?.data!!
                val uri: Uri = data?.data!!
                val uriString: String = uri.toString()
                var pdfName: String? = null
                if (uriString.startsWith("content://")) {
                    var myCursor: Cursor? = null
                    try {
                        myCursor =
                            applicationContext!!.contentResolver.query(uri, null, null, null, null)
                        if (myCursor != null && myCursor.moveToFirst()) {
                            pdfName =
                                myCursor.getString(myCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                            binding.textViewNamaSurat.text = pdfName
                        }
                    } finally {
                        myCursor?.close()
                    }
                }
            } else {
                Toast.makeText(this, "Anda Tidak Jadi Memilih File", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}