package com.example.AdminBalajiTrading.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.AdminBalajiTrading.R
import com.example.AdminBalajiTrading.model.Category
import com.example.AdminBalajiTrading.model.AllMenu
import android.widget.TextView
import android.view.View

class CategoryAdapter(private val categories: List<Category>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_CATEGORY = 0
        const val VIEW_TYPE_PRODUCT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (categories[position].products.isNotEmpty()) VIEW_TYPE_CATEGORY else VIEW_TYPE_PRODUCT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CATEGORY -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
                CategoryViewHolder(view)
            }
            VIEW_TYPE_PRODUCT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_item, parent, false)
                ProductViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoryViewHolder -> {
                val category = categories[position]
                holder.bind(category)
            }
            is ProductViewHolder -> {
                val product = categories[position].products[0]  // Use the first product for the product view
                holder.bind(product)
            }
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    // ViewHolder for Category
    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryNameTextView: TextView = itemView.findViewById(R.id.categoryNameTextView)

        fun bind(category: Category) {
            categoryNameTextView.text = category.categoryName  // Set the category name
        }
    }

    // ViewHolder for Product
    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productNameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        private val subcategoryTextView: TextView = itemView.findViewById(R.id.subcategoryTextView)

        fun bind(product: AllMenu) {
            productNameTextView.text = product.productName
            priceTextView.text = product.productPrice
            subcategoryTextView.text = product.subcategory
        }
    }
}
