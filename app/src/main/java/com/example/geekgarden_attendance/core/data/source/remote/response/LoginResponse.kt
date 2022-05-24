package com.example.geekgarden_attendance.core.data.source.remote.response

import com.example.geekgarden_attendance.core.data.source.model.User

data class LoginResponse(
    val data: User? = null,
    val token: String? = null
)
