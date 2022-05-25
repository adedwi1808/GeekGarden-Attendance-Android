package com.example.geekgarden_attendance.core.data.source.remote.request

data class AttendanceRequest(
    val id: Int,
    val tempat_absensi: String? = null,
    val status_absensi: String? = null,
    val longitude: String? = null,
    val latitude: String? = null,
    val foto_absensi: String? = null
)
