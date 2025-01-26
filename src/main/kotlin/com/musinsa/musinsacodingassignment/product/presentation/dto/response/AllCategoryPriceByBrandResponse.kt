package com.musinsa.musinsacodingassignment.product.presentation.dto.response

import com.musinsa.musinsacodingassignment.product.service.vo.AllCategoryPriceByBrand

data class AllCategoryPriceByBrandResponse(
    val lowestPrice: LowestPrice
)

data class LowestPrice(
    val brand: String,
    val categories: List<LowestCategoryPrice>,
    val totalPrice: Int
)

data class LowestCategoryPrice(
    val category: String,
    val price: Int
)

fun AllCategoryPriceByBrand.toAllCategoryPriceByBrandResponse(): AllCategoryPriceByBrandResponse {
    return AllCategoryPriceByBrandResponse(
        lowestPrice = LowestPrice(
            brand = this.brand.name,
            categories = this.categories.map { LowestCategoryPrice(it.category.name, it.price) },
            totalPrice = this.totalPrice
        )
    )
}
