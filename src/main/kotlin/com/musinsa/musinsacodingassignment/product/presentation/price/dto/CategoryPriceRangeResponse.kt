package com.musinsa.musinsacodingassignment.product.presentation.price.dto

import com.musinsa.musinsacodingassignment.product.application.vo.CategoryPriceRange

data class CategoryPriceRangeResponse(
    val category: String,
    val minPrice: List<BrandPrice>,
    val maxPrice: List<BrandPrice>
)

data class BrandPrice(
    val brand: String,
    val price: Int
)

fun CategoryPriceRange.toCategoryPriceRangeResponse(): CategoryPriceRangeResponse {
    return CategoryPriceRangeResponse(
        category = this.category.name,
        minPrice = this.minPriceProducts.map { BrandPrice(it.brand.name, it.price) },
        maxPrice = this.maxPriceProducts.map { BrandPrice(it.brand.name, it.price) }
    )
}
