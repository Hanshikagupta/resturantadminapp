package com.example.blank

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import com.example.blank.databinding.ActivityLoginBinding
import com.example.blank.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email :String
    private lateinit var password: String


    private lateinit var database : DatabaseReference
    private val binding:ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        auth= Firebase.auth
        database=Firebase.database.reference
        binding.loginbutton.setOnClickListener{

            //get text from edit text
            email =binding.emaillogin.text.toString().trim()
            password=binding.passwordlogiin.text.toString().trim()

            if(email.isBlank()||password.isBlank()){
                Toast.makeText(this,"Please fill all detail",Toast.LENGTH_SHORT).show()
            }
            else
            {
                creteUserAccount(email,password)
            }
            val intent =Intent(this,signup::class.java)
            startActivity(intent)

        }
        binding.dontbutton.setOnClickListener{
            val intent =Intent(this,signup::class.java)
            startActivity(intent)

        }
    }

    private fun creteUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
            task-> if(task.isSuccessful){
                val user =auth.currentUser
            updateUi(user)
        }else{
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                task-> if(task.isSuccessful){
                val user =auth.currentUser
                saveUserData()
                updateUi(user)
                }else{
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
            }
        }
        }}

    private fun saveUserData() {
        email=binding.emaillogin.text.toString().trim()
        password=binding.passwordlogiin.text.toString().trim()
        val user =UserModel(email,password)
        val userId =FirebaseAuth.getInstance().currentUser?.uid
    userId.let {
        if (it != null) {
            database.child("user").child(it).setValue(user)
        }
    }}

    private fun updateUi(user: FirebaseUser?) {
        startActivity(Intent(this,MainActivity::class.java))
    }
}