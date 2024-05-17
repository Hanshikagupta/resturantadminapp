package com.example.blank

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blank.databinding.ActivityAdminProfileBinding

class adminProfile : AppCompatActivity() {
    private val binding : ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.outbackbutton.setOnClickListener {
            finish()
        }
        binding.nameadmin.isEnabled = false
        binding.address.isEnabled = false
        binding.phone.isEnabled = false
        binding.password.isEnabled = false
        binding.email.isEnabled = false

var isEnable = false
        binding.editButton.setOnClickListener {
            isEnable =! isEnable
            binding.nameadmin.isEnabled =  isEnable
            binding.address.isEnabled = isEnable
            binding.phone.isEnabled =  isEnable
            binding.password.isEnabled =  isEnable
            binding.email.isEnabled =  isEnable
            if (isEnable)
                binding.nameadmin.requestFocus()
        }

    }
}