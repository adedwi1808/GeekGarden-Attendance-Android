package com.example.geekgarden_attendance.core.data.source.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Attendance(
    val id: Int? = 0,
    val id_user: Int,
    val tempat_absensi_datang: String? = null,
    val status_absensi_datang: String? = null,
    val longitude_datang: String? = null,
    val latitude_datang: String? = null,
    val foto_absensi_datang: String? = null,
    val tanggal_absensi_datang: String? = null,
    val tempat_absensi_pulang: String? = null,
    val status_absensi_pulang: String? = null,
    val longitude_pulang: String? = null,
    val latitude_pulang: String? = null,
    val foto_absensi_pulang: String? = null,
    val tanggal_absensi_pulang: String? = null
)