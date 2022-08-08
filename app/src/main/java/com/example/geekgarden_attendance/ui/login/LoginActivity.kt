package com.example.geekgarden_attendance.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.geekgarden_attendance.core.data.source.remote.network.State
import com.example.geekgarden_attendance.core.data.source.remote.request.LoginRequest
import com.example.geekgarden_attendance.databinding.ActivityLoginBinding
import com.example.geekgarden_attendance.ui.navigation.NavigationActivity
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
            binding.textInputEmail.setText(it)
        }

        binding.btnSignIn.setOnClickListener {
            login()
        }

        binding.btnLupaPassword.setOnClickListener {
            val intent = Intent(this, LupaPasswordActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun login() {
        if (binding.textInputEmail.text!!.isEmpty()) binding.textInputEmail.setError("Harap Masukkan Email")
        if (binding.textInputPassword.text!!.isEmpty()) binding.textInputPassword.setError("Harap Masukkan Password")


        val body = LoginRequest(
            binding.textInputEmail.text.toString(),
            binding.textInputPassword.text.toString(),
        )

        viewModel.login(body).observe(this) {
            when(it.state){
                State.SUCCES -> {
                    Toast.makeText(this, "Selamat Datang ${it?.data?.nama}", Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                    riwayatAbsensi()
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
    fun riwayatAbsensi(){
        viewModel.riwayatAbsensi().observe(this) {
            when(it.state){
                State.SUCCES -> {
                    madings()
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

    fun madings(){
        viewModel.madings().observe(this) {
            when(it.state){
                State.SUCCES -> {
                    checkAbsensi()
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

    fun checkAbsensi(){
        viewModel.checkAbsensi().observe(this) {
            when(it.state){
                State.SUCCES -> {
                    binding.progressBar.isVisible = false
                    val intent = Intent(this, NavigationActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
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

}