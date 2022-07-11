package com.example.geekgarden_attendance.core.data.source.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Attendance(
    val id_pegawai: Int,
    val id_absensi: Int,
    val tempat: String? = null,
    val status: String? = null,
    val longitude: String? = null,
    val latitude: String? = null,
    val foto: String? = null
)