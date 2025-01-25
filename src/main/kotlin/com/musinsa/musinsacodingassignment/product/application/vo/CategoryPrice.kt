package com.musinsa.musinsacodingassignment.product.application.vo

import com.musinsa.musinsacodingassignment.category.domain.Category

data class CategoryPrice(
    val category: Category,
    val price: Int
)
