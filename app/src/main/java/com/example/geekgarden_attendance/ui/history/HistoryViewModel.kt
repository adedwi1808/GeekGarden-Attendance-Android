package com.example.geekgarden_attendance.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geekgarden_attendance.core.data.source.model.Absensi
import com.example.geekgarden_attendance.core.data.source.model.MadingGeekGarden
import com.example.geekgarden_attendance.util.Prefs

class HistoryViewModel : ViewModel() {

    val listRiwayatAbsensi: LiveData<List<Absensi>> = MutableLiveData<List<Absensi>>().apply {
        value = Prefs.getRiwayatAbsensi()
    }
}