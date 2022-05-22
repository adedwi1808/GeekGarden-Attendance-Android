package com.example.geekgarden_attendance.ui.updateProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.geekgarden_attendance.core.data.repository.AppRepository
import com.example.geekgarden_attendance.core.data.source.remote.request.LoginRequest
import com.example.geekgarden_attendance.core.data.source.remote.request.UpdateProfileRequest
import okhttp3.MultipartBody

class UpdateProfileViewModel(val repository: AppRepository) : ViewModel() {

    fun updateUser(data: UpdateProfileRequest) = repository.updateUser(data).asLiveData()

    fun uploadImage(id: Int? = null, fileImage: MultipartBody.Part? = null) = repository.uploadImage(id, fileImage).asLiveData()

}