package com.example.geekgarden_attendance.core.data.source.remote.request

data class UpdateProfileRequest(
    val id: Int,
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
)
