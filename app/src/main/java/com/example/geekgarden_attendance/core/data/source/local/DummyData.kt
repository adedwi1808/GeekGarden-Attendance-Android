package com.example.geekgarden_attendance.core.data.source.local

import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.core.data.source.model.AttendanceStats
import com.example.geekgarden_attendance.core.data.source.model.MadingGeekGarden

object DummyData {
    val listMadingGeekGarden = listOf(
        MadingGeekGarden(id = 1, judulMading = "Liburan Telah Tiba", bodyMading =
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum porttitor purus in felis bibendum elementum. Vestibulum tincidunt vehicula nisi vitae vehicula. Vestibulum lobortis posuere felis eu efficitur. Phasellus in ornare lacus. Phasellus ornare vulputate urna, sit amet convallis mi maximus et. Quisque maximus ac mauris quis aliquet. Cras id augue posuere, mollis nunc nec, porttitor nulla. Aenean luctus, massa ut gravida tempor, mi mi efficitur nulla, vitae volutpat tortor felis nec lorem. Phasellus gravida orci at ligula luctus, in gravida turpis sodales."
            , fotoMading = R.drawable.rraccoon, tanggalMading = "22 Mei 2022"),
        MadingGeekGarden(id = 2, judulMading = "GeekGeekGeekGeekGeekGeekGeekGeekGeek", bodyMading = "Geeeeeek", fotoMading = R.drawable.rraccoon, tanggalMading = "22 Mei 2022"),
    )

    val attendanceStatsDummy = AttendanceStats(id = 1, hadir = 20, izin = 2, sakit = 1, alpha = 5)
}