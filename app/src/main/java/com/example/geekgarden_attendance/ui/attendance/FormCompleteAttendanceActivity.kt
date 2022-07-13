package com.example.geekgarden_attendance.ui.attendance

import android.app.Activity
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.core.data.source.remote.network.State
import com.example.geekgarden_attendance.core.data.source.remote.request.AttendanceRequest
import com.example.geekgarden_attendance.core.data.source.remote.request.CompleteAttendanceRequest
import com.example.geekgarden_attendance.databinding.ActivityFormCompleteAttendanceBinding
import com.example.geekgarden_attendance.extension.toMultipartBody
import com.example.geekgarden_attendance.ui.navigation.NavigationViewModel
import com.example.geekgarden_attendance.util.Prefs
import com.github.drjacky.imagepicker.ImagePicker
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class FormCompleteAttendanceActivity : AppCompatActivity() {

    private val viewModel: NavigationViewModel by viewModel()
    private var _binding: ActivityFormCompleteAttendanceBinding? = null
    private val binding get() = _binding!!
    private var fileImage: File? = null
    private var imageViewIsNull: Boolean = true
    private var tempatAbsen: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFormCompleteAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupData()
        setToolBar()
        setButtonAction()
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
            fileImage = File(uri.path!!)
            binding.imageViewCompleteAttendance.setPadding(0)
            Picasso.get().load(uri).fit().centerCrop().into(binding.imageViewCompleteAttendance)
            imageViewIsNull = false
        }
    }

    fun setToolBar(){
        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        toolBar?.title = "Form Absensi"
        setSupportActionBar(toolBar)
    }

    fun setButtonAction(){
        binding.buttonSubmit.setOnClickListener {
            doAttendance()
        }

        binding.imageViewCompleteAttendance.setOnClickListener {
            picImage()
        }
    }

    private fun doAttendance() {

        if (imageViewIsNull) {
            Toast.makeText(this,"Harap Masukkan gambar", Toast.LENGTH_SHORT).show()
            return
        }
        val body = CompleteAttendanceRequest(
            tempat = tempatAbsen ,
            status = "Pulang",
            longitude = Prefs.getLongitude(),
            latitude = Prefs.getLatitude(),
            progress_hari_ini = binding.textViewProgresshariIni.text.toString()
            )

        viewModel.completeAttendance(body).observe(this) {
            when(it.state){
                State.SUCCES -> {
                    uploadAttendanceImage()
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

    private fun uploadAttendanceImage(){
        val idAbsen = Prefs.getAttendance()?.id_absensi
        val file = fileImage.toMultipartBody("foto")
        viewModel.uploadAttendanceImage(idAbsen, file).observe(this) {
            when(it.state){
                State.SUCCES -> {
                    checkAbsensi()
                }
                State.ERROR -> {
                    Log.d("ERR",it.message.toString())
                    binding.progressBar.isVisible = false

                }
                State.LOADING -> {
                    binding.progressBar.isVisible = true
                }
            }

        }
    }

    fun checkAbsensi(){
        viewModel.checkAbsensi().observe(this) {
            when(it.state){
                State.SUCCES -> {
                    Toast.makeText(this, "Anda Berhasil Melakukan Absensi", Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                    onBackPressed()
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

    fun setupData(){
        if (checkDistance() > 100) {
            tempatAbsen = "DiLuar Kantor"
        }else{
            tempatAbsen = "Di Area Kantor"
        }
    }
    fun checkDistance(): Double{
        val startPoint = Location("locationA")
        startPoint.latitude = Prefs.getLatitude().toDouble()
        startPoint.longitude = Prefs.getLongitude().toDouble()

        val endPoint = Location("locationA")
        endPoint.latitude = -7.745356
        endPoint.longitude = 110.362758

        return startPoint.distanceTo(endPoint).toDouble()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}