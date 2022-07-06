package com.example.geekgarden_attendance.ui.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geekgarden_attendance.core.data.source.local.DummyData
import com.example.geekgarden_attendance.core.data.source.model.AttendanceStats
import com.example.geekgarden_attendance.core.data.source.model.OtherMoreButton

class MoreViewModel : ViewModel() {

    val attendanceStats: LiveData<AttendanceStats> = MutableLiveData<AttendanceStats>().apply {
        value = DummyData.attendanceStatsDummy
    }

    val listOtherMoreButton: LiveData<List<OtherMoreButton>> = MutableLiveData<List<OtherMoreButton>>().apply {
        value = DummyData.listOtherMoreButton
    }
}