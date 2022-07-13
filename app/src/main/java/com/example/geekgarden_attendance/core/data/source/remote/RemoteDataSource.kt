package com.example.geekgarden_attendance.core.data.source.remote

import com.example.geekgarden_attendance.core.data.source.remote.network.ApiService
import com.example.geekgarden_attendance.core.data.source.remote.request.AttendanceRequest
import com.example.geekgarden_attendance.core.data.source.remote.request.CompleteAttendanceRequest
import com.example.geekgarden_attendance.core.data.source.remote.request.LoginRequest
import com.example.geekgarden_attendance.core.data.source.remote.request.UpdateProfileRequest
import com.example.geekgarden_attendance.util.Prefs
import okhttp3.MultipartBody

class RemoteDataSource(private val api: ApiService) {

    //AUTH
    suspend fun login(data: LoginRequest) = api.login(data)

    suspend fun updateUser(data: UpdateProfileRequest) = api.updatePegawai("Bearer ${Prefs.getToken()}", data.id_pegawai, data)

    suspend fun uploadImage(id: Int? = null, fileImage: MultipartBody.Part? = null) = api.uploadImage("Bearer ${Prefs.getToken()}",id, fileImage)

    suspend fun uploadAttendanceImage(id: Int? = null, fileImage: MultipartBody.Part? = null) = api.uploadAttendanceImage("Bearer ${Prefs.getToken()}",id, fileImage)

    suspend fun selectAllMading() = api.selectAllMading("Bearer ${Prefs.getToken()}")

    suspend fun doAttendance(data: AttendanceRequest) = api.doAttendance("Bearer ${Prefs.getToken()}", data)

    suspend fun checkAbsensi() = api.checkAbsensi("Bearer ${Prefs.getToken()}")

    suspend fun completeAttendance(data: CompleteAttendanceRequest) = api.completeAttendance("Bearer ${Prefs.getToken()}", data)

}