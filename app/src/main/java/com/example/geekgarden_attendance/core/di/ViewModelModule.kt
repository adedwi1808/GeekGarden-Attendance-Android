package com.example.geekgarden_attendance.core.di

import com.example.geekgarden_attendance.ui.home.DetailMading.DetailMadingViewModel
import com.example.geekgarden_attendance.ui.login.LoginViewModel
import com.example.geekgarden_attendance.ui.login.LupaPasswordViewModel
import com.example.geekgarden_attendance.ui.navigation.NavigationViewModel
import com.example.geekgarden_attendance.ui.statuspengaduanabsensi.StatusPengaduanAbsensiViewModel
import com.example.geekgarden_attendance.ui.statuspengajuanizin.StatusPengajuanIzinViewModel
import com.example.geekgarden_attendance.ui.updateProfile.UpdateProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { UpdateProfileViewModel(get()) }
    viewModel { NavigationViewModel(get()) }
    viewModel { StatusPengaduanAbsensiViewModel(get()) }
    viewModel { StatusPengajuanIzinViewModel(get()) }
    viewModel { LupaPasswordViewModel(get()) }
    viewModel { DetailMadingViewModel(get()) }
}