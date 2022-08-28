package com.example.geekgarden_attendance.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.geekgarden_attendance.core.data.source.remote.network.State
import com.example.geekgarden_attendance.core.data.source.remote.request.LupaPasswordRequest
import com.example.geekgarden_attendance.databinding.ActivityLupaPasswordBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LupaPasswordActivity : AppCompatActivity() {

    private val viewModel: LupaPasswordViewModel by viewModel()
    private var _binding: ActivityLupaPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLupaPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
    }

    fun setData() {
        binding.btnLupaPassword.setOnClickListener {
            lupaPassword()
        }
    }

    private fun lupaPassword() {
        if (binding.textInputEmail.text!!.isEmpty()) binding.textInputEmail.error = "Harap Masukkan Email"

        val body = LupaPasswordRequest(
            binding.textInputEmail.text.toString(),
        )

        viewModel.lupaPassword(body).observe(this) {
            when(it.state){
                State.SUCCES -> {
                    Toast.makeText(this, "Silahkan Buka Email Untuk Melakukan Reset Password", Toast.LENGTH_SHORT).show()
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

}