package com.example.geekgarden_attendance.util

import com.chibatching.kotpref.KotprefModel
import com.example.geekgarden_attendance.core.data.source.model.Attendance
import com.example.geekgarden_attendance.core.data.source.model.AttendanceStats
import com.example.geekgarden_attendance.core.data.source.model.MadingGeekGarden
import com.example.geekgarden_attendance.core.data.source.model.Pegawai
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Prefs: KotprefModel(){
    var isLogin by booleanPref(false)
    var user by stringPref()
    var userToken by stringPref()
    var attendance by stringPref()
    var attendanceStats  by stringPref()
    var madings by stringPref()
    var userLongitude by stringPref("0.0")
    var userLatitude by stringPref("0.0")
    var userDidNotFinishAttendance by booleanPref(false)

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


    fun setToken(token: String){
        userToken= token
    }

    fun getToken(): String{
        return userToken
    }

    fun setUser(data: Pegawai?){
        val gson = Gson()
        user = gson.toJson(data)
    }

    fun getUser(): Pegawai? {
        if (user.isEmpty()) return null
        val gson = Gson()
        return gson.fromJson(user, Pegawai::class.java)
    }

    fun setAttendance(data: Attendance?){
        val gson = Gson()
        attendance = gson.toJson(data)
    }

    fun getAttendance(): Attendance? {
        if (attendance.isEmpty()) return null
        val gson = Gson()
        return gson.fromJson(attendance, Attendance::class.java)
    }

    fun setAttendanceStats(data: AttendanceStats?){
        val gson = Gson()
        attendanceStats = gson.toJson(data)
    }

    fun getAttendanceStats(): AttendanceStats? {
        if (attendanceStats.isEmpty()) return null
        val gson = Gson()
        return gson.fromJson(attendanceStats, AttendanceStats::class.java)
    }

}