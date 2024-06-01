package com.example.blank

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blank.adapter.Menuitemadapter
import com.example.blank.databinding.ActivityAllitemactivityBinding
import com.example.blank.model.allmenu
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class allitemactivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private  var menuItems:ArrayList<allmenu> = ArrayList()
    private val binding: ActivityAllitemactivityBinding by lazy {
        ActivityAllitemactivityBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        databaseReference=FirebaseDatabase.getInstance().reference
        reteriveMenuItem()


        binding.backbutton.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        }

     private fun reteriveMenuItem() {
       database=FirebaseDatabase.getInstance()
       val foodref: DatabaseReference=database.reference.child("menu")
        //fetch data from database
        foodref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear exiisting data
                menuItems.clear()

                //loop for through each item
                for(foodSnapshot:DataSnapshot in snapshot.children)
                {
                    val menuItem =foodSnapshot.getValue(allmenu::class.java)
                    menuItem?.let{
                        menuItems.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Database Error", "Error: ${error.message}")
            }
        })

    }
    private fun setAdapter(){
        val adapter = Menuitemadapter(this@allitemactivity,menuItems,databaseReference)
        binding.menurecyclerview.layoutManager=LinearLayoutManager(this)
        binding.menurecyclerview.adapter=adapter
    }
}
