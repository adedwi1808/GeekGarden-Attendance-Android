package com.example.geekgarden_attendance.ui.history.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.geekgarden_attendance.core.data.source.model.LaporanAbsensi
import com.example.geekgarden_attendance.databinding.ItemStatusLaporanAbsensiBinding
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("NotifyDataSetChanged")
class StatusLaporanAbsensiAdapter: RecyclerView.Adapter<StatusLaporanAbsensiAdapter.ViewHolder>() {

    private var data = mutableListOf<LaporanAbsensi>()

    inner class ViewHolder(private val itemBinding: ItemStatusLaporanAbsensiBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(item: LaporanAbsensi, position: Int){
            itemBinding.apply {
                textViewTanggalLaporan.text = tanggalFormat(item.tanggal_laporan)
                textViewHariLaporan.text = hariFormat(item.tanggal_laporan)
                textViewTanggalAbsen.text = tanggalAbsenFormat(item.tanggal_absen)
                textViewStatusLaporan.text = item.status_laporan
                textViewKonfirmator.text = item.admin?.nama ?: "-"
                if (position%2 == 1){
                    cardMain.setCardBackgroundColor(Color.rgb(104,121,157))
                    textViewStatusLaporan.setTextColor(Color.WHITE)
                    textViewKonfirmator.setTextColor(Color.WHITE)
                    textViewTanggalAbsen.setTextColor(Color.WHITE)
                }else{
                    cardMain.setCardBackgroundColor(Color.rgb(244,248,255))
                }
            }
        }
    }

    fun addItems(item: List<LaporanAbsensi>){
            data.clear()
            data.addAll(item)
            notifyDataSetChanged()
    }

    @SuppressLint("SimpleDateFormat")
    fun tanggalFormat(date: String?): String?{
        if(date == null) return null
        val myFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale("in", "ID")).parse(date)
        val formattedDatesString = myFormat?.let {
            SimpleDateFormat("dd", Locale("in", "ID")).format(
                it
            )
        }
        return formattedDatesString.toString()
    }


    @SuppressLint("SimpleDateFormat")
    fun tanggalAbsenFormat(date: String?): String?{
        if(date == null) return null
        val myFormat = SimpleDateFormat("yyyy-MM-dd", Locale("in", "ID")).parse(date)
        val formattedDatesString = myFormat?.let {
            SimpleDateFormat("dd/MM/yyyy", Locale("in", "ID")).format(
                it
            )
        }
        return formattedDatesString.toString()
    }

    @SuppressLint("SimpleDateFormat")
    fun hariFormat(date: String?): String?{
        if(date == null) return null
        val myFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale("in", "ID")).parse(date)
        val formattedDatesString = myFormat?.let {
            SimpleDateFormat("EEE", Locale("in", "ID")).format(
                it
            )
        }
        return formattedDatesString.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemStatusLaporanAbsensiBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}