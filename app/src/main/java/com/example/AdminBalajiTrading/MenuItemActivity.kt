package com.example.AdminBalajiTrading

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.AdminBalajiTrading.databinding.ActivityAddItemBinding
import com.google.firebase.database.FirebaseDatabase

data class Product(
    val productName: String = "",
    val productPrice: String = "",
    val subcategory: String = ""
)

class AddItemActivity : AppCompatActivity() {
    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }

    private val categories = listOf("Cements", "Paints", "Pipes", "Tanks", "Other")
    private val subcategories = mapOf(
        "Cements" to listOf("ACC cements", "Ultratech cements", "Ambuja cements", "Other"),
        "Paints" to listOf("Asianpaints", "Indigo", "Berger", "Other"),
        "Pipes" to listOf("Supreme", "Ashirwad", "Finolex", "Ajay", "Other"),
        "Tanks" to listOf("Plasto", "Paras", "Tata", "Other"),
        "Other" to emptyList()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupSpinners()
        setupListeners()
    }

    private fun setupSpinners() {
        // Setup category spinner
        binding.categorySpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categories
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.categorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedCategory = categories[position]
                    binding.customCategoryInput.visibility =
                        if (selectedCategory == "Other") View.VISIBLE else View.GONE

                    val subcategoryList = subcategories[selectedCategory] ?: emptyList()
                    binding.subcategorySpinner.adapter = ArrayAdapter(
                        this@AddItemActivity,
                        android.R.layout.simple_spinner_item,
                        subcategoryList
                    ).apply {
                        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        // Setup subcategory spinner
        binding.subcategorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedSubcategory = binding.subcategorySpinner.selectedItem?.toString()
                    binding.customSubcategoryInput.visibility =
                        if (selectedSubcategory == "Other") View.VISIBLE else View.GONE
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun setupListeners() {
        binding.AddItemButton.setOnClickListener {
            val selectedCategory = binding.categorySpinner.selectedItem?.toString()
            val selectedSubcategory = binding.subcategorySpinner.selectedItem?.toString()
            val customCategory = binding.customCategoryInput.text.toString()
            val customSubcategory = binding.customSubcategoryInput.text.toString()
            val productName = binding.enterproductname.text.toString()
            val productPrice = binding.enterproductprice.text.toString()

            // Validation
            if (selectedCategory.isNullOrEmpty() || productName.isEmpty() || productPrice.isEmpty() ||
                (selectedCategory == "Other" && customCategory.isEmpty())
            ) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Determine final category and subcategory
            val categoryToSave =
                if (selectedCategory == "Other") customCategory else selectedCategory
            val subcategoryToSave = when {
                selectedSubcategory.isNullOrEmpty() -> "Default Subcategory"
                selectedSubcategory == "Other" && customSubcategory.isEmpty() -> {
                    Toast.makeText(this, "Please enter a custom subcategory", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                selectedSubcategory == "Other" -> customSubcategory
                else -> selectedSubcategory
            }

            // Create product object
            val product = Product(
                productName = productName,
                productPrice = productPrice,
                subcategory = subcategoryToSave
            )

            // Save to Firebase under the selected category
            val database = FirebaseDatabase.getInstance()
            val reference = database.getReference("products").child(categoryToSave)

            reference.push().setValue(product)
                .addOnSuccessListener {
                    Toast.makeText(this, "Product added successfully!", Toast.LENGTH_SHORT).show()
                    clearFields()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to add product: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun clearFields() {
        binding.enterproductname.text.clear()
        binding.enterproductprice.text.clear()
        binding.customCategoryInput.text.clear()
        binding.customSubcategoryInput.text.clear()
    }
}
