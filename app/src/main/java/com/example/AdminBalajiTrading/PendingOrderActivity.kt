package com.example.AdminBalajiTrading

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.AdminBalajiTrading.adpater.PendingOrderAdapter
import com.example.AdminBalajiTrading.databinding.ActivityPendingOrderBinding
import com.example.AdminBalajiTrading.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PendingOrderActivity : AppCompatActivity() {
    private val binding:ActivityPendingOrderBinding by lazy {
        ActivityPendingOrderBinding.inflate(layoutInflater)
    }
    private var listOfName:MutableList<String> = mutableListOf()
    private var listOfTotalPrice :MutableList<String> = mutableListOf()
    private var listOfImageFirstFoodOrder:MutableList<String> = mutableListOf()
    private var listOfOrderItem:MutableList<OrderDetails> = mutableListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseOrderDetails: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        database=FirebaseDatabase.getInstance()
        databaseOrderDetails=database.reference.child("OrderDetails")
        getOrderDetails()
        binding.backButton.setOnClickListener {
            finish()
        }




    }

    private fun getOrderDetails() {

        databaseOrderDetails.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapshot in snapshot.children){
                    val orderDetails = orderSnapshot.getValue(OrderDetails::class.java)
                    orderDetails?.let {
                        listOfOrderItem.add(it)
                    }
                }
                addDataToListForRecyclerView()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    private fun addDataToListForRecyclerView() {
    for (orderItem in listOfOrderItem){
        orderItem.userName?.let {listOfName.add(it)}
        orderItem.totalprice?.let {listOfTotalPrice.add(it)}
        orderItem.foodImages?.filterNot {it.isEmpty()}?.forEach{
            listOfImageFirstFoodOrder.add(it)
        }



    }
        setAdapter()
    }

    private fun setAdapter() {
        binding.pendingOrderRecyclerView.layoutManager=LinearLayoutManager(this)
        val adapter= PendingOrderAdapter(this,listOfName,listOfTotalPrice,listOfImageFirstFoodOrder)
        binding.pendingOrderRecyclerView.adapter = adapter
    }
}

