package com.example.geekgarden_attendance.ui.statusLaporanAbsensi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.geekgarden_attendance.core.data.repository.AppRepository
import com.example.geekgarden_attendance.core.data.source.model.LaporanAbsensi
import com.example.geekgarden_attendance.util.Prefs


class StatusLaporanAbsensiViewModel(private val repository: AppRepository) : ViewModel() {

    val listStatusLaporanAbsensi: LiveData<List<LaporanAbsensi>> = MutableLiveData<List<LaporanAbsensi>>().apply {
        value = Prefs.getLaporanAbsensi()
    }

    fun checkAbsensi() = repository.checkAbsensi().asLiveData()

    fun riwayatLaporanAbsensi() = repository.riwayatLaporanAbsensi().asLiveData()
}