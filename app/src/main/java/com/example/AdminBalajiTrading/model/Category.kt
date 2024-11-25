package com.example.AdminBalajiTrading.model

data class Category(
    val categoryName: String = "",  // Category name (e.g., Cement, Wood)
    val products: List<AllMenu> = emptyList()  // List of products in this category
)
