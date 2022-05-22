package com.example.geekgarden_attendance.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geekgarden_attendance.core.data.source.local.DummyData
import com.example.geekgarden_attendance.core.data.source.model.MadingGeekGarden

class HomeViewModel : ViewModel() {
    val listMadingGeekGarden: LiveData<List<MadingGeekGarden>> = MutableLiveData<List<MadingGeekGarden>>().apply {
        value = DummyData.listMadingGeekGarden
    }
}