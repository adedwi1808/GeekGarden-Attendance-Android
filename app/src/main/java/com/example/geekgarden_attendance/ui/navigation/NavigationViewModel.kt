package com.example.geekgarden_attendance.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.geekgarden_attendance.core.data.repository.AppRepository
import com.example.geekgarden_attendance.core.data.source.remote.request.AttendanceRequest
import com.example.geekgarden_attendance.core.data.source.remote.request.CompleteAttendanceRequest
import com.example.geekgarden_attendance.core.data.source.remote.request.LaporkanAbsensiRequest
import com.example.geekgarden_attendance.core.data.source.remote.request.PengajuanIzinRequest
import okhttp3.MultipartBody


class NavigationViewModel(private val repository: AppRepository) : ViewModel() {

    fun doAttendance(data: AttendanceRequest) = repository.doAttendance(data).asLiveData()

    fun uploadAttendanceImage(id: Int? = null, fileImage: MultipartBody.Part? = null) = repository.uploadAttendanceImage(id, fileImage).asLiveData()

    fun uploadWorkPermitApplicationLetter(id: Int? = null, img: MultipartBody.Part? = null) = repository.uploadWorkPermitApplicationLetter(id, img).asLiveData()

    fun completeAttendance(data: CompleteAttendanceRequest) = repository.completeAttendance(data).asLiveData()

    fun workPermit(data: PengajuanIzinRequest) = repository.WorkPermit(data).asLiveData()


    fun laporkanAbsensi(data: LaporkanAbsensiRequest) = repository.LaporkanAbsensi(data).asLiveData()

    fun checkAbsensi() = repository.checkAbsensi().asLiveData()

    fun riwayatAbsensi() = repository.riwayatAbsensi().asLiveData()

}