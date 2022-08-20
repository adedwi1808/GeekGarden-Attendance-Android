package com.example.geekgarden_attendance.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.geekgarden_attendance.core.data.source.model.OtherMoreButton
import com.example.geekgarden_attendance.databinding.ItemLayananLainnyaBinding

@SuppressLint("NotifyDataSetChanged")
class OtherMoreButtonAdapter: RecyclerView.Adapter<OtherMoreButtonAdapter.ViewHolder>() {

    private var data = ArrayList<OtherMoreButton>()
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener
    }

    inner class ViewHolder(private val itemBinding: ItemLayananLainnyaBinding, listener: onItemClickListener): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(item: OtherMoreButton, position: Int){
            itemBinding.apply {
                textViewNamaButton.text = item.namaButton
                imageViewIcon.setImageResource(item.icon)
                if (position == 3){
                    bottomBorder.isVisible = false
                }
            }
        }
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    fun addItems(item: List<OtherMoreButton>){
            data.addAll(item)
            notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayananLainnyaBinding.inflate(
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