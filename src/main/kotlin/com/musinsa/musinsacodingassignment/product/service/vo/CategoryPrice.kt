package com.musinsa.musinsacodingassignment.product.service.vo

import com.musinsa.musinsacodingassignment.category.domain.Category

data class CategoryPrice(
    val category: Category,
    val price: Int
)
