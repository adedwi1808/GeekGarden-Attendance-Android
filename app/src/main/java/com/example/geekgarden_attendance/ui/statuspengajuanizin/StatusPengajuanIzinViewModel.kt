package com.example.geekgarden_attendance.ui.statuspengajuanizin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.geekgarden_attendance.core.data.repository.AppRepository
import com.example.geekgarden_attendance.core.data.source.model.PengajuanIzin
import com.example.geekgarden_attendance.util.Prefs


class StatusPengajuanIzinViewModel(private val repository: AppRepository) : ViewModel() {

    val listStatusPengajuanIzin: LiveData<List<PengajuanIzin>> = MutableLiveData<List<PengajuanIzin>>().apply {
        value = Prefs.getRiwayatPengajuanIzin()
    }

    fun checkAbsensi() = repository.checkAbsensi().asLiveData()

    fun riwayatPengajuanIzin() = repository.riwayatPengajuanIzin().asLiveData()
}
