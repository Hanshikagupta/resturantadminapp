package com.example.blank


import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blank.adapter.OrderDetailsAdapter
import com.example.blank.databinding.ActivityOrderDetailsBinding
import com.example.blank.model.OrderDetails

class OrderDetailsActivity : AppCompatActivity() {
    private val binding:ActivityOrderDetailsBinding by lazy {
        ActivityOrderDetailsBinding.inflate(layoutInflater)
    }
    private var userName:String?=null
    private var tablenos: String?=null
    private var phoneNumber :String?=null
    private var totalPrice :String?=null
    private var foodName:ArrayList<String> = arrayListOf()
    private var foodImages:ArrayList<String> = arrayListOf()
    private var foodQuantity:ArrayList<Int> = arrayListOf()
    private var foodPrices: ArrayList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        binding.backbutton.setOnClickListener {
            finish()
        }
        getDataFromIntent()

    }

    private fun getDataFromIntent() {
        //receive details
        val receiveOrderDetails =intent.getSerializableExtra("UserOrderDetails")as OrderDetails
        receiveOrderDetails?.let { orderDetails->

            userName=receiveOrderDetails.userName
            foodName= receiveOrderDetails.foodName as ArrayList<String>
            foodImages=receiveOrderDetails.foodImages as ArrayList<String>
            foodQuantity=receiveOrderDetails.foodQuantities as ArrayList<Int>
            tablenos=receiveOrderDetails.tableno
            phoneNumber=receiveOrderDetails.phoneNumber
            foodPrices=receiveOrderDetails.foodPrices as ArrayList<String>
            totalPrice=receiveOrderDetails.totalPrice

            setUserDetails()
            setAdapter()

        }

    }



    private fun setUserDetails() {
        binding.name.text=userName
        binding.tableno.text=tablenos
        binding.phoneno.text=phoneNumber
        binding.total.text=totalPrice
    }
    private fun setAdapter() {
     binding.orderDetailsRecycler.layoutManager=LinearLayoutManager(this)
        val adapter=OrderDetailsAdapter(this,foodName,foodImages,foodQuantity,foodPrices)
        binding.orderDetailsRecycler.adapter=adapter
    }
}