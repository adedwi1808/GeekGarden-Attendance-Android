package com.example.geekgarden_attendance.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.geekgarden_attendance.core.data.repository.AppRepository
import com.example.geekgarden_attendance.core.data.source.remote.request.LoginRequest

class LoginViewModel(var repository: AppRepository) : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
//        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    fun login(data: LoginRequest) = repository.login(data).asLiveData()
    fun madings() = repository.selectAllMading().asLiveData()
    fun riwayatAbsensi() = repository.riwayatAbsensi().asLiveData()
    fun dataAbsensi() = repository.dataAbsensi().asLiveData()
    fun checkAbsensi() = repository.checkAbsensi().asLiveData()

}