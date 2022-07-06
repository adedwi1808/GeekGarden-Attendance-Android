package com.example.geekgarden_attendance.core.data.source.local

import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.core.data.source.model.AttendanceStats
import com.example.geekgarden_attendance.core.data.source.model.OtherMoreButton

object DummyData {
//    val listMadingGeekGarden = listOf(
//        MadingGeekGarden(id = 1, judul_mading = "Liburan Telah Tiba", body_mading =
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum porttitor purus in felis bibendum elementum. Vestibulum tincidunt vehicula nisi vitae vehicula. Vestibulum lobortis posuere felis eu efficitur. Phasellus in ornare lacus. Phasellus ornare vulputate urna, sit amet convallis mi maximus et. Quisque maximus ac mauris quis aliquet. Cras id augue posuere, mollis nunc nec, porttitor nulla. Aenean luctus, massa ut gravida tempor, mi mi efficitur nulla, vitae volutpat tortor felis nec lorem. Phasellus gravida orci at ligula luctus, in gravida turpis sodales."
//            , foto_mading = R.drawable.rraccoon, created_at = "22 Mei 2022"),
//        MadingGeekGarden(id = 2, judul_mading = "GeekGeekGeekGeekGeekGeekGeekGeekGeek", body_mading = "Geeeeeek", foto_mading = R.drawable.rraccoon, created_at = "22 Mei 2022"),
//    )
    val listOtherMoreButton = listOf(
    OtherMoreButton(id = 0, icon = R.drawable.ic_baseline_pengajuan_izin, namaButton = "Pengajuan Izin"),
    OtherMoreButton(id = 1, icon = R.drawable.ic_baseline_laporkan_absensi, namaButton = "Laporkan Absensi"),
    OtherMoreButton(id = 3, icon = R.drawable.ic_baseline_status_laporan, namaButton = "Status Laporan")
    )
    val attendanceStatsDummy = AttendanceStats(id = 1, hadir = 20, izin = 3, sakit = 7, alpha = 5)
}