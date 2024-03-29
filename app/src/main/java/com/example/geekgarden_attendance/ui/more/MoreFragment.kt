package com.example.geekgarden_attendance.ui.more

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.geekgarden_attendance.databinding.FragmentMoreBinding
import com.example.geekgarden_attendance.ui.home.adapter.OtherMoreButtonAdapter
import com.example.geekgarden_attendance.ui.navigation.NavigationActivity
import com.example.geekgarden_attendance.ui.statuspengaduanabsensi.StatusPengaduanAbsensiActivity
import com.example.geekgarden_attendance.ui.statuspengajuanizin.StatusPengajuanIzinActivity
import com.example.geekgarden_attendance.ui.updateProfile.UpdateProfileActivity
import com.example.geekgarden_attendance.util.Constants.PEGAWAI_URL
import com.example.geekgarden_attendance.util.Prefs
import com.squareup.picasso.Picasso

class MoreFragment : Fragment() {

    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!
    private lateinit var moreViewModel: MoreViewModel
    private val adapterOtherMoreButton = OtherMoreButtonAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        moreViewModel = ViewModelProvider(this).get(MoreViewModel::class.java)
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        val root: View = binding.root
        buttonAction()
        setupAdapter()
        setupOtherMoreButton()
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

    private fun setupAdapter(){
        binding.recyclerViewOtherMoreButton.adapter = adapterOtherMoreButton
        adapterOtherMoreButton.setOnItemClickListener(object : OtherMoreButtonAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                otherMoreButtonOnClickLogic(position)
            }
        })
    }

    private fun otherMoreButtonOnClickLogic(position: Int){
        when(position){
            0 -> {
                val intent = Intent(requireContext(), FormWorkPermitActivity::class.java)
                startActivity(intent)
            }
            1 -> {
                val intent = Intent(requireContext(), FormReportAttendanceActivity::class.java)
                startActivity(intent)
            }
            2 -> {
                val intent = Intent(requireContext(), StatusPengaduanAbsensiActivity::class.java)
                startActivity(intent)
            }
            3 -> {
                val intent = Intent(requireContext(), StatusPengajuanIzinActivity::class.java)
                startActivity(intent)
            }
            else -> {
                Toast.makeText(requireContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupOtherMoreButton(){
        moreViewModel.listOtherMoreButton.observe(requireActivity()) {
            if(!it.isNullOrEmpty()) {
                adapterOtherMoreButton.addItems(it)
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
                Picasso.get().load(PEGAWAI_URL+pegawai.foto_profile).into(imageViewProfile)
            }

            binding.dataKehadiran.apply {
                moreViewModel.attendanceStats.observe(viewLifecycleOwner,{
                    textViewHadir.text = it.hadir.toString()
                    textViewIzin.text = it.izin.toString()
                    textViewCuti.text = it.cuti.toString()
                    textViewLembur.text = it.lembur.toString()
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}