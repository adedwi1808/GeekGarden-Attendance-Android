package com.example.geekgarden_attendance.core.data.source.remote.request

data class CompleteAttendanceRequest(
    val id: Int? = null,
    val tempat_absensi_pulang: String? = null,
    val status_absensi_pulang: String? = null,
    val longitude_pulang: String? = null,
    val latitude_pulang: String? = null,
    val foto_absensi_pulang: String? = null
)
