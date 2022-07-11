package com.example.geekgarden_attendance.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.geekgarden_attendance.databinding.FragmentHomeBinding
import com.example.geekgarden_attendance.ui.home.adapter.MadingGeekGardenAdapter
import com.example.geekgarden_attendance.ui.updateProfile.UpdateProfileActivity
import com.example.geekgarden_attendance.util.Constants
import com.example.geekgarden_attendance.util.Prefs
import com.squareup.picasso.Picasso

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
        setupMadings()
        setButtonAction()
        return root
    }


    private fun setupAdapter(){
        binding.recyclerViewMading.adapter = adapterMading
    }

    private fun setupMadings(){
        homeViewModel.listMadingGeekGarden.observe(requireActivity()) {
            if(!it.isNullOrEmpty()) {
                adapterMading.addItems(it)
            }
        }
    }

    private fun setUserData() {

        val pegawai = Prefs.getPegawai()

        if (pegawai != null) {
            val userNameInitial = pegawai.nama?.split(' ')?.mapNotNull { it.firstOrNull()?.toString() }?.reduce { acc, s -> acc + s }

            binding.miniProfile.apply {
                textViewNama.text = pegawai.nama
                textViewPosisi.text = pegawai.jabatan
                textViewNameInitial.text =  userNameInitial
                Picasso.get().load(Constants.PEGAWAI_URL + pegawai.foto_profile)
                    .into(binding.miniProfile.imageViewProfile)
            }
        }
    }

    private fun setButtonAction(){
        binding.miniProfile.imageButtonEdit.setOnClickListener {
            val intent = Intent(requireContext(), UpdateProfileActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}