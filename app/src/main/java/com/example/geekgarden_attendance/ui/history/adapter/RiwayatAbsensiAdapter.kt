package com.example.geekgarden_attendance.ui.history.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.core.data.source.model.Absensi
import com.example.geekgarden_attendance.core.data.source.model.MadingGeekGarden
import com.example.geekgarden_attendance.databinding.ItemMadingGeekgardenBinding
import com.example.geekgarden_attendance.databinding.ItemRiwayatAbsensiBinding
import com.example.geekgarden_attendance.util.Constants
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("NotifyDataSetChanged")
class RiwayatAbsensiAdapter: RecyclerView.Adapter<RiwayatAbsensiAdapter.ViewHolder>() {

    private var data = ArrayList<Absensi>()

    inner class ViewHolder(private val itemBinding: ItemRiwayatAbsensiBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(item: Absensi, position: Int){
            itemBinding.apply {
                textViewStatusAbsen.text = item.status
                textViewLokasiAbsen.text = item.tempat
                textViewJamHadir.text = jamFormat(item.tanggal)
                textViewHariHadir.text = hariFormat(item.tanggal)
                textViewTanggalAbsen.text = tanggalFormat(item.tanggal)
                if (position%2 == 1){
                    cardMain.setCardBackgroundColor(Color.rgb(104,121,157))
                    textViewStatusAbsen.setTextColor(Color.WHITE)
                    textViewJamHadir.setTextColor(Color.WHITE)
                    textViewLokasiAbsen.setTextColor(Color.WHITE)
                }else{
                    cardMain.setCardBackgroundColor(Color.rgb(244,248,255))
                }
            }
        }
    }

    fun addItems(item: List<Absensi>){
        if (data.isEmpty()) {
            data.addAll(item)
            notifyDataSetChanged()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun tanggalFormat(date: String?): String?{
        if(date == null) return null
        val myFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale("in", "ID")).parse(date)
        val formattedDatesString = SimpleDateFormat("dd", Locale("in", "ID")).format(myFormat)
        return formattedDatesString.toString()
    }

    @SuppressLint("SimpleDateFormat")
    fun jamFormat(date: String?): String?{
        if(date == null) return null
        val myFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale("in", "ID")).parse(date)
        val formattedDatesString = SimpleDateFormat("h:mm", Locale("in", "ID")).format(myFormat)
        return formattedDatesString.toString()
    }

    @SuppressLint("SimpleDateFormat")
    fun hariFormat(date: String?): String?{
        if(date == null) return null
        val myFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale("in", "ID")).parse(date)
        val formattedDatesString = SimpleDateFormat("EEE", Locale("in", "ID")).format(myFormat)
        return formattedDatesString.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRiwayatAbsensiBinding.inflate(
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