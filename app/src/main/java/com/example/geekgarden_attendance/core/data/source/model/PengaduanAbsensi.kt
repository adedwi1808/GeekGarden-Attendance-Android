package com.example.geekgarden_attendance.core.data.source.model

data class PengaduanAbsensi(
    val id_pengaduan_absensi: Int? = null,
    val id_pegawai: String? = null,
    val id_admin: String? = null,
    val tanggal_absen: String? = null,
    val keterangan_pengaduan: String? = null,
    val tanggal_pengaduan: String? = null,
    val status_pengaduan: String? = null,
    val admin: Admin? = null
)