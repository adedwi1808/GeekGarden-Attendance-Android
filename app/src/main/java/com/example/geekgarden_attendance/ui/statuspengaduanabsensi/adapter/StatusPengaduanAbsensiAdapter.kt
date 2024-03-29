package com.example.geekgarden_attendance.ui.history.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.geekgarden_attendance.core.data.source.model.PengaduanAbsensi
import com.example.geekgarden_attendance.databinding.ItemStatusPengaduanAbsensiBinding
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("NotifyDataSetChanged")
class StatusPengaduanAbsensiAdapter: RecyclerView.Adapter<StatusPengaduanAbsensiAdapter.ViewHolder>() {

    private var data = mutableListOf<PengaduanAbsensi>()

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    inner class ViewHolder(private val itemBinding: ItemStatusPengaduanAbsensiBinding, listener: onItemClickListener): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(item: PengaduanAbsensi, position: Int){
            itemBinding.apply {
                textViewTanggalPengaduan.text = tanggalFormat(item.tanggal_pengaduan)
                textViewHariPengaduan.text = hariFormat(item.tanggal_pengaduan)
                textViewTanggalAbsen.text = tanggalAbsenFormat(item.tanggal_absen)
                textViewStatusPengaduan.text = item.status_pengaduan
                textViewKonfirmator.text = item.admin?.nama ?: "-"
                when(item.status_pengaduan){
                    "Diterima"->
                        bannerStatus.setCardBackgroundColor(Color.parseColor("#23CF92"))
                    "Ditolak"->
                        bannerStatus.setCardBackgroundColor(Color.parseColor("#FF6359"))
                    else->
                        bannerStatus.setCardBackgroundColor(Color.parseColor("#FFBD59"))
                }

                if (position%2 == 1){
                    cardMain.setCardBackgroundColor(Color.rgb(104,121,157))
                    textViewKonfirmator.setTextColor(Color.WHITE)
                    textViewTanggalAbsen.setTextColor(Color.WHITE)
                }else{
                    cardMain.setCardBackgroundColor(Color.rgb(244,248,255))
                }
            }
        }

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    fun addItems(item: List<PengaduanAbsensi>){
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
            ItemStatusPengaduanAbsensiBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false), mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}