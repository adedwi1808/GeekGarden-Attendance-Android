package com.example.geekgarden_attendance.ui.home.DetailMading

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.databinding.ActivityDetailMadingBinding
import com.example.geekgarden_attendance.util.Constants
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class DetailMadingActivity : AppCompatActivity() {

    private val viewModel: DetailMadingViewModel by viewModel()
    private var _binding: ActivityDetailMadingBinding? = null
    private val binding get() = _binding!!
    private var bundle: Bundle? = null
    private var judul: String? = null
    private var foto: String? = null
    private var tanggal: String? = null
    private var informasi: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailMadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bundle = intent.extras

        setupBundle()
        setupLayout()
    }

    private fun setupBundle(){
        judul = bundle!!.getString("judul")
        foto = bundle!!.getString("foto")
        tanggal = bundle!!.getString("tanggal")
        informasi = bundle!!.getString("informasi")
    }

    private fun setupLayout(){
        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        toolBar?.title = judul
        setSupportActionBar(toolBar)


        Picasso.get().load(Constants.MADING_URL + foto).fit().centerCrop()
            .error(R.drawable.ic_baseline_image_not_supported_24)
            .into(binding.fotoMading)

        binding.TextViewTanggalMading.text = dateFormat(tanggal)

        binding.textViewInformasiMading.text = informasi
    }

    @SuppressLint("SimpleDateFormat")
    private fun dateFormat(date: String?): String?{
        if(date == null) return null
        val myFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale("in", "ID")).parse(date)
        val formattedDatesString =
            myFormat?.let { SimpleDateFormat("EEE, dd MMM yyyy", Locale("in", "ID")).format(it) }
        return formattedDatesString.toString()
    }


}