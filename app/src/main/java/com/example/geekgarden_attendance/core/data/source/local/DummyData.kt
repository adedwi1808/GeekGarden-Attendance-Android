package com.example.geekgarden_attendance.core.data.source.local

import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.core.data.source.model.OtherMoreButton

object DummyData {
    val listOtherMoreButton = listOf(
    OtherMoreButton(id = 0, icon = R.drawable.ic_baseline_pengajuan_izin, namaButton = "Pengajuan Izin"),
    OtherMoreButton(id = 1, icon = R.drawable.ic_baseline_laporkan_absensi, namaButton = "Pengaduan Absensi"),
    OtherMoreButton(id = 2, icon = R.drawable.ic_baseline_report_problem_24, namaButton = "Status Pengaduan"),
    OtherMoreButton(id = 3, icon = R.drawable.ic_baseline_status_laporan, namaButton = "Status Pengajuan Izin")
    )
}