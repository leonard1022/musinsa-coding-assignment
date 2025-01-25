package com.musinsa.musinsacodingassignment.product.application.vo

import com.musinsa.musinsacodingassignment.brand.domain.Brand

data class AllCategoryPriceByBrand(
    val brand: Brand,
    val totalPrice: Int,
    val categories: List<CategoryPrice>
)
