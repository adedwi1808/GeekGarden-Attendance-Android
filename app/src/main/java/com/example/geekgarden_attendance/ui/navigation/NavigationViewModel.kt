package com.example.geekgarden_attendance.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.geekgarden_attendance.core.data.repository.AppRepository
import com.example.geekgarden_attendance.core.data.source.remote.request.AttendanceRequest


class NavigationViewModel(val repository: AppRepository) : ViewModel() {

    fun doAttendance(data: AttendanceRequest) = repository.doAttendance(data).asLiveData()

}