package com.example.geekgarden_attendance.util

import com.chibatching.kotpref.KotprefModel
import com.example.geekgarden_attendance.core.data.source.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Prefs: KotprefModel(){
    var isLogin by booleanPref(false)
    var user by stringPref()
    var userToken by stringPref()
    var attendance by stringPref()
    var pengajuanIzin by stringPref()
    var riwayatPengajuanIzin by stringPref()
    var dataAbsensi  by stringPref()
    var madings by stringPref()
    var pengaduanAbsensi by stringPref()
    var riwayatAbsensi by stringPref()
    var userLongitude by stringPref("0.0")
    var userLatitude by stringPref("0.0")
    var userDidNotFinishAttendance by booleanPref(false)
    var checkAbsensi by stringPref()

    fun setRiwayatPengajuanIzin(data: List<PengajuanIzin>?){
        val gson = Gson()
        riwayatPengajuanIzin = gson.toJson(data)
    }

    fun getRiwayatPengajuanIzin(): List<PengajuanIzin>? {
        if (riwayatPengajuanIzin.isEmpty()) return null
        val gson = Gson()
        val myType = object : TypeToken<List<PengajuanIzin>>() {}.type
        return gson.fromJson<List<PengajuanIzin>>(riwayatPengajuanIzin, myType)
    }

    fun setPengajuanIzin(data: PengajuanIzin?){
        val gson = Gson()
        pengajuanIzin = gson.toJson(data)
    }

    fun getPengajuanIzin(): PengajuanIzin? {
        if (pengajuanIzin.isEmpty()) return null
        val gson = Gson()
        return gson.fromJson(pengajuanIzin, PengajuanIzin::class.java)
    }

    fun setCheckAbsensi(data: CheckAbsensi?){
        val gson = Gson()
        checkAbsensi = gson.toJson(data)
    }

    fun getCheckAbsensi(): CheckAbsensi? {
        if (checkAbsensi.isEmpty()) return null
        val gson = Gson()
        return gson.fromJson(checkAbsensi, CheckAbsensi::class.java)
    }

    fun setLongitude(data: String){
        this.userLongitude = data
    }

    fun getLongitude(): String{
        return userLongitude
    }

    fun setLatitude(data: String){
        this.userLatitude = data
    }

    fun getLatitude(): String{
        return userLatitude
    }

    fun setMading(data: List<MadingGeekGarden>?){
        val gson = Gson()
        madings = gson.toJson(data)
    }

    fun getMading(): List<MadingGeekGarden>? {
        if (madings.isEmpty()) return null
        val gson = Gson()
        val myType = object : TypeToken<List<MadingGeekGarden>>() {}.type
        return gson.fromJson<List<MadingGeekGarden>>(madings, myType)
    }

    fun setPengaduanAbsensi(data: List<PengaduanAbsensi>?){
        val gson = Gson()
        pengaduanAbsensi = gson.toJson(data)
    }

    fun getPengaduanAbsensi(): List<PengaduanAbsensi>? {
        if (pengaduanAbsensi.isEmpty()) return null
        val gson = Gson()
        val myType = object : TypeToken<List<PengaduanAbsensi>>() {}.type
        return gson.fromJson<List<PengaduanAbsensi>>(pengaduanAbsensi, myType)
    }

    fun setRiwayatAbsensi(data: List<Absensi>?){
        val gson = Gson()
        riwayatAbsensi = gson.toJson(data)
    }

    fun getRiwayatAbsensi(): List<Absensi>? {
        if (riwayatAbsensi.isEmpty()) return null
        val gson = Gson()
        val myType = object : TypeToken<List<Absensi>>() {}.type
        return gson.fromJson<List<Absensi>>(riwayatAbsensi, myType)
    }

    fun setToken(token: String){
        userToken= token
    }

    fun getToken(): String{
        return userToken
    }

    fun setPegawai(data: Pegawai?){
        val gson = Gson()
        user = gson.toJson(data)
    }

    fun getPegawai(): Pegawai? {
        if (user.isEmpty()) return null
        val gson = Gson()
        return gson.fromJson(user, Pegawai::class.java)
    }

    fun setAttendance(data: Absensi?){
        val gson = Gson()
        attendance = gson.toJson(data)
    }

    fun getAttendance(): Absensi? {
        if (attendance.isEmpty()) return null
        val gson = Gson()
        return gson.fromJson(attendance, Absensi::class.java)
    }

    fun setDataAbsensi(data: DataAbsensi?){
        val gson = Gson()
        dataAbsensi = gson.toJson(data)
    }

    fun getDataAbsensi(): DataAbsensi? {
        if (dataAbsensi.isEmpty()) return null
        val gson = Gson()
        return gson.fromJson(dataAbsensi, DataAbsensi::class.java)
    }

}
