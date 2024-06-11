package com.example.blank

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blank.adapter.DeliveryAdapter
import com.example.blank.databinding.ActivityOutfordeliveryBinding
import com.example.blank.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class outfordelivery : AppCompatActivity() {
    private val binding: ActivityOutfordeliveryBinding by lazy {
        ActivityOutfordeliveryBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase
    private var listOfCompleteOrderList:ArrayList<OrderDetails> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.outbackbutton.setOnClickListener{
            finish()
        }
        //retrieve and dispatch completed order
        retrieveCompleteOrderDetails()


        }

    private fun retrieveCompleteOrderDetails() {
        //initialize firebase
        database=FirebaseDatabase.getInstance()
        val completeOrderReference= database.reference.child("CompletedOrder")
            .orderByChild("currentTime")
        completeOrderReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear listbefore populating it with new data
                listOfCompleteOrderList.clear()
                for (orderSnapshot in snapshot.children) {
                    val completeOrder= orderSnapshot.getValue(OrderDetails::class.java)
                    completeOrder?.let {
                        listOfCompleteOrderList.add(it)
                    }
                }
                listOfCompleteOrderList.reverse()
                setDataIntoRecyclerView()
            }


            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun setDataIntoRecyclerView() {
        //initial list to hold customer name and status
        val customerName= mutableListOf<String>()
        val moneyStatus= mutableListOf<Boolean>()

        for (order in listOfCompleteOrderList){
            order.userName?.let {
                customerName.add(it)
            }
            moneyStatus.add(order.paymentReceived)
        }
        val adapter =DeliveryAdapter(customerName,moneyStatus)

        binding.outfordeliveryrecycler.layoutManager=LinearLayoutManager(this)
        binding.outfordeliveryrecycler.adapter= adapter
    }
}
