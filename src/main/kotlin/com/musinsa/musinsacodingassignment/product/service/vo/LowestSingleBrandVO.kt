package com.musinsa.musinsacodingassignment.product.service.vo

data class LowestSingleBrandVO(
    val brandName: String,
    val totalPrice: Int,
    val categories: List<CategoryPrice>
)

data class CategoryPrice(
    val category: String,
    val price: Int
)
