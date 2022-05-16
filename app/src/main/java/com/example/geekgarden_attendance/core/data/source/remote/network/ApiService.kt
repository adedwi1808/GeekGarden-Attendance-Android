package com.example.geekgarden_attendance.core.data.source.remote.network

import com.example.geekgarden_attendance.core.data.source.remote.request.LoginRequest
import com.example.geekgarden_attendance.core.data.source.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(
        @Body login:LoginRequest
    ): Response<LoginResponse>


}