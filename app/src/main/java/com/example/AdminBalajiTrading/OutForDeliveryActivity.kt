package com.example.AdminBalajiTrading

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.AdminBalajiTrading.adpater.DeliveryAdapter
import com.example.AdminBalajiTrading.databinding.ActivityOutForDeliveryBinding

class OutForDeliveryActivity : AppCompatActivity() {
    private  val binding: ActivityOutForDeliveryBinding by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        val customerName= arrayListOf("John Doe","Jane Smith","Mike Jackson")

        val moneyStatus= arrayListOf("Received","Not Received","Pending")

        val adapter=DeliveryAdapter(customerName,moneyStatus)
        binding.deliveryRecyclerView.adapter=adapter
        binding.deliveryRecyclerView.layoutManager=LinearLayoutManager(this)

        }
    }
