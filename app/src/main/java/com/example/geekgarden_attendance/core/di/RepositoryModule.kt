package com.example.geekgarden_attendance.core.di

import com.example.geekgarden_attendance.core.data.repository.AppRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AppRepository(get(), get()) }

}