package com.example.AdminBalajiTrading

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.AdminBalajiTrading.databinding.ActivityCreateUserBinding

class CreateUserActivity : AppCompatActivity() {
    private  val binding:ActivityCreateUserBinding by lazy {
        ActivityCreateUserBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.backButton.setOnClickListener{
            finish()

        }
    }
}