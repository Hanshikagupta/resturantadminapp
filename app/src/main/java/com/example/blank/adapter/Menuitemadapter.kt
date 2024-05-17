package com.example.blank.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.blank.databinding.ItemBinding
import com.example.blank.model.allmenu
import com.google.firebase.database.DatabaseReference


class Menuitemadapter(
    private val context: Context,
    private val menuList: ArrayList<allmenu>,
    databaseReference: DatabaseReference
): RecyclerView.Adapter<Menuitemadapter.AddItemViewHolder>() {
    private val itemQuantities =IntArray(menuList.size){1}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
       val binding=ItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }



    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
holder.bind(position)
    }

    override fun getItemCount(): Int =menuList.size
    inner  class AddItemViewHolder(private val binding: ItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
        binding.apply {
            val quantity =itemQuantities[position]
            val menuItem= menuList[position]
            val uriString =menuItem.foodImage
            val uri =Uri.parse(uriString)
            foodname.text=menuItem.foodName
            pendingOrderQuantity.text=menuItem.foodPrice
            Glide.with(context).load(uri).into(cartimage)

            foodItemQuantity.text=quantity.toString()
            minusbutton.setOnClickListener {
                decreaseQuantity(position)
            }
            plusbutton.setOnClickListener {
                increaseQuantity(position)
            }
            delete.setOnClickListener {
                deleteQuantity(position)
            }
        }
        }

        private fun decreaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                binding.foodItemQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                binding.foodItemQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun deleteQuantity(position: Int){
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,menuList.size)

        }

    }

}