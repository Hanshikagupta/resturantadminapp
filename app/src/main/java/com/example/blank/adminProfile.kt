package com.example.blank

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.blank.databinding.ActivityAdminProfileBinding
import com.example.blank.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class adminProfile : AppCompatActivity() {
    private val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var adminReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        adminReference = database.reference.child("user")

        binding.outbackbutton.setOnClickListener {
            finish()
        }
        binding.saveInformation.setOnClickListener{
            updateUserData()
        }
        binding.nameadmin.isEnabled = false
        binding.address.isEnabled = false
        binding.phone.isEnabled = false
        binding.password.isEnabled = false
        binding.email.isEnabled = false
        binding.saveInformation.isEnabled = false

        var isEnable = false
        binding.editButton.setOnClickListener {
            isEnable = !isEnable
            binding.nameadmin.isEnabled = isEnable
            binding.address.isEnabled = isEnable
            binding.phone.isEnabled = isEnable
            binding.password.isEnabled = isEnable
            binding.email.isEnabled = isEnable
            if (isEnable){
                binding.nameadmin.requestFocus()
            }
            binding.saveInformation.isEnabled=isEnable
        }
        retrieveUserData()
    }



    private fun retrieveUserData() {
        val currentUserUid = auth.currentUser?.uid
        if (currentUserUid != null) {
            val userReference = adminReference.child(currentUserUid)
            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        var ownerName = snapshot.child("name").getValue()
                        var email = snapshot.child("email").getValue()
                        var password = snapshot.child("password").getValue()
                        var address = snapshot.child("nameOfResturante").getValue()
                        var phone = snapshot.child("phone").getValue()
                        setDataToTextView(ownerName, email, password, address, phone)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }

    }

    private fun setDataToTextView(
        ownerName: Any?,
        email: Any?,
        password: Any?,
        address: Any?,
        phone: Any?
    ) {
        binding.nameadmin.setText(ownerName.toString())
        binding.address.setText(address.toString())
        binding.email.setText(email.toString())
        binding.phone.setText(phone.toString())
        binding.password.setText(password.toString())

    }
    private fun updateUserData() {
       var updateName= binding.nameadmin.text.toString()
       var updateEmail= binding.email.text.toString()
       var updatePassword= binding.password.text.toString()
       var updatePhone= binding.phone.text.toString()
       var updateAddress= binding.address.text.toString()

        //set data
        var userData = UserModel(updateName,updateAddress,updateEmail,updatePassword,updatePhone)
        adminReference.setValue(userData).addOnSuccessListener {
            Toast.makeText(this,"Profile Updated",Toast.LENGTH_SHORT).show()
            auth.currentUser?.updateEmail(updateEmail)
            auth.currentUser?.updatePassword(updatePassword)
        }
    }
}