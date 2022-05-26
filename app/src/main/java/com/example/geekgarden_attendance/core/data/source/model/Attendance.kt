package com.example.geekgarden_attendance.core.data.source.model

data class Attendance(
    val id: Int? = 0,
    val id_user: Int,
    val tempat_absensi_datang: String? = null,
    val status_absensi_datang: String? = null,
    val longitude_datang: String? = null,
    val latitude_datang: String? = null,
    val foto_absensi_datang: String? = null
)