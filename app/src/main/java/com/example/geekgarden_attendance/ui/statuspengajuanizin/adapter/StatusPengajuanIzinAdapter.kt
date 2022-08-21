package com.example.geekgarden_attendance.ui.history.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.geekgarden_attendance.core.data.source.model.PengajuanIzin
import com.example.geekgarden_attendance.databinding.ItemStatusPengajuanIzinBinding
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("NotifyDataSetChanged")
class StatusPengajuanIzinAdapter: RecyclerView.Adapter<StatusPengajuanIzinAdapter.ViewHolder>() {

    private var data = mutableListOf<PengajuanIzin>()

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    inner class ViewHolder(private val itemBinding: ItemStatusPengajuanIzinBinding, listener: onItemClickListener): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(item: PengajuanIzin, position: Int){
            itemBinding.apply {
                textViewTanggalPengajuan.text = tanggalFormat(item.tanggal_mengajukan_izin)
                textViewHariPengajuan.text = hariFormat(item.tanggal_mengajukan_izin)
                textViewTanggalMulai.text = tanggalIzin(item.tanggal_mulai_izin)
                textViewTanggalSelesai.text = tanggalIzin(item.tanggal_selesai_izin)
                textViewStatusPengajuan.text = item.status_izin
                textViewKonfirmator.text = item.admin?.nama ?: "-"
                textViewJenisIzin.text = item.jenis_izin
                when(item.status_izin){
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
                    textViewTanggalMulai.setTextColor(Color.WHITE)
                    textViewTanggalSelesai.setTextColor(Color.WHITE)
                    textViewJenisIzin.setTextColor(Color.WHITE)
                    iconSampaiDengan.setTextColor(Color.WHITE)
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

    fun addItems(item: List<PengajuanIzin>){
            data.clear()
            data.addAll(item)
            notifyDataSetChanged()
    }

    @SuppressLint("SimpleDateFormat")
    fun tanggalFormat(date: String?): String?{
        if(date == null) return null
        val myFormat = SimpleDateFormat("yyyy-MM-dd", Locale("in", "ID")).parse(date)
        val formattedDatesString = myFormat?.let {
            SimpleDateFormat("dd", Locale("in", "ID")).format(
                it
            )
        }
        return formattedDatesString.toString()
    }


    @SuppressLint("SimpleDateFormat")
    fun tanggalIzin(date: String?): String?{
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
        val myFormat = SimpleDateFormat("yyyy-MM-dd", Locale("in", "ID")).parse(date)
        val formattedDatesString = myFormat?.let {
            SimpleDateFormat("EEE", Locale("in", "ID")).format(
                it
            )
        }
        return formattedDatesString.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemStatusPengajuanIzinBinding.inflate(
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