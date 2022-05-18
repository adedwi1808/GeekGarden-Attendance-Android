package com.example.geekgarden_attendance.ui.updateProfile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.geekgarden_attendance.R
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
    }

    fun setToolBar(){
        val toolBar = findViewById(R.id.toolbar) as Toolbar?
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