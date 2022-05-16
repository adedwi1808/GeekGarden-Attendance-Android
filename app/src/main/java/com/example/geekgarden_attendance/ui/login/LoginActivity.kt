package com.example.geekgarden_attendance.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.geekgarden_attendance.core.data.source.remote.network.State
import com.example.geekgarden_attendance.core.data.source.remote.request.LoginRequest
import com.example.geekgarden_attendance.databinding.ActivityLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
    }

    fun setData() {
        viewModel.text.observe(this) {
            binding.TextinputEmail.setText(it)
        }

        binding.btnSignIn.setOnClickListener {
            login()
        }
    }

    private fun login() {

        if (binding.TextinputEmail.text!!.isEmpty()) binding.TextinputEmail.setError("Harap Masukkan Email")
        if (binding.TextinputPassword.text!!.isEmpty()) binding.TextinputPassword.setError("Harap Masukkan Password")


        val body = LoginRequest(
            binding.TextinputEmail.text.toString(),
            binding.TextinputPassword.text.toString(),
        )

        viewModel.login(body).observe(this) {
            when(it.state){
                State.SUCCES -> {
                    Toast.makeText(this, "Selamat Datang ${it?.data?.name}", Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false

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