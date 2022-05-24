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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("NotifyDataSetChanged")
class MadingGeekGardenAdapter: RecyclerView.Adapter<MadingGeekGardenAdapter.ViewHolder>() {

    private var data = ArrayList<MadingGeekGarden>()

    inner class ViewHolder(private val itemBinding: ItemMadingGeekgardenBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(item: MadingGeekGarden, position: Int){
            itemBinding.apply {
                Picasso.get().load(Constants.MADING_URL + item.foto_mading).fit().centerCrop()
                    .error(R.drawable.ic_baseline_image_not_supported_24)
                    .into(imageViewMading)

                TextViewJudulMading.text = item.judul_mading
                TextViewBodyMading.text = item.body_mading
                TextViewTanggalMading.text = item.create_at?.substring(0,10)
            }
        }
    }

    fun addItems(item: List<MadingGeekGarden>){
        if (data.isEmpty()) {
            data.addAll(item)
            notifyDataSetChanged()
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMadingGeekgardenBinding.inflate(
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