package com.example.geekgarden_attendance.core.data.source.remote.network

import com.example.geekgarden_attendance.core.data.source.remote.request.AttendanceRequest
import com.example.geekgarden_attendance.core.data.source.remote.request.CompleteAttendanceRequest
import com.example.geekgarden_attendance.core.data.source.remote.request.LoginRequest
import com.example.geekgarden_attendance.core.data.source.remote.request.UpdateProfileRequest
import com.example.geekgarden_attendance.core.data.source.remote.response.AttendanceResponse
import com.example.geekgarden_attendance.core.data.source.remote.response.LoginResponse
import com.example.geekgarden_attendance.core.data.source.remote.response.SelectAllMadingResponse
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
        @Header("Authorization") token: String,
        @Path("id") int: Int,
        @Body data: UpdateProfileRequest
    ): Response<LoginResponse>

    @Multipart
    @POST("upload-user/{id}")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Path("id") int: Int? = null,
        @Part data: MultipartBody.Part? = null
    ): Response<LoginResponse>

    @GET("madings")
    suspend fun selectAllMading(
        @Header("Authorization") token: String,
        ): Response<SelectAllMadingResponse>

    @POST("fill-attendance/{id}")
    suspend fun doAttendance(
        @Header("Authorization") token: String,
        @Path("id") int: Int? = null,
        @Part data: AttendanceRequest,
        @Part fileImage: MultipartBody.Part? = null,
        ): Response<AttendanceResponse>


    @Multipart
    @POST("upload-attendance-image/{id}")
    suspend fun uploadAttendanceImage(
        @Header("Authorization") token: String,
        @Path("id") int: Int? = null,
        @Part data: MultipartBody.Part? = null,
    ): Response<AttendanceResponse>

    @PUT("complete-attendance/{id}")
    suspend fun completeAttendance(
        @Header("Authorization") token: String,
        @Path("id") int: Int? = null,
        @Body data: CompleteAttendanceRequest
    ):Response<AttendanceResponse>

    @Multipart
    @POST("upload-complete-attendance-image/{id}")
    suspend fun uploadCompleteAttendanceImage(
        @Header("Authorization") token: String,
        @Path("id") int: Int? = null,
        @Part data: MultipartBody.Part? = null,

    ): Response<AttendanceResponse>
}