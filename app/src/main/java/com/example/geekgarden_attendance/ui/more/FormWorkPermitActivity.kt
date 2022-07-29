package com.example.geekgarden_attendance.ui.more

import android.app.Activity
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.core.data.source.remote.network.State
import com.example.geekgarden_attendance.core.data.source.remote.request.PengajuanIzinRequest
import com.example.geekgarden_attendance.databinding.ActivityWorkPermitBinding
import com.example.geekgarden_attendance.extension.toMultipartBody
import com.example.geekgarden_attendance.ui.navigation.NavigationViewModel
import com.example.geekgarden_attendance.util.Prefs
import com.github.drjacky.imagepicker.ImagePicker
import com.google.android.material.datepicker.MaterialDatePicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class FormWorkPermitActivity : AppCompatActivity(){

    private val viewModel: NavigationViewModel by viewModel()
    private var _binding: ActivityWorkPermitBinding? = null
    private val binding get() = _binding!!
    private lateinit var tanggalMulaiIzin: String
    private lateinit var tanggalSelesaiIzin: String
    private var fileImage: File? = null


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

    private fun picImage() {
        ImagePicker.with(this)
            .crop()
            .maxResultSize(1920,1920,true)
            .createIntentFromDialog { launcher.launch(it) }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it.data?.data!!
            binding.textViewNamaSurat.text = getFileName(uri)
            fileImage = File(uri.path!!)
        }
    }


    private fun setButtonAction() {
        binding.buttonSuratSakit.setOnClickListener {
            picImage()
        }

        binding.datePicker.setOnClickListener {
            datePickerHandler()
        }

        binding.buttonSubmit.setOnClickListener{
            if (binding.alasanIzin.length() < 1 || tanggalMulaiIzin.length < 1 || tanggalSelesaiIzin.length < 1){
                Toast.makeText(this, "Silahkan Lengkapi Form", Toast.LENGTH_SHORT).show()
            }else {
                workPermit()
            }
        }
    }


    private fun workPermit() {

        val body = PengajuanIzinRequest(
            jenis_izin = binding.spinner.selectedItem.toString(),
            alasan_izin = binding.alasanIzin.text.toString(),
            tanggal_mulai_izin = tanggalMulaiIzin,
            tanggal_selesai_izin = tanggalSelesaiIzin,
            status_izin = "diAjukan"
        )

        viewModel.workPermit(body).observe(this) {
            when(it.state){
                State.SUCCES -> {
                    if (fileImage?.exists() == true){
                        uploadWorkPermitApplicationLetter()
                    }else{
                        Toast.makeText(this, "Anda Berhasil Mengajukan Izin", Toast.LENGTH_SHORT).show()
                        binding.progressBar.isVisible = false
                        onBackPressed()
                    }
                }
                State.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    Log.d("ERR", it.message.toString())
                    binding.progressBar.isVisible = false

                }
                State.LOADING -> {
                    binding.progressBar.isVisible = true
                }
            }

        }
    }

    private fun uploadWorkPermitApplicationLetter(){
        val id_pengajuan_izin = Prefs.getPengajuanIzin()?.id_pengajuan_izin

        val file = fileImage.toMultipartBody()
        viewModel.uploadWorkPermitApplicationLetter(id_pengajuan_izin, file).observe(this) {
            when(it.state){
                State.SUCCES -> {
                    Toast.makeText(this, "Anda Berhasil Mengajukan Izin", Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                    onBackPressed()
                }
                State.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    Log.d("ERR",it.message.toString())
                    binding.progressBar.isVisible = false

                }
                State.LOADING -> {
                    binding.progressBar.isVisible = true
                }
            }

        }
    }

    fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
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

            val dateFormmat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            tanggalMulaiIzin = dateFormmat.format((Date(it.first)))
            tanggalSelesaiIzin = dateFormmat.format((Date(it.second)))
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}