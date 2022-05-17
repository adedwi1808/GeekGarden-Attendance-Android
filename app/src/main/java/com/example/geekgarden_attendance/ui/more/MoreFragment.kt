package com.example.geekgarden_attendance.ui.more

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.geekgarden_attendance.NavigationActivity
import com.example.geekgarden_attendance.databinding.FragmentMoreBinding
import com.example.geekgarden_attendance.util.Prefs

class MoreFragment : Fragment() {

    private var _binding: FragmentMoreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(MoreViewModel::class.java)

        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        val root: View = binding.root
        buttonAction()
        setUserData()
        return root
    }

    private fun buttonAction() {
        binding.buttonLogOut.setOnClickListener {
            Prefs.isLogin = false
            val intent = Intent(this@MoreFragment.requireContext(), NavigationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }


    private fun setUserData() {
        val user = Prefs.getUser()

        if (user != null) {
            val userNameInitial = user.name?.split(' ')?.mapNotNull { it.firstOrNull()?.toString() }?.reduce { acc, s -> acc + s }

            binding.apply {
                textViewNama.text = user.name
                textViewPosisi.text = "Belum ada"
                textViewNameInitial.text =  userNameInitial
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}