package com.example.geekgarden_attendance.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.geekgarden_attendance.databinding.FragmentHomeBinding
import com.example.geekgarden_attendance.ui.home.DetailMading.DetailMadingActivity
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
        adapterMading.setOnItemClickListener(object : MadingGeekGardenAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(requireActivity(), DetailMadingActivity::class.java)
                intent.putExtra("judul", Prefs.getMading()!![position].judul)
                intent.putExtra("informasi", Prefs.getMading()!![position].informasi)
                intent.putExtra("foto", Prefs.getMading()!![position].foto)
                intent.putExtra("tanggal", Prefs.getMading()!![position].create_at)

                startActivity(intent)
            }
        })
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

            binding.dataKehadiran.apply {
                homeViewModel.attendanceStats.observe(viewLifecycleOwner,{
                    textViewHadir.text = it.hadir.toString()
                    textViewIzin.text = it.izin.toString()
                    textViewCuti.text = it.cuti.toString()
                    textViewLembur.text = it.lembur.toString()
                })
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