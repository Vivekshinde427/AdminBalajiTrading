package com.example.AdminBalajiTrading.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.AdminBalajiTrading.databinding.ItemItemBinding
import com.example.AdminBalajiTrading.model.AllMenu

class AddItemAdapter(
    private val menuItems: MutableList<AllMenu>
) : RecyclerView.Adapter<AddItemAdapter.AddItemViewHolder>() {

    private var itemQuantities = IntArray(menuItems.size) { 1 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        Log.d("AddItemAdapter", "onBindViewHolder called for position $position")
        Log.d("AddItemAdapter", "menuItems size: ${menuItems.size}, itemQuantities size: ${itemQuantities.size}")

        // Ensure itemQuantities is initialized properly based on menuItems size
        if (itemQuantities.size != menuItems.size) {
            Log.d("AddItemAdapter", "Initializing itemQuantities size: ${menuItems.size}")
            itemQuantities = IntArray(menuItems.size) { 1 }
        }

        if (position >= menuItems.size || position >= itemQuantities.size) {
            Log.e("AddItemAdapter", "Invalid position: $position")
            return
        }

        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItems.size

    // Update menu items and reset itemQuantities size accordingly
    fun updateMenuItems(newItems: List<AllMenu>) {
        Log.d("AddItemAdapter", "Updating menu items. New size: ${newItems.size}")
        menuItems.clear()
        menuItems.addAll(newItems)

        // Reinitialize itemQuantities with the same size as menuItems, each set to 1
        itemQuantities = IntArray(menuItems.size) { 1 }
        notifyDataSetChanged()
    }

    inner class AddItemViewHolder(private val binding: ItemItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            Log.d("AddItemAdapter", "Binding data at position $position")
            if (position >= menuItems.size || position >= itemQuantities.size) {
                Log.e("AddItemAdapter", "Invalid position during bind: $position")
                return
            }

            val item = menuItems[position]
            binding.apply {
                productNameTextView.text = item.productName.ifEmpty { "Unknown Product" }
                priceTextView.text = item.productPrice.ifEmpty { "N/A" }
                subcategoryTextView.text = item.subcategory.ifEmpty { "Uncategorized" }
                quantityTextView.text = itemQuantities[position].toString()

                plusButton.setOnClickListener { increaseQuantity(position) }
                minusButton.setOnClickListener { decreaseQuantity(position) }
                deleteButton.setOnClickListener { deleteItem(position) }
            }
        }

        private fun increaseQuantity(position: Int) {
            if (position < itemQuantities.size && itemQuantities[position] < 10) {
                itemQuantities[position]++
                notifyItemChanged(position)
            }
        }

        private fun decreaseQuantity(position: Int) {
            if (position < itemQuantities.size && itemQuantities[position] > 1) {
                itemQuantities[position]--
                notifyItemChanged(position)
            }
        }

        private fun deleteItem(position: Int) {
            if (position < menuItems.size && position < itemQuantities.size) {
                menuItems.removeAt(position)
                itemQuantities = itemQuantities.copyOfRange(0, menuItems.size) // Remove quantity for deleted item
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemCount)
            }
        }
    }
}
