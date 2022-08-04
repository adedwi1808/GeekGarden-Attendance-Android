package com.example.geekgarden_attendance.core.data.source.remote.request

data class LaporkanAbsensiRequest(
    val tanggal_absen: String? = null,
    val keterangan_laporan: String? = null,
)
