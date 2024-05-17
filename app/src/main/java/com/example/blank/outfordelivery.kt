package com.example.blank

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blank.adapter.DeliveryAdapter
import com.example.blank.databinding.ActivityOutfordeliveryBinding

class outfordelivery : AppCompatActivity() {
    private val binding: ActivityOutfordeliveryBinding by lazy {
        ActivityOutfordeliveryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val customerName = arrayListOf(

            "Samosa",
            "Burger",
            "kabab",
        )
        val moneyStatus = arrayListOf("received","not received","pending",)
        val adapter =DeliveryAdapter(customerName,moneyStatus)
        binding.outfordeliveryrecycler.adapter= adapter
        binding.outfordeliveryrecycler.layoutManager=LinearLayoutManager(this)
        binding.outbackbutton.setOnClickListener{
            finish()
        }

        }
    }
