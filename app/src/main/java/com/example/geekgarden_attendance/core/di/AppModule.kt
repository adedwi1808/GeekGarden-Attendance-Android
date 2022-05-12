package com.example.geekgarden_attendance.core.di

import com.example.geekgarden_attendance.core.data.source.local.LocalDataSource
import com.example.geekgarden_attendance.core.data.source.remote.RemoteDataSource
import com.example.geekgarden_attendance.core.data.source.remote.network.ApiConfig
import org.koin.dsl.module

val appModule = module {
    single {ApiConfig.provideApiService}

    single {RemoteDataSource(get())}

    single {LocalDataSource()}
}