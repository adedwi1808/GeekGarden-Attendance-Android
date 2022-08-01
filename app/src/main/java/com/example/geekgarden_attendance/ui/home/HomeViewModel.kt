package com.example.geekgarden_attendance.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geekgarden_attendance.core.data.source.model.MadingGeekGarden
import com.example.geekgarden_attendance.util.Prefs

class HomeViewModel : ViewModel() {


    val listMadingGeekGarden: LiveData<List<MadingGeekGarden>> = MutableLiveData<List<MadingGeekGarden>>().apply {
        value = Prefs.getMading()
    }

}