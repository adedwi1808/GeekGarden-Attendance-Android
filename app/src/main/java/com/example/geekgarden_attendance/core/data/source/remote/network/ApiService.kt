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

    @POST("lupa-password")
    suspend fun lupaPassword(
        @Body login:LupaPasswordRequest
    ): Response<LupaPasswordResponse>

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

    @GET("riwayat-absensi")
    suspend fun riwayatAbsensi(
        @Header("Authorization") token: String,
    ): Response<RiwayatAbsensiResponse>

    @GET("cek-absensi")
    suspend fun checkAbsensi(
        @Header("Authorization") token: String,
    ): Response<CheckAbsensiResponse>

    @POST("absensi-hadir")
    suspend fun absensiHadir(
        @Header("Authorization") token: String,
        @Body data: AbsenRequest
        ): Response<AttendanceResponse>


    @Multipart
    @POST("upload-bukti-absensi/{id_absensi}")
    suspend fun uploadBuktiAbsensi(
        @Header("Authorization") token: String,
        @Path("id_absensi") int: Int? = null,
        @Part data: MultipartBody.Part? = null,
    ): Response<AttendanceResponse>

    @POST("absensi-pulang")
    suspend fun absensiPulang(
        @Header("Authorization") token: String,
        @Body data: absensiPulangRequest
    ):Response<AttendanceResponse>

    @POST("pengajuan-izin")
    suspend fun pengajuanIzin(
        @Header("Authorization") token: String,
        @Body data: PengajuanIzinRequest
    ):Response<PengajuanIzinResponse>

    @Multipart
    @POST("upload-pengajuan-izin/{id_pengajuan_izin}")
    suspend fun uploadSuratPengajuanIzin(
        @Header("Authorization") token: String,
        @Path("id_pengajuan_izin") int: Int? = null,
        @Part data: MultipartBody.Part? = null,
    ): Response<PengajuanIzinResponse>

    @POST("laporkan-absensi")
    suspend fun laporkanAbsensi(
        @Header("Authorization") token: String,
        @Body data: LaporkanAbsensiRequest
    ):Response<LaporkanAbsensiResponse>

    @GET("riwayat-laporan-absensi")
    suspend fun riwayatLaporanAbsensi(
        @Header("Authorization") token: String,
    ): Response<RiwayatLaporanAbsensiResponse>

    @GET("data-absensi")
    suspend fun dataAbsensi(
        @Header("Authorization") token: String,
    ): Response<DataAbsensiResponse>
}