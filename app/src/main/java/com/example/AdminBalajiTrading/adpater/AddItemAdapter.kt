package com.example.AdminBalajiTrading.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.AdminBalajiTrading.databinding.ItemItemBinding

class AddItemAdapter(private val MenuItemName: MutableList<String>, private val MenuItemPrice: MutableList<String>, private val MenuItemImage: MutableList<Int>): RecyclerView.Adapter<AddItemAdapter.AddItemViemHolder>() {

    private val itemQuantities = IntArray(MenuItemName.size) { 1 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViemHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddItemViemHolder(binding)
    }


    override fun onBindViewHolder(holder: AddItemViemHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = MenuItemName.size


    inner class AddItemViemHolder(private val binding: ItemItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                productNameTextView.text = MenuItemName[position]
                priceTextView.text = MenuItemPrice[position]
                productImageView.setImageResource(MenuItemImage[position])
                quantityTextView.text = quantity.toString()

                minusButton.setOnClickListener {
                    decreaseQuantity(position)

                }
                deleteButton.setOnClickListener {
                    deleteQuantity(position)
                }

                plusButton.setOnClickListener {
                    increaseQuantity(position)
                }


            }
        }


        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++

                binding.quantityTextView.text = itemQuantities[position].toString()
            }
        }

        private fun decreaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--

                binding.quantityTextView.text = itemQuantities[position].toString()
            }
        }

        private fun deleteQuantity(position: Int) {
            MenuItemName.removeAt(position)
            MenuItemPrice.removeAt(position)
            MenuItemImage.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, MenuItemName.size)

        }

    }
}
