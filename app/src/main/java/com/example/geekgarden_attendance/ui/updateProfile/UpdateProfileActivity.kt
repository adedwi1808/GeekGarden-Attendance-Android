package com.example.geekgarden_attendance.ui.updateProfile

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.core.data.source.remote.network.State
import com.example.geekgarden_attendance.core.data.source.remote.request.UpdateProfileRequest
import com.example.geekgarden_attendance.databinding.ActivityUpdateProfileBinding
import com.example.geekgarden_attendance.util.Prefs
import com.github.drjacky.imagepicker.ImagePicker
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateProfileActivity : AppCompatActivity() {

    private val viewModel: UpdateProfileViewModel by viewModel()

    private var _binding: ActivityUpdateProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonAction()
        setToolBar()
        setData()
    }

    fun buttonAction(){
        binding.buttonEdit.setOnClickListener {
            updateUser()
        }

        binding.imageViewProfile.setOnClickListener {
            picImage()
        }
    }

    private fun picImage() {
        ImagePicker.with(this)
            .maxResultSize(1080, 1080, true)
            .createIntentFromDialog { launcher.launch(it) }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it.data?.data!!
            // Use the uri to load the image
            Picasso.get().load(uri).into(binding.imageViewProfile)
        }
    }

    fun setToolBar(){
        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        toolBar?.title = "Edit Profile"
        setSupportActionBar(toolBar)
    }


    fun setData() {
        val user = Prefs.getUser()

        if (user != null) {
            val userNameInitial = user.name?.split(' ')?.mapNotNull { it.firstOrNull()?.toString() }?.reduce { acc, s -> acc + s }
            binding.apply {
                textInputEditUsername.setText(user.name)
//                textViewPosisi.text = "Belum ada"
                textInputEditEmail.setText(user.email)
                textInputEditPhone.setText(user.phone)
                textViewNameInitial.setText(userNameInitial)
            }
        }
    }

    private fun updateUser() {

        if (binding.textInputEditEmail.text!!.isEmpty()) binding.textInputEditEmail.setError("Harap Masukkan Email")
        if (binding.textInputEditUsername.text!!.isEmpty()) binding.textInputEditUsername.setError("Harap Masukkan Nama Lengkap anda")
        if (binding.textInputEditPhone.text!!.isEmpty()) binding.textInputEditPhone.setError("Harap Masukkan Nomor HP")


        val idUser = Prefs.getUser()?.id
        val body = UpdateProfileRequest(
            idUser ?: 0,
            name = binding.textInputEditUsername.text.toString(),
            email = binding.textInputEditEmail.text.toString(),
            phone = binding.textInputEditPhone.text.toString()
        )

        viewModel.updateUser(body).observe(this) {
            when(it.state){
                State.SUCCES -> {
                    Toast.makeText(this, "Selamat Datang ${it?.data?.name}", Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                    onBackPressed()
                }
                State.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false

                }
                State.LOADING -> {
                    binding.progressBar.isVisible = true
                }
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}