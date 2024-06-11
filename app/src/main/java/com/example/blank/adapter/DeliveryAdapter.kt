package com.example.blank.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blank.databinding.DeliveryitemBinding

class DeliveryAdapter(private val customerNames:MutableList<String>,private val moneyStatus:MutableList<Boolean>):RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
       val binding= DeliveryitemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DeliveryViewHolder(binding)
    }



    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)

    }
    override fun getItemCount(): Int =customerNames.size


    inner class DeliveryViewHolder(private val binding: DeliveryitemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
               customerName.text=customerNames[position]
                if (moneyStatus[position]==true){
                    money.text="Received"
                }else{
                    money.text="NotReceived"
                }

                val colorMap = mapOf(
                    true to Color.GREEN, false to Color.RED
                )
                money.setTextColor(colorMap[moneyStatus[position]]?:Color.BLACK)
                status.backgroundTintList= ColorStateList.valueOf(colorMap[moneyStatus[position]]?:Color.BLACK)
            }

        }

    }

}