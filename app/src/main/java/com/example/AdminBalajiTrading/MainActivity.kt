package com.example.AdminBalajiTrading

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.AdminBalajiTrading.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.addMenu.setOnClickListener{
            val intent=Intent(this,AddItemActivity::class.java)
            startActivity(intent)

        }
        binding.allItemMenu.setOnClickListener{
            val intent=Intent(this,AllItemActivity::class.java)
            startActivity(intent)

        }
        binding.outForDeliveryButton.setOnClickListener{
            val intent=Intent(this,OutForDeliveryActivity::class.java)
            startActivity(intent)

        }
        binding.profile.setOnClickListener{
            val intent=Intent(this,AdminProfileActivity::class.java)
            startActivity(intent)

        }
        binding.createUser.setOnClickListener{
            val intent=Intent(this,CreateUserActivity::class.java)
            startActivity(intent)

        }
        binding.pendingOrderTextView.setOnClickListener {
            val intent=Intent(this,PendingOrderActivity::class.java)
            startActivity(intent)
        }
    }
}