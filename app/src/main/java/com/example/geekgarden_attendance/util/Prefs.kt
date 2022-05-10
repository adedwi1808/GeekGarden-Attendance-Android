package com.example.geekgarden_attendance.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class Prefs(activity: Activity) {
    private var sp: SharedPreferences? = null

    init {
        sp = activity.getSharedPreferences("MYPREF", Context.MODE_PRIVATE)
    }

    val login = "login"
    fun setIsLogin(value: Boolean){
        sp!!.edit().putBoolean(login, value).apply()
    }

    fun getIsLogin(value: Boolean): Boolean{
        return sp!!.getBoolean(login, false)
    }
}