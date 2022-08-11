package com.example.geekgarden_attendance.ui.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geekgarden_attendance.core.data.source.local.DummyData
import com.example.geekgarden_attendance.core.data.source.model.DataAbsensi
import com.example.geekgarden_attendance.core.data.source.model.OtherMoreButton
import com.example.geekgarden_attendance.util.Prefs

class MoreViewModel : ViewModel() {

    val attendanceStats: LiveData<DataAbsensi> = MutableLiveData<DataAbsensi>().apply {
        value = Prefs.getDataAbsensi()
    }

    val listOtherMoreButton: LiveData<List<OtherMoreButton>> = MutableLiveData<List<OtherMoreButton>>().apply {
        value = DummyData.listOtherMoreButton
    }
}