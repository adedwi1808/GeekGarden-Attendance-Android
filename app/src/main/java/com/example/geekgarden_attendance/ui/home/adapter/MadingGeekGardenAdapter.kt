package com.example.geekgarden_attendance.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.geekgarden_attendance.R
import com.example.geekgarden_attendance.core.data.source.model.MadingGeekGarden
import com.example.geekgarden_attendance.databinding.ItemMadingGeekgardenBinding
import com.example.geekgarden_attendance.util.Constants
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("NotifyDataSetChanged")
class MadingGeekGardenAdapter: RecyclerView.Adapter<MadingGeekGardenAdapter.ViewHolder>() {

    private var data = ArrayList<MadingGeekGarden>()
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    inner class ViewHolder(private val itemBinding: ItemMadingGeekgardenBinding, listener: onItemClickListener): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(item: MadingGeekGarden, position: Int){
            itemBinding.apply {
                Picasso.get().load(Constants.MADING_URL + item.foto).fit().centerCrop()
                    .error(R.drawable.ic_baseline_image_not_supported_24)
                    .into(imageViewMading)

                TextViewJudulMading.text = item.judul
                TextViewBodyMading.text = item.informasi
                TextViewTanggalMading.text = dateFormat(item.create_at)
            }
        }

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    fun addItems(item: List<MadingGeekGarden>){
        if (data.isEmpty()) {
            data.addAll(item)
            notifyDataSetChanged()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun dateFormat(date: String?): String?{
        if(date == null) return null
        val myFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale("in", "ID")).parse(date)
        val formattedDatesString =
            myFormat?.let { SimpleDateFormat("EEE, dd MMM yyyy", Locale("in", "ID")).format(it) }
        return formattedDatesString.toString()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMadingGeekgardenBinding.inflate(
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