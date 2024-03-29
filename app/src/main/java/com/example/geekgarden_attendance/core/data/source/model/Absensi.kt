package com.example.geekgarden_attendance.core.data.source.model

data class Absensi(
    val id_pegawai: Int,
    val id_absensi: Int,
    val tempat: String? = null,
    val status: String? = null,
    val longitude: String? = null,
    val latitude: String? = null,
    val foto: String? = null,
    val tanggal: String? = null
)