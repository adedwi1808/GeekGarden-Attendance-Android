package com.example.geekgarden_attendance.ui.statusPengaduanAbsensi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.geekgarden_attendance.core.data.repository.AppRepository
import com.example.geekgarden_attendance.core.data.source.model.PengaduanAbsensi
import com.example.geekgarden_attendance.util.Prefs


class StatusPengaduanAbsensiViewModel(private val repository: AppRepository) : ViewModel() {

    val listStatusPengaduanAbsensi: LiveData<List<PengaduanAbsensi>> = MutableLiveData<List<PengaduanAbsensi>>().apply {
        value = Prefs.getPengaduanAbsensi()
    }

    fun checkAbsensi() = repository.checkAbsensi().asLiveData()

    fun riwayatLaporanAbsensi() = repository.riwayatPengaduanAbsensi().asLiveData()
}