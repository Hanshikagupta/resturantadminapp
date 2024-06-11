package com.example.blank.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.blank.databinding.OrderdetailsitemsBinding


class OrderDetailsAdapter(private val context: Context,
                          private var foodName:ArrayList<String> ,
                          private var foodImages:ArrayList<String> ,
                          private var foodQuantities:ArrayList<Int> ,
                          private var foodPrices: ArrayList<String>
):RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
val binding=OrderdetailsitemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderDetailsViewHolder(binding)
    }

    override fun getItemCount(): Int =foodName.size


    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
holder.bind(position)
    }
    inner class OrderDetailsViewHolder(private val binding: OrderdetailsitemsBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                foodname.text=foodName[position]
                foodQuantity.text=foodQuantities[position].toString()
                val uriString =foodImages[position]
                val uri =Uri.parse(uriString)
                Glide.with(context).load(uri).into(foodimage)
                foodPrice.text=foodPrices[position]
            }

        }

    }

}