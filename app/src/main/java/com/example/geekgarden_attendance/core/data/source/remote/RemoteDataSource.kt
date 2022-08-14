package com.example.geekgarden_attendance.core.data.source.remote

import com.example.geekgarden_attendance.core.data.source.remote.network.ApiService
import com.example.geekgarden_attendance.core.data.source.remote.request.*
import com.example.geekgarden_attendance.util.Prefs
import okhttp3.MultipartBody

class RemoteDataSource(private val api: ApiService) {

    //AUTH
    suspend fun login(data: LoginRequest) = api.login(data)

    suspend fun lupaPassword(data: LupaPasswordRequest) = api.lupaPassword(data)

    suspend fun updateUser(data: UpdateProfileRequest) = api.updatePegawai("Bearer ${Prefs.getToken()}", data.id_pegawai, data)

    suspend fun uploadImage(id: Int? = null, fileImage: MultipartBody.Part? = null) = api.uploadImage("Bearer ${Prefs.getToken()}",id, fileImage)

    suspend fun uploadBuktiAbsensi(id: Int? = null, fileImage: MultipartBody.Part? = null) = api.uploadBuktiAbsensi("Bearer ${Prefs.getToken()}",id, fileImage)

    suspend fun selectAllMading() = api.selectAllMading("Bearer ${Prefs.getToken()}")

    suspend fun riwayatAbsensi() = api.riwayatAbsensi("Bearer ${Prefs.getToken()}")

    suspend fun riwayatLaporanAbsensi() = api.riwayatLaporanAbsensi("Bearer ${Prefs.getToken()}")

    suspend fun dataAbsensi() = api.dataAbsensi("Bearer ${Prefs.getToken()}")

    suspend fun absensiHadir(data: AbsenRequest) = api.absensiHadir("Bearer ${Prefs.getToken()}", data)

    suspend fun checkAbsensi() = api.checkAbsensi("Bearer ${Prefs.getToken()}")

    suspend fun absensiPulang(data: absensiPulangRequest) = api.absensiPulang("Bearer ${Prefs.getToken()}", data)

    suspend fun pengajuanIzin(data: PengajuanIzinRequest) = api.pengajuanIzin("Bearer ${Prefs.getToken()}", data)

    suspend fun laporkanAbsensi(data: LaporkanAbsensiRequest) = api.laporkanAbsensi("Bearer ${Prefs.getToken()}", data)

    suspend fun uploadSuratPengajuanIzin(id: Int? = null, img: MultipartBody.Part? = null) = api.uploadSuratPengajuanIzin("Bearer ${Prefs.getToken()}",id, img)


}