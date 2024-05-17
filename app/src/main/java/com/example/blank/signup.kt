package com.example.blank

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import com.example.blank.databinding.ActivitySignupBinding
import com.example.blank.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class signup : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email :String
    private lateinit var password: String
    private lateinit var username :String
    private lateinit var nameOfResturant:String
    private lateinit var database : DatabaseReference
    private val binding: ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        //initialize firebase
        auth = Firebase.auth
        database=Firebase.database.reference



        binding.createaccount.setOnClickListener{

            email=binding.emailorphone.text.toString().trim()
            username=binding.name.text.toString().trim()
            nameOfResturant=binding.resturantname.text.toString().trim()
            password=binding.password.text.toString().trim()

            if (username.isBlank()||nameOfResturant.isBlank()||email.isBlank()||password.isBlank())
            {
                Toast.makeText(this,"Please fill all details", Toast.LENGTH_LONG).show()
            }
            else{
                createaccount(email,password)
            }


        }
        binding.alreadyhaveaccount.setOnClickListener{
            val intent =Intent(this,Login ::class.java)
            startActivity(intent)

        }

        val locationlist = arrayOf("Kanpur","Lucknow", "Allahabad")
        val adapter =ArrayAdapter(this,android.R.layout.simple_list_item_1,locationlist)
        val autoCompleteTextView= binding.listoflocation
        autoCompleteTextView.setAdapter(adapter)

        }

    private fun createaccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task->
            if(task.isSuccessful){
                Toast.makeText(this,"Account Created",Toast.LENGTH_SHORT).show()
               safeuserData()
                val intent =Intent(this,Login ::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"Account Not Created",Toast.LENGTH_SHORT).show()
                Log.d("Account","CreateAccount: Failure",task.exception)
            }
        }
    }
//save data into database
    private fun safeuserData() {
        email=binding.emailorphone.text.toString().trim()
        username=binding.name.text.toString().trim()
        nameOfResturant=binding.resturantname.text.toString().trim()
        password=binding.password.text.toString().trim()
       val user=UserModel(username,nameOfResturant,email,password)
    //save data to firebase
        val userId=FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)
    }
}
