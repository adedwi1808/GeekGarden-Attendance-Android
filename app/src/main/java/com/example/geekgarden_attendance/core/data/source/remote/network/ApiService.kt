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
    @POST("login-pegawai")
    suspend fun login(
        @Body login:LoginRequest
    ): Response<LoginResponse>

    @PUT("update-pegawai/{id_pegawai}")
    suspend fun updatePegawai(
        @Header("Authorization") token: String,
        @Path("id_pegawai") int: Int,
        @Body data: UpdateProfileRequest
    ): Response<LoginResponse>

    @Multipart
    @POST("foto-pegawai/{id_pegawai}")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Path("id_pegawai") int: Int? = null,
        @Part data: MultipartBody.Part? = null
    ): Response<LoginResponse>

    @GET("madings")
    suspend fun selectAllMading(
        @Header("Authorization") token: String,
        ): Response<SelectAllMadingResponse>

    @POST("absensi-hadir/{id_pegawai}")
    suspend fun doAttendance(
        @Header("Authorization") token: String,
        @Path("id_pegawai") int: Int? = null,
        @Body data: AttendanceRequest
        ): Response<AttendanceResponse>


    @Multipart
    @POST("upload-bukti-absensi/{id_absensi}")
    suspend fun uploadAttendanceImage(
        @Header("Authorization") token: String,
        @Path("id_absensi") int: Int? = null,
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