package com.example.geekgarden_attendance.core.data.source.remote.network

import com.example.geekgarden_attendance.core.data.source.remote.request.*
import com.example.geekgarden_attendance.core.data.source.remote.response.*
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

    @GET("cek-absensi")
    suspend fun checkAbsensi(
        @Header("Authorization") token: String,
    ): Response<CheckAbsensiResponse>

    @POST("absensi-hadir")
    suspend fun doAttendance(
        @Header("Authorization") token: String,
        @Body data: AttendanceRequest
        ): Response<AttendanceResponse>


    @Multipart
    @POST("upload-bukti-absensi/{id_absensi}")
    suspend fun uploadAttendanceImage(
        @Header("Authorization") token: String,
        @Path("id_absensi") int: Int? = null,
        @Part data: MultipartBody.Part? = null,
    ): Response<AttendanceResponse>

    @POST("absensi-pulang")
    suspend fun completeAttendance(
        @Header("Authorization") token: String,
        @Body data: CompleteAttendanceRequest
    ):Response<AttendanceResponse>

    @POST("pengajuan-izin")
    suspend fun workPermit(
        @Header("Authorization") token: String,
        @Body data: PengajuanIzinRequest
    ):Response<PengajuanIzinResponse>

    @Multipart
    @POST("upload-pengajuan-izin/{id_pengajuan_izin}")
    suspend fun uploadWorkPermitApplicationLetter(
        @Header("Authorization") token: String,
        @Path("id_pengajuan_izin") int: Int? = null,
        @Part data: MultipartBody.Part? = null,
    ): Response<PengajuanIzinResponse>

}