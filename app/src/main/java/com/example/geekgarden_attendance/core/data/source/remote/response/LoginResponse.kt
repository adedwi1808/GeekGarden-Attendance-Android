package com.example.geekgarden_attendance.core.data.source.remote.response

import com.example.geekgarden_attendance.core.data.source.model.Pegawai

data class LoginResponse(
    val data: Pegawai? = null,
    val token: String? = null
)
