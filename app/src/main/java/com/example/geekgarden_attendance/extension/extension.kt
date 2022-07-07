package com.example.geekgarden_attendance.extension

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun File?.toMultipartBody(name: String = "image"): MultipartBody.Part? {
    if (this == null) return null
    val reqFile: RequestBody = this.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("image", this.name, reqFile)
}

