package com.example.blank

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blank.adapter.PendingOrderAdapter
import com.example.blank.databinding.ActivityPendingOrderBinding
import com.example.blank.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PendingOrder : AppCompatActivity(), PendingOrderAdapter.OnItemClicked {
    private lateinit var binding: ActivityPendingOrderBinding
    private var listOfName: MutableList<String> = mutableListOf()
    private var listOfTotalPrice: MutableList<String> = mutableListOf()
    private var listOfImageFirstFoodOrder: MutableList<String> = mutableListOf()
    private var listOfOrderItem: ArrayList<OrderDetails> = arrayListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseOrderDetails: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initalize database
        database = FirebaseDatabase.getInstance()
        //initialize to database reference
        databaseOrderDetails = database.reference.child("OrderDetails")

        getOrderDetails()

        binding.outbackbutton.setOnClickListener {
            finish()

        }

    }

    private fun getOrderDetails() {
        //retrieve order details from firebase
        databaseOrderDetails.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapshot in snapshot.children) {
                    val orderDetails = orderSnapshot.getValue(OrderDetails::class.java)
                    orderDetails?.let {
                        listOfOrderItem.add(it)

                    }
                }
                addDataToListForRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun addDataToListForRecyclerView() {
        for (orderItem in listOfOrderItem) {
            //add data to respective list for populating the recycler view
            orderItem.userName?.let { listOfName.add(it) }
            orderItem.totalPrice?.let { listOfTotalPrice.add(it) }
            orderItem.foodImages?.filterNot { it.isEmpty() }?.forEach {
                listOfImageFirstFoodOrder.add(it)
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
        binding.pendingOrderRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter =
            PendingOrderAdapter(this, listOfName, listOfTotalPrice, listOfImageFirstFoodOrder, this)
        binding.pendingOrderRecyclerView.adapter = adapter
    }

    override fun onItemClickedListener(position: Int) {
        val intent = Intent(this, OrderDetailsActivity::class.java)
        val userOrderDetails = listOfOrderItem[position]
        intent.putExtra("UserOrderDetails", userOrderDetails)
        startActivity(intent)
    }

    override fun onItemAcceptClickedListener(position: Int) {
        //handleitem acceptence and update database
        val childItemPushKey = listOfOrderItem[position].itemPushKey
        val clickItemOrderReference = childItemPushKey?.let {
            database.reference.child("OrderDetails").child(it)
        }
        clickItemOrderReference?.child("orderAccepted")?.setValue(true)
        updateOrderAcceptedStatus(position)
    }


    override fun onItemDispatchClickedListener(position: Int) {
        //handle item dispatch and update database
        val dispatchItemPushKey=listOfOrderItem[position].itemPushKey
        val dispatchItemOrderReference=database.reference.child("CompletedOrder").child(dispatchItemPushKey!!)
        dispatchItemOrderReference.setValue(listOfOrderItem[position])
            .addOnSuccessListener {
                //deleteitem from orderdetails
                deleteItemFromOrderDetails(dispatchItemPushKey)
            }
    }

    private fun deleteItemFromOrderDetails(dispatchItemPushKey: String) {
        val orderDetailsItemReference=database.reference.child("OrderDetails").child(dispatchItemPushKey)
        orderDetailsItemReference.removeValue()
            .addOnSuccessListener {
                Toast.makeText(this,"Order Is Dispatched",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(this,"Order Is Not Dispatched",Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateOrderAcceptedStatus(position: Int) {
        //update order acceptance in user's buying history and order-details
        val userIdOfClicked = listOfOrderItem[position].userUid
        val pushKeyOfClickedItem = listOfOrderItem[position].itemPushKey
        val buyHistoryReference =
            database.reference.child("user").child(userIdOfClicked!!).child("BuyHistory")
                .child(pushKeyOfClickedItem!!)
        buyHistoryReference.child("orderAccepted").setValue(true)
        databaseOrderDetails.child(pushKeyOfClickedItem).child("orderAccepted").setValue(true)
    }

}