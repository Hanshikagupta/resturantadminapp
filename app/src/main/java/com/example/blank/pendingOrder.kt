package com.example.blank

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blank.adapter.PendingOrderAdapter
import com.example.blank.databinding.ActivityPendingOrderBinding

class pendingOrder : AppCompatActivity() {
    private lateinit var binding: ActivityPendingOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.outbackbutton.setOnClickListener {
            finish()

        }


        val orderedcustomerName = arrayListOf(

            "Jhon d",
            "dj",
            "eded",
        )
        val orderedQuantity = arrayListOf( "8",
            "6",
            "5",
            )
        val orderedFoodImage = arrayListOf(R.drawable.img,R.drawable.img_3,R.drawable.img_1,)
        val adapter = PendingOrderAdapter(orderedQuantity, orderedFoodImage,orderedcustomerName,this)
        binding.pendingOrderRecyclerView.adapter = adapter
        binding.pendingOrderRecyclerView.layoutManager = LinearLayoutManager(this)
    }

}