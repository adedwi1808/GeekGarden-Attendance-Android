package com.example.geekgarden_attendance.util

import android.app.Application
import com.chibatching.kotpref.Kotpref
import com.example.geekgarden_attendance.core.di.appModule
import com.example.geekgarden_attendance.core.di.repositoryModule
import com.example.geekgarden_attendance.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(appModule, viewModelModule, repositoryModule))
        }
    }
}