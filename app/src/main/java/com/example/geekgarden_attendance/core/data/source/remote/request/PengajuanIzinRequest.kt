package com.example.geekgarden_attendance.core.data.source.remote.request

data class PengajuanIzinRequest(
    val jenis_izin: String? = null,
    val tanggal_mulai_izin: String? = null,
    val tanggal_selesai_izin: String? = null,
    val alasan_izin: String? = null,
    val status_izin: String? = null
)
