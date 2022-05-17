package com.example.geekgarden_attendance.core.data.repository

import android.util.Log
import com.example.geekgarden_attendance.core.data.source.local.LocalDataSource
import com.example.geekgarden_attendance.core.data.source.remote.RemoteDataSource
import com.example.geekgarden_attendance.core.data.source.remote.network.Resource
import com.example.geekgarden_attendance.core.data.source.remote.request.LoginRequest
import com.example.geekgarden_attendance.util.Prefs
import kotlinx.coroutines.flow.flow
import org.json.JSONObject




class AppRepository(val local: LocalDataSource, val remote: RemoteDataSource) {
    fun login(data: LoginRequest) = flow {
        emit(Resource.loading(null))
        try {
            remote.login(data).let {
                if (it.isSuccessful){
                    Prefs.isLogin = true
                    val body = it.body()
                    val user = body?.data

                    Prefs.setUser(user)

                    emit(Resource.success(user))
                    Log.d("SUC", "Success : ${body.toString()}")
                }else{
                    val errJSON = JSONObject(it.errorBody()?.string())
                    emit(Resource.error(null, errJSON.getString("message") ?:"Failed to login"))
                    Log.d("ERR", "ERROR")
                }
            }
        }catch (err:Exception){
            emit(Resource.error(null, err.message ?: "Fail to login"))
            Log.d("ERR", "Login Err: ${err.message}")
        }
    }
}