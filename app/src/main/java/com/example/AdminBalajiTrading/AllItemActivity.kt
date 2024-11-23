package com.example.AdminBalajiTrading

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.AdminBalajiTrading.adapter.AddItemAdapter
import com.example.AdminBalajiTrading.model.AllMenu
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllItemActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addItemAdapter: AddItemAdapter
    private val menuItems = mutableListOf<AllMenu>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_item)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        addItemAdapter = AddItemAdapter(menuItems)
        recyclerView.adapter = addItemAdapter

        fetchProductsFromFirebase()
    }

    private fun fetchProductsFromFirebase() {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("products")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                menuItems.clear()
                for (subcategorySnapshot in snapshot.children) {
                    for (productSnapshot in subcategorySnapshot.children) {
                        val product = productSnapshot.getValue(AllMenu::class.java)
                        if (product != null) {
                            menuItems.add(product)
                        }
                    }
                }
                addItemAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AllItemActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
