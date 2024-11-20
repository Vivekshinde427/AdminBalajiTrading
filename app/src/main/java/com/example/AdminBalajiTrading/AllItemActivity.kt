package com.example.AdminBalajiTrading

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.AdminBalajiTrading.adpater.AddItemAdapter
import com.example.AdminBalajiTrading.databinding.ActivityAllItemBinding

class AllItemActivity : AppCompatActivity() {
    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val menuProductName= listOf("Fertilizers","Pesticides","Urea","Corn","Pesticides","Urea")
        val menuItemPrice= listOf("₹5","₹6","₹7","₹5","₹6","₹7",)
        val menuImage= listOf(R.drawable.pipes,R.drawable.kocide_3000,R.drawable.kocide_3000,R.drawable.pipes,R.drawable.kocide_3000,R.drawable.pipes)
        binding.backButton.setOnClickListener {
            finish()
        }

        val adapter=AddItemAdapter(ArrayList(menuProductName),ArrayList(menuItemPrice),ArrayList(menuImage))

        binding.MenuRecyclerView.layoutManager=LinearLayoutManager(this)
        binding.MenuRecyclerView.adapter=adapter

        }
    }
