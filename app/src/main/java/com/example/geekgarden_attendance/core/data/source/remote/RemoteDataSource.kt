package com.example.geekgarden_attendance.core.data.source.remote

import com.example.geekgarden_attendance.core.data.source.remote.network.ApiService
import com.example.geekgarden_attendance.core.data.source.remote.request.LoginRequest
import com.example.geekgarden_attendance.core.data.source.remote.request.UpdateProfileRequest
import okhttp3.MultipartBody

class RemoteDataSource(private val api: ApiService) {

    suspend fun login(data: LoginRequest) = api.login(data)

    suspend fun updateUser(data: UpdateProfileRequest) = api.updateUser(data.id, data)

    suspend fun uploadImage(id: Int? = null, fileImage: MultipartBody.Part? = null) = api.uploadImage(id, fileImage)

}