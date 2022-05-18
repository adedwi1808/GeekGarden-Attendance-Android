package com.example.geekgarden_attendance.ui.updateProfile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.core.data.source.remote.network.State
import com.example.geekgarden_attendance.core.data.source.remote.request.UpdateProfileRequest
import com.example.geekgarden_attendance.databinding.ActivityUpdateProfileBinding
import com.example.geekgarden_attendance.util.Prefs
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateProfileActivity : AppCompatActivity() {

    private val viewModel: UpdateProfileViewModel by viewModel()

    private var _binding: ActivityUpdateProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolBar()
        setData()
        buttonAction()
    }

    fun buttonAction(){
        binding.buttonEdit.setOnClickListener {
            updateUser()
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
            binding.apply {
                textInputEditUsername.setText(user.name)
//                textViewPosisi.text = "Belum ada"
                textInputEditEmail.setText(user.email)
                textInputEditPhone.setText(user.phone)
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
//                    val intent = Intent(this, NavigationActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    startActivity(intent)
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