package com.example.geekgarden_attendance.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.geekgarden_attendance.core.data.repository.AppRepository
import com.example.geekgarden_attendance.core.data.source.remote.request.LupaPasswordRequest

class LupaPasswordViewModel(var repository: AppRepository) : ViewModel() {
    fun lupaPassword(data: LupaPasswordRequest) = repository.lupaPassword(data).asLiveData()
}