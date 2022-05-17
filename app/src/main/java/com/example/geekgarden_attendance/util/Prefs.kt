package com.example.geekgarden_attendance.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.chibatching.kotpref.KotprefModel
import com.example.geekgarden_attendance.core.data.source.model.User
import com.google.gson.Gson

object Prefs: KotprefModel(){
    var isLogin by booleanPref(false)
    var user by stringPref()

    fun setUser(data: User?){
        val gson = Gson()
        user = gson.toJson(data)
    }

    fun getUser(): User? {
        if (user.isEmpty()) return null
        val gson = Gson()
        return gson.fromJson(user, User::class.java)
    }


}