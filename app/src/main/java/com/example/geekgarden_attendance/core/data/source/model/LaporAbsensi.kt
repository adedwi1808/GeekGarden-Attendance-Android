package com.example.geekgarden_attendance.core.data.source.model

data class LaporAbsensi(
    val id_laporan_absensi: Int? = null,
    val id_pegawai: String? = null,
    val id_admin: String? = null,
    val tanggal_absen: String? = null,
    val keterangan_laporan: String? = null,
    val tanggal_laporan: String? = null,
    val status_laporan: String? = null,
    val admin: Admin? = null
)