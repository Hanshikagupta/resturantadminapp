package com.example.blank.adapter


import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.blank.PendingOrder
import com.example.blank.databinding.PendingOrdersItemBinding


class PendingOrderAdapter(
    private val context: Context,
    private val customerNames: MutableList<String>,
    private val quantity: MutableList<String>,
    private val foodImage: MutableList<String>,

    private val itemClicked: OnItemClicked
) : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {

    interface OnItemClicked{
        fun onItemClickedListener(position: Int)
        fun onItemAcceptClickedListener(position: Int)
        fun onItemDispatchClickedListener(position: Int)

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PendingOrderAdapter.PendingOrderViewHolder {
        val binding =
            PendingOrdersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendingOrderViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PendingOrderAdapter.PendingOrderViewHolder,
        position: Int
    ) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = customerNames.size
    inner class PendingOrderViewHolder(private val binding: PendingOrdersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false
        fun bind(position: Int) {

            binding.apply {
                customerName.text = customerNames[position]
                pendingOrderQuantity.text = quantity[position]
                val uriString = foodImage[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(orderedFoodImage)
                orderedAcceptButton.apply {
                    if (!isAccepted) {
                        text = "Accept"

                    } else {
                        text = "Dispatch"
                    }
                    setOnClickListener {
                        if (!isAccepted) {
                            text = "Dispatch"
                            isAccepted = true
                            showToast("Order is Accepted")
                            itemClicked.onItemAcceptClickedListener(position)


                        } else {
                            customerNames.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showToast("Order is Dispatched")
                            itemClicked.onItemDispatchClickedListener(position)

                        }

                    }
                }
                itemView.setOnClickListener{
                    itemClicked.onItemClickedListener(position)
                }
            }

        }

        private fun showToast(message: String) {
            //  Toast.makeText(context, message,Toast.LENGTH_SHORT).show()
        }

    }
}