package com.example.geekgarden_attendance.core.data.repository

import android.util.Log
import com.example.geekgarden_attendance.core.data.source.local.LocalDataSource
import com.example.geekgarden_attendance.core.data.source.model.LaporAbsensi
import com.example.geekgarden_attendance.core.data.source.remote.RemoteDataSource
import com.example.geekgarden_attendance.core.data.source.remote.network.Resource
import com.example.geekgarden_attendance.core.data.source.remote.request.*
import com.example.geekgarden_attendance.util.Prefs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.channelFlow
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

                    Prefs.setPegawai(user)
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

                    Prefs.setPegawai(user)
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

                    Prefs.setPegawai(user)
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

    @OptIn(ExperimentalCoroutinesApi::class)
    fun riwayatAbsensi() = channelFlow{
        send(Resource.loading(null))
        try {
            remote.riwayatAbsensi().let {
                if (it.isSuccessful){
                    val body = it.body()
                    val riwayatAbsensi = body?.data

                    Prefs.setRiwayatAbsensi(riwayatAbsensi)

                    async {send(Resource.success(riwayatAbsensi))}
                }else{
                    async {
                    val errJSON = JSONObject(it.errorBody()?.string())
                    send(Resource.error(null, errJSON.getString("message") ?:"Failed to update"))
                    }
                }
            }
        }catch (err:Exception){
            async {
            send(Resource.error(null, err.message ?: "Fail to get riwayat absensi"))
            }
        }
    }

    fun checkAbsensi() = flow {
        emit(Resource.loading(null))
        try {
            remote.checkAbsensi().let {
                if (it.isSuccessful){
                    val body = it.body()
                    Prefs.setCheckAbsensi(body?.data!!)
                    emit(Resource.success(body))
                }else{
                    val errJSON = JSONObject(it.errorBody()?.string())
                    emit(Resource.error(null, errJSON.getString("message") ?:"Failed to update"))
                }
            }
        }catch (err:Exception){
            emit(Resource.error(null, err.message ?: "Fail to login"))
        }
    }

    fun doAttendance(data: AttendanceRequest) = flow {
        emit(Resource.loading(null))
        try {
            remote.absensiHadir( data).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val attendance = body?.data

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
            remote.uploadBuktiAbsensi(id, fileImage).let {
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
            remote.absensiPulang(data).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val attendance = body?.data
                    Prefs.setAttendance(attendance)

                    Log.d("SUCC", Prefs.getAttendance().toString())
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

    fun WorkPermit(data: PengajuanIzinRequest) = flow {
        emit(Resource.loading(null))
        try {
            remote.pengajuanIzin(data).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val pengajuanIzin = body?.data
                    Prefs.setPengajuanIzin(pengajuanIzin)

                    Log.d("SUCC", body.toString())
                    emit(Resource.success(pengajuanIzin))
                }else{
                    val errJSON = JSONObject(it.errorBody()?.string())
                    emit(Resource.error(null, errJSON.getString("message") ?:"Failed to update"))
                }
            }
        }catch (err:Exception){
            emit(Resource.error(null, err.message ?: "Fail to login"))
        }
    }

    fun uploadWorkPermitApplicationLetter(id: Int? = null, img: MultipartBody.Part? = null) = flow {
        emit(Resource.loading(null))
        try {
            remote.uploadSuratPengajuanIzin(id, img).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val pengajuanIzin = body?.data
                    Prefs.setPengajuanIzin(pengajuanIzin)
                    emit(Resource.success(pengajuanIzin))
                }else{
                    val errJSON = JSONObject(it.errorBody()?.string())
                    emit(Resource.error(null, errJSON.getString("message") ?:"Failed to update"))
                }
            }
        }catch (err:Exception){
            emit(Resource.error(null, err.message ?: "Fail to login"))
        }
    }

    fun LaporkanAbsensi(data: LaporkanAbsensiRequest) = flow {
        emit(Resource.loading(null))
        try {
            remote.laporkanAbsensi(data).let {
                if (it.isSuccessful){
                    val body = it.body()
                    val laporkanAbsensi = body?.data

                    Log.d("SUCC", body.toString())
                    emit(Resource.success(laporkanAbsensi))
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