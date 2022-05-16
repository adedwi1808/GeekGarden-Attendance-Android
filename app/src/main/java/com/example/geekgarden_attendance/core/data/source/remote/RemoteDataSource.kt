package com.example.geekgarden_attendance.core.data.source.remote

import com.example.geekgarden_attendance.core.data.source.remote.network.ApiService
import com.example.geekgarden_attendance.core.data.source.remote.request.LoginRequest

class RemoteDataSource(private val api: ApiService) {

    suspend fun login(data: LoginRequest) = api.login(data)

}