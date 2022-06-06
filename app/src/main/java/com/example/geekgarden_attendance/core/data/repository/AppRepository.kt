package com.example.geekgarden_attendance.core.data.repository

import android.util.Log
import com.example.geekgarden_attendance.core.data.source.local.LocalDataSource
import com.example.geekgarden_attendance.core.data.source.remote.RemoteDataSource
import com.example.geekgarden_attendance.core.data.source.remote.network.Resource
import com.example.geekgarden_attendance.core.data.source.remote.request.AttendanceRequest
import com.example.geekgarden_attendance.core.data.source.remote.request.CompleteAttendanceRequest
import com.example.geekgarden_attendance.core.data.source.remote.request.LoginRequest
import com.example.geekgarden_attendance.core.data.source.remote.request.UpdateProfileRequest
import com.example.geekgarden_attendance.util.Prefs
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
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
                    Prefs.setToken(body!!.token!!)

                    emit(Resource.success(user))
                }else{
                    val errJSON = JSONObject(it.errorBody()?.string())
                    emit(Resource.error(null, errJSON.getString("message") ?:"Failed to login"))
                }
            }
        }catch (err:Exception){
            emit(Resource.error(null, err.message ?: "Fail to login"))
            Log.d("ERR", "Login Err: ${err.message}")
        }
    }

    fun updateUser(data: UpdateProfileRequest) = flow {
        emit(Resource.loading(null))
        try {
            remote.updateUser(data).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data

                    Prefs.setUser(user)
                    emit(Resource.success(user))
                }else{
                    val errJSON = JSONObject(it.errorBody()?.string())
                    emit(Resource.error(null, errJSON.getString("message") ?:"Failed to update"))
                }
            }
        }catch (err:Exception){
            emit(Resource.error(null, err.message ?: "Fail to login"))
        }
    }

    fun uploadImage(id: Int? = null, fileImage: MultipartBody.Part? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.uploadImage(id, fileImage).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val user = body?.data

                    Prefs.setUser(user)
                    emit(Resource.success(user))
                }else{
                    val errJSON = JSONObject(it.errorBody()?.string())
                    emit(Resource.error(null, errJSON.getString("message") ?:"Failed to update"))
                }
            }
        }catch (err:Exception){
            emit(Resource.error(null, err.message ?: "Fail to login"))
        }
    }

    fun selectAllMading() = flow {
        emit(Resource.loading(null))
        try {
            remote.selectAllMading().let {
                if (it.isSuccessful){
                    val body = it.body()
                    val madings = body?.data
                    Prefs.setMading(madings)
                    emit(Resource.success(madings))
                }else{
                    val errJSON = JSONObject(it.errorBody()?.string())
                    emit(Resource.error(null, errJSON.getString("message") ?:"Failed to update"))
                }
            }
        }catch (err:Exception){
            emit(Resource.error(null, err.message ?: "Fail to login"))
        }
    }

    fun doAttendance(id : Int? = null, data: AttendanceRequest, fileImage: MultipartBody.Part? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.doAttendance(id, data, fileImage).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val attendance = body?.data

                    if (attendance != null) {
                    }


                    Prefs.setAttendance(attendance)
                    emit(Resource.success(attendance))
                }else{
                    val errJSON = JSONObject(it.errorBody()?.string())
                    emit(Resource.error(null, errJSON.getString("message") ?:"Failed to login"))
                }
            }
        }catch (err:Exception){
            emit(Resource.error(null, err.message ?: "Fail to login"))
            Log.d("ERR", "Login Err: ${err.message}")
        }
    }

    fun uploadAttendanceImage(id: Int? = null, fileImage: MultipartBody.Part? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.uploadAttendanceImage(id, fileImage).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val attendance = body?.data
                    Prefs.userDidNotFinishAttendance = true

                    Prefs.setAttendance(attendance)
                    emit(Resource.success(attendance))
                }else{
                    val errJSON = JSONObject(it.errorBody()?.string())
                    emit(Resource.error(null, errJSON.getString("message") ?:"Failed to update"))
                }
            }
        }catch (err:Exception){
            emit(Resource.error(null, err.message ?: "Fail to login"))
        }
    }

    fun completeAttendance(data: CompleteAttendanceRequest) = flow {
        emit(Resource.loading(null))
        try {
            remote.completeAttendance(data).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val attendance = body?.data
                    Log.d("Mantappp", Prefs.getAttendance().toString())
                    emit(Resource.success(attendance))
                }else{
                    val errJSON = JSONObject(it.errorBody()?.string())
                    emit(Resource.error(null, errJSON.getString("message") ?:"Failed to update"))
                }
            }
        }catch (err:Exception){
            emit(Resource.error(null, err.message ?: "Fail to login"))
        }
    }


    fun uploadCompleteAttendanceImage(id: Int? = null, fileImage: MultipartBody.Part? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.uploadCompleteAttendanceImage(id, fileImage).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val attendance = body?.data
                    Log.d("qwert", attendance.toString())

                    Prefs.userDidNotFinishAttendance = false
                    Prefs.setAttendance(attendance)
                    emit(Resource.success(attendance))
                }else{
                    val errJSON = JSONObject(it.errorBody()?.string())
                    emit(Resource.error(null, errJSON.getString("message") ?:"Failed to update"))
                }
            }
        }catch (err:Exception){
            emit(Resource.error(null, err.message ?: "Fail to login"))
        }
    }

}