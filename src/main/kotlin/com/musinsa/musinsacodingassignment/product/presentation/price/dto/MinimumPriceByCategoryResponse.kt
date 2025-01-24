package com.musinsa.musinsacodingassignment.product.presentation.price.dto

import com.musinsa.musinsacodingassignment.product.application.vo.MinimumPriceProduct

data class MinimumPriceByCategoryResponse(
    val products: List<MinimumPriceProduct>,
    val totalPrice: Int
)

fun List<MinimumPriceProduct>.toMinimumPriceByCategoryResponse(): MinimumPriceByCategoryResponse {
    return MinimumPriceByCategoryResponse(
        products = this,
        totalPrice = this.sumOf { it.price }
    )
}
