package com.example.geekgarden_attendance.core.data.source.remote.request

data class UpdateProfileRequest(
    val id: Int,
    val name: String,
    val email: String,
    val phoneNumber: Int,
)
