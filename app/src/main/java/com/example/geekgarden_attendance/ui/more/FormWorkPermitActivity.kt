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
import org.koin.androidx.viewmodel.ext.android.viewModel

class FormWorkPermitActivity : AppCompatActivity() {

    private val viewModel: NavigationViewModel by viewModel()
    private var _binding: ActivityWorkPermitBinding? = null
    private val binding get() = _binding!!
    private lateinit var pdfUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWorkPermitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolBar()
        setSpinner()
        setButtonAction()
    }
    fun setToolBar(){
        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        toolBar?.title = "Form Mengajukan Izin"
        setSupportActionBar(toolBar)
    }

    fun setSpinner(){
        val jenisIzin = resources.getStringArray(R.array.jenisIzin)
        var adapterSpinner = ArrayAdapter(this, R.layout.spinner_item, jenisIzin)
        adapterSpinner.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        binding.spinner.adapter = adapterSpinner
    }

    fun setButtonAction(){
     binding.buttonSuratSakit.setOnClickListener {
         val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
         pdfIntent.type = "application/pdf"
         pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
         startActivityForResult(pdfIntent, 12)
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
                        myCursor = applicationContext!!.contentResolver.query(uri, null, null, null, null)
                        if (myCursor != null && myCursor.moveToFirst()) {
                            pdfName = myCursor.getString(myCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                            binding.textViewNamaSurat.text = pdfName
                        }
                    } finally {
                        myCursor?.close()
                    }
                }
            } else{
                Toast.makeText(this, "Anda Tidak Jadi Memilih File", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}