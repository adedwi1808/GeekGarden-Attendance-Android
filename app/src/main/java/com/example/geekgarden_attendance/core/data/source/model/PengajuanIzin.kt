package com.example.geekgarden_attendance.core.data.source.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class PengajuanIzin(
    val id_pengajuan_izin: Int,
    val id_absensi: Int,
    val jenis_izin: String? = null,
    val tanggal_mulai_izin: String? = null,
    val tanggal_selesai_izin: String? = null,
    val alasan_izin: String? = null,
    val status_izin: String? = null,
    val surat_izin: String? = null
)