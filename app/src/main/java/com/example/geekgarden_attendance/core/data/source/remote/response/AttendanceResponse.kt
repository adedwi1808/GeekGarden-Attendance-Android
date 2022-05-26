package com.example.geekgarden_attendance.core.data.source.remote.response

import com.example.geekgarden_attendance.core.data.source.model.Attendance
import com.example.geekgarden_attendance.core.data.source.model.User
import com.example.geekgarden_attendance.core.data.source.remote.request.AttendanceRequest

data class AttendanceResponse(
    val data: Attendance? = null,
)
