package com.example.geekgarden_attendance.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.geekgarden_attendance.core.data.repository.AppRepository
import com.example.geekgarden_attendance.core.data.source.remote.request.AbsenRequest
import com.example.geekgarden_attendance.core.data.source.remote.request.absensiPulangRequest
import com.example.geekgarden_attendance.core.data.source.remote.request.AdukanAbsensiRequest
import com.example.geekgarden_attendance.core.data.source.remote.request.PengajuanIzinRequest
import okhttp3.MultipartBody


class NavigationViewModel(private val repository: AppRepository) : ViewModel() {

    fun absensiHadir(data: AbsenRequest) = repository.doAttendance(data).asLiveData()

    fun uploadAttendanceImage(id: Int? = null, fileImage: MultipartBody.Part? = null) = repository.uploadAttendanceImage(id, fileImage).asLiveData()

    fun uploadSuratIzin(id: Int? = null, img: MultipartBody.Part? = null) = repository.uploadWorkPermitApplicationLetter(id, img).asLiveData()

    fun absensiPulang(data: absensiPulangRequest) = repository.completeAttendance(data).asLiveData()

    fun pengajuanIzin(data: PengajuanIzinRequest) = repository.WorkPermit(data).asLiveData()

    fun adukanAbsensi(data: AdukanAbsensiRequest) = repository.AdukanAbsensi(data).asLiveData()

    fun checkAbsensi() = repository.checkAbsensi().asLiveData()

    fun riwayatAbsensi() = repository.riwayatAbsensi().asLiveData()
}