package com.example.geekgarden_attendance.ui.updateProfile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.geekgarden_attendance.NavigationActivity
import com.example.geekgarden_attendance.core.data.source.remote.network.State
import com.example.geekgarden_attendance.core.data.source.remote.request.LoginRequest
import com.example.geekgarden_attendance.core.data.source.remote.request.UpdateProfileRequest
import com.example.geekgarden_attendance.databinding.ActivityLoginBinding
import com.example.geekgarden_attendance.databinding.ActivityUpdateProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateProfileActivity : AppCompatActivity() {

    private val viewModel: UpdateProfileViewModel by viewModel()

    private var _binding: ActivityUpdateProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
    }

    fun setData() {

    }

    private fun login() {

        if (binding.textInputEditEmail.text!!.isEmpty()) binding.textInputEditEmail.setError("Harap Masukkan Email")
        if (binding.textInputEditUsername.text!!.isEmpty()) binding.textInputEditUsername.setError("Harap Masukkan Nama Lengkap anda")
        if (binding.textInputEditPhone.text!!.isEmpty()) binding.textInputEditPhone.setError("Harap Masukkan Nomor HP")

        var phone =  binding.textInputEditPhone.text.toString()
        var phoneInt = Integer.parseInt(phone)

        val body = UpdateProfileRequest(
            1,
            binding.textInputEditUsername.text.toString(),
            binding.textInputEditEmail.text.toString(),
            phoneInt
        )

//        viewModel.login(body).observe(this) {
//            when(it.state){
//                State.SUCCES -> {
//                    Toast.makeText(this, "Selamat Datang ${it?.data?.name}", Toast.LENGTH_SHORT).show()
//                    binding.progressBar.isVisible = false
//                    val intent = Intent(this, NavigationActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    startActivity(intent)
//                }
//                State.ERROR -> {
//                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
//                    binding.progressBar.isVisible = false
//
//                }
//                State.LOADING -> {
//                    binding.progressBar.isVisible = true
//                }
//            }
//
//        }
    }

}