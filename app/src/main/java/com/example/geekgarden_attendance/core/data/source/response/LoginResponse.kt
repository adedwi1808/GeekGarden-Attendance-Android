package com.example.geekgarden_attendance.core.data.source.response

import com.example.geekgarden_attendance.core.data.source.model.User

data class LoginResponse(
    val code: Int? = null,
    val message: String? = null,
    val data: User? = null
)
