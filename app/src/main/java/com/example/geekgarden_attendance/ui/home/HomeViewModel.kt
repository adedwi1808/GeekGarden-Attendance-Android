package com.example.geekgarden_attendance.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geekgarden_attendance.core.data.source.model.DataAbsensi
import com.example.geekgarden_attendance.core.data.source.model.MadingGeekGarden
import com.example.geekgarden_attendance.util.Prefs

class HomeViewModel : ViewModel() {


    val listMadingGeekGarden: LiveData<List<MadingGeekGarden>> = MutableLiveData<List<MadingGeekGarden>>().apply {
        value = Prefs.getMading()
    }

    val attendanceStats: LiveData<DataAbsensi> = MutableLiveData<DataAbsensi>().apply {
        value = Prefs.getDataAbsensi()
    }

}