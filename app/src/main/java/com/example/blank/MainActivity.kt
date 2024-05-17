package com.example.blank

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blank.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

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
            val intent = Intent(this, pendingOrder::class.java)
            startActivity(intent)

        }
    }
}


