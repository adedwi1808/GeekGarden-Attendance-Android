package com.example.geekgarden_attendance.ui.more

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.geekgarden_attendance.ui.navigation.NavigationActivity
import com.example.geekgarden_attendance.databinding.FragmentMoreBinding
import com.example.geekgarden_attendance.ui.updateProfile.UpdateProfileActivity
import com.example.geekgarden_attendance.util.Constants.USER_URL
import com.example.geekgarden_attendance.util.Prefs
import com.squareup.picasso.Picasso

class MoreFragment : Fragment() {

    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!
    private lateinit var moreViewModel: MoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        moreViewModel = ViewModelProvider(this).get(MoreViewModel::class.java)
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        val root: View = binding.root
        buttonAction()
        return root
    }

    override fun onResume() {
        super.onResume()
        setUserData()

    }

    private fun buttonAction() {
        binding.buttonLogOut.setOnClickListener {
            Prefs.isLogin = false
            Prefs.clear()
            val intent = Intent(this@MoreFragment.requireContext(), NavigationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.miniProfile.imageButtonEdit.setOnClickListener {
            val intent = Intent(this@MoreFragment.requireContext(), UpdateProfileActivity::class.java)
            startActivity(intent)
        }
    }


    private fun setUserData() {
        val user = Prefs.getUser()

        if (user != null) {
            val userNameInitial = user.name?.split(' ')?.mapNotNull { it.firstOrNull()?.toString() }?.reduce { acc, s -> acc + s }

            binding.miniProfile.apply {
                textViewNama.text = user.name
                textViewPosisi.text = "Belum ada"
                textViewNameInitial.text =  userNameInitial
                Picasso.get().load(USER_URL+user.image).into(imageViewProfile)
            }



            binding.dataKehadiran.apply {
                moreViewModel.attendanceStats.observe(viewLifecycleOwner,{
                    textViewHadir.text = it.hadir.toString()
                    textViewAlpha.text = it.alpha.toString()
                    textViewIzin.text = it.izin.toString()
                    textViewSakit.text = it.sakit.toString()
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}