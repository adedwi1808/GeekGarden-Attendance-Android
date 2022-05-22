package com.example.geekgarden_attendance.core.data.source.remote.network

import com.example.geekgarden_attendance.core.data.source.remote.request.LoginRequest
import com.example.geekgarden_attendance.core.data.source.remote.request.UpdateProfileRequest
import com.example.geekgarden_attendance.core.data.source.remote.response.LoginResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("login")
    suspend fun login(
        @Body login:LoginRequest
    ): Response<LoginResponse>

    @PUT("update-user/{id}")
    suspend fun updateUser(
        @Path("id") int: Int,
        @Body data: UpdateProfileRequest
    ): Response<LoginResponse>

    @Multipart
    @POST("upload-user/{id}")
    suspend fun uploadImage(
        @Path("id") int: Int? = null,
        @Part data: MultipartBody.Part? = null
    ): Response<LoginResponse>
}