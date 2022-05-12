package com.example.geekgarden_attendance.core.data.repository

import com.example.geekgarden_attendance.core.data.source.local.LocalDataSource
import com.example.geekgarden_attendance.core.data.source.remote.RemoteDataSource

class AppRepository(val local: LocalDataSource, val remote: RemoteDataSource) {

}