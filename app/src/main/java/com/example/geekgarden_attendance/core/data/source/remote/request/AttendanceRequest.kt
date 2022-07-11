package com.example.geekgarden_attendance.core.data.source.remote.request

data class AttendanceRequest(
    val id_pegawai: Int,
    val tempat: String? = null,
    val status: String? = null,
    val longitude: String? = null,
    val latitude: String? = null,
    val foto: String? = null
)
