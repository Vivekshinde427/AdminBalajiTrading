package com.example.AdminBalajiTrading

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.AdminBalajiTrading.databinding.ActivityAddItemBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.net.URI
import java.net.URL

class AddItemActivity : AppCompatActivity() {

    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private lateinit var foodDescription: String
    private lateinit var foodIngrediant: String
    private var foodImageURI: Uri? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase


    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance()

        binding.AddItemButton.setOnClickListener{

            foodName = binding.foodName.text.toString().trim()
            foodPrice = binding.foodPrice.text.toString().trim()
            foodDescription = binding.description.text.toString().trim()
            foodIngrediant = binding.ingredient.text.toString().trim()

            if (!(foodName.isBlank()||foodPrice.isBlank()||foodDescription.isBlank()||foodIngrediant.isBlank())){
                uploadData()
                Toast.makeText(this,"Item Add Succesfully",Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this,"Fill all the details",Toast.LENGTH_SHORT).show()

            }

        }
        binding.selectImage.setOnClickListener{
            pickImage.launch("image/*")
        }


        binding.backButton.setOnClickListener {
            finish()
        }

    }

    private fun uploadData() {

        val menuRef =database.getReference("menu")
        val newItemKey = menuRef.push().key

        if (foodImageURI != null){
            val storageRef = Firebase
        }

    }

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            binding.selectedImage.setImageURI(uri)
        }
    }
}