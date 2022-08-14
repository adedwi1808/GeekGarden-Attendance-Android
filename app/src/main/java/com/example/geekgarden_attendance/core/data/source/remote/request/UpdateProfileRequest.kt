package com.example.geekgarden_attendance.core.data.source.remote.request

data class UpdateProfileRequest(
    val id_pegawai: Int,
    val email: String? = null,
    val nomor_hp: String? = null,
    val password: String? = null,
)
