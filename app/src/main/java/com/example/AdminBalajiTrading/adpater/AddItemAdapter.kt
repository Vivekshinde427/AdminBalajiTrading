package com.example.AdminBalajiTrading.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.AdminBalajiTrading.databinding.ItemItemBinding
import com.example.AdminBalajiTrading.model.AllMenu

class AddItemAdapter(private var menuItems: List<AllMenu>) : RecyclerView.Adapter<AddItemAdapter.ViewHolder>() {

    // Inflate the layout for each item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Use the correct binding class (ItemItemBinding)
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // Bind the data to the view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = menuItems[position]
        holder.bind(item)
    }

    // Get the item count
    override fun getItemCount(): Int {
        return menuItems.size
    }

    // Update the list of products
    fun updateMenuItems(newMenuItems: List<AllMenu>) {
        menuItems = newMenuItems
        notifyDataSetChanged()
    }

    // ViewHolder class to bind the data
    inner class ViewHolder(private val binding: ItemItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menuItem: AllMenu) {
            binding.productNameTextView.text = menuItem.productName
            binding.priceTextView.text = menuItem.productPrice
            binding.subcategoryTextView.text = menuItem.subcategory
        }
    }
}
