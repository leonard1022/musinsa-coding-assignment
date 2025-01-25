package com.musinsa.musinsacodingassignment.product.application.vo

import com.musinsa.musinsacodingassignment.brand.domain.Brand
import com.musinsa.musinsacodingassignment.category.domain.Category

data class MinimumPriceProduct(
    val brand: Brand,
    val category: Category,
    val price: Int
)
