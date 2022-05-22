package com.example.geekgarden_attendance.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.geekgarden_attendance.databinding.FragmentHomeBinding
import com.example.geekgarden_attendance.ui.home.adapter.MadingGeekGardenAdapter
import com.example.geekgarden_attendance.util.Constants
import com.example.geekgarden_attendance.util.Prefs
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val adapterMading = MadingGeekGardenAdapter()
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupAdapter()
        setUserData()
        return root
    }

    private fun setupAdapter(){
        binding.recyclerViewMading.adapter = adapterMading
    }

    private fun setUserData() {

        homeViewModel.listMadingGeekGarden.observe(requireActivity(),{
            adapterMading.addItems(it)
        })

        val user = Prefs.getUser()

        if (user != null) {
            val userNameInitial = user.name?.split(' ')?.mapNotNull { it.firstOrNull()?.toString() }?.reduce { acc, s -> acc + s }

            binding.miniProfile.apply {
                textViewNama.text = user.name
                textViewPosisi.text = "Belum ada"
                textViewNameInitial.text =  userNameInitial
                Picasso.get().load(Constants.USER_URL +user.image).into(binding.miniProfile.imageViewProfile)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}