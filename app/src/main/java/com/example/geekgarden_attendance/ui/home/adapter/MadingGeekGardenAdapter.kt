package com.example.geekgarden_attendance.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.geekgarden_attendance.core.data.source.model.MadingGeekGarden
import com.example.geekgarden_attendance.databinding.ItemMadingGeekgardenBinding

@SuppressLint("NotifyDataSetChanged")
class MadingGeekGardenAdapter: RecyclerView.Adapter<MadingGeekGardenAdapter.ViewHolder>() {

    private var data = ArrayList<MadingGeekGarden>()

    inner class ViewHolder(private val itemBinding: ItemMadingGeekgardenBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(item: MadingGeekGarden, position: Int){
            itemBinding.apply {
                imageViewMading.setImageResource(item.fotoMading)
                TextViewJudulMading.text = item.judulMading
                TextViewBodyMading.text = item.bodyMading
                TextViewTanggalMading.text = item.tanggalMading
            }
        }
    }

    fun addItems(items: List<MadingGeekGarden>){
        data.addAll(items)
        notifyDataSetChanged()
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