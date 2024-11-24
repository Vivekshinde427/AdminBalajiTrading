package com.example.AdminBalajiTrading

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.AdminBalajiTrading.adapter.AddItemAdapter
import com.example.AdminBalajiTrading.databinding.ActivityAllItemBinding
import com.example.AdminBalajiTrading.model.AllMenu
import com.google.firebase.database.*

class AllItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllItemBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var addItemAdapter: AddItemAdapter
    private val menuItems = mutableListOf<AllMenu>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase reference to the correct node
        databaseReference = FirebaseDatabase.getInstance().reference.child("products")

        setupRecyclerView()
        fetchProductsFromFirebase()
    }

    private fun setupRecyclerView() {
        addItemAdapter = AddItemAdapter(menuItems)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@AllItemActivity)
            adapter = addItemAdapter
        }
    }

    private fun fetchProductsFromFirebase() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Log the raw snapshot to see what's being returned from Firebase
                Log.d("AllItemActivity", "Raw snapshot: ${snapshot.value}")

                if (snapshot.exists()) {
                    menuItems.clear()

                    // Loop through each subcategory (Cements, Plasto, etc.)
                    for (subcategorySnapshot in snapshot.children) {
                        Log.d("AllItemActivity", "Subcategory snapshot: ${subcategorySnapshot.key}")

                        // Loop through each product in the subcategory
                        for (itemSnapshot in subcategorySnapshot.children) {
                            Log.d("AllItemActivity", "Item snapshot: ${itemSnapshot.value}")

                            try {
                                // Map the snapshot to the AllMenu object
                                val menuItem = itemSnapshot.getValue(AllMenu::class.java)
                                if (menuItem != null) {
                                    menuItems.add(menuItem)
                                    Log.d("AllItemActivity", "Parsed menu item: $menuItem")
                                } else {
                                    Log.e("AllItemActivity", "Menu item is null for snapshot: ${itemSnapshot.value}")
                                }
                            } catch (e: Exception) {
                                Log.e("AllItemActivity", "Error parsing item: ${e.message}")
                            }
                        }
                    }

                    // Notify the adapter to update the UI
                    if (menuItems.isNotEmpty()) {
                        addItemAdapter.updateMenuItems(menuItems)
                    } else {
                        Log.d("AllItemActivity", "No valid products found in Firebase")
                        Toast.makeText(this@AllItemActivity, "No products found.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("AllItemActivity", "No valid products found in Firebase")
                    Toast.makeText(this@AllItemActivity, "No products found.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("AllItemActivity", "Database error: ${error.message}")
                Toast.makeText(this@AllItemActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
