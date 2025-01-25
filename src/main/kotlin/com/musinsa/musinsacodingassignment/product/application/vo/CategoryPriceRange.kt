package com.musinsa.musinsacodingassignment.product.application.vo

import com.musinsa.musinsacodingassignment.category.domain.Category

data class CategoryPriceRange(
    val category: Category,
    val minPriceProducts: List<BrandPrice>,
    val maxPriceProducts: List<BrandPrice>
)
