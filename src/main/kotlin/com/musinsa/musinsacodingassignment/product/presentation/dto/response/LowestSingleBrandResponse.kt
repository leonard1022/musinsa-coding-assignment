package com.musinsa.musinsacodingassignment.product.presentation.dto.response

import com.musinsa.musinsacodingassignment.product.service.vo.CategoryPrice
import com.musinsa.musinsacodingassignment.product.service.vo.LowestSingleBrandVO

data class LowestSingleBrandResponse(
    val lowestPrice: LowestPrice
)

data class LowestPrice(
    val brandName: String,
    val categories: List<LowestCategoryPrice>,
    val totalPrice: Int
)

data class LowestCategoryPrice(
    val category: String,
    val price: Int
)

data class BrandCategoryPrice(
    val brandName: String,
    val categories: List<CategoryPrice>,
    val totalPrice: Int
)

fun LowestSingleBrandVO.toLowestSingleBrandResponse(): LowestSingleBrandResponse {
    return LowestSingleBrandResponse(
        lowestPrice = LowestPrice(
            brandName = this.brandName,
            categories = this.categories.map { LowestCategoryPrice(it.category, it.price) },
            totalPrice = this.totalPrice
        )
    )
}
