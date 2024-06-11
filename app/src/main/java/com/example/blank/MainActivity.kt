package com.example.blank

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.blank.databinding.ActivityMainBinding
import com.example.blank.model.OrderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var completeOrderedReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        binding.addmenuconstrain.setOnClickListener {
            val intent = Intent(this, additem::class.java)
            startActivity(intent)
        }
        binding.additemmenu.setOnClickListener {
            val intent = Intent(this, allitemactivity::class.java)
            startActivity(intent)
        }
        binding.outfordeliverybutton.setOnClickListener {
            val intent = Intent(this, outfordelivery::class.java)
            startActivity(intent)
        }
        binding.profile.setOnClickListener {
            val intent = Intent(this, adminProfile::class.java)
            startActivity(intent)
        }
        binding.createUser.setOnClickListener {
            val intent = Intent(this, CreateUser::class.java)
            startActivity(intent)
        }

        binding.pendingOrderTextView.setOnClickListener {
            val intent = Intent(this, PendingOrder::class.java)
            startActivity(intent)

        }
        binding.logout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this,signup::class.java))
            finish()
        }
        pendingOrders()
        completedOrders()
        wholeTimeEarning()
    }

    private fun wholeTimeEarning() {
        var listOfTotalPay = mutableListOf<Int>()
        completeOrderedReference=FirebaseDatabase.getInstance().reference.child("CompletedOrder")
        completeOrderedReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapshot in snapshot.children)
                {
                    var completeOrder=orderSnapshot.getValue(OrderDetails::class.java)
                    completeOrder?.totalPrice?.replace("Rs.","")?.toIntOrNull()
                        ?.let { i->
                            listOfTotalPay.add(i)
                        }
                }


                binding.wholTimeEarning.text = listOfTotalPay.sum().toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun completedOrders() {

        val completedOrderReference = database.reference.child("CompletedOrder")
        var completedOrderItemCount = 0
        completedOrderReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                completedOrderItemCount = snapshot.childrenCount.toInt()
                binding.completeorders.text = completedOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun pendingOrders() {
        database = FirebaseDatabase.getInstance()
        val pendingOrderReference = database.reference.child("OrderDetails")
        var pendingOrderItemCount = 0
        pendingOrderReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                
                pendingOrderItemCount = snapshot.childrenCount.toInt()
                binding.pendingorders.text = pendingOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}


