package com.example.geekgarden_attendance.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.geekgarden_attendance.R
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

    fun setData(){
        viewModel.text.observe(this) {
            binding.TextinputEmail.setText(it)
        }

        binding.btnSignIn.setOnClickListener {
            viewModel.ubahData()
        }
    }

    fun testData() {

    }


}