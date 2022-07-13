package com.example.geekgarden_attendance.core.data.source.model

data class CheckAbsensi(
    val jumlah_absen_hari_ini: Int?,
    val jam_hadir: Tanggal?,
    val jam_pulang: Tanggal?
)
data class Tanggal(
    val tanggal: String
)