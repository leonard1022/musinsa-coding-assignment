package com.musinsa.musinsacodingassignment.product.presentation.dto.response

import com.musinsa.musinsacodingassignment.product.service.vo.LowestPricesByCategoryVO

data class LowestPricesByCategoryResponse(
    val products: List<LowestPricesByCategoryVO>,
    val totalPrice: Int
)

fun List<LowestPricesByCategoryVO>.toMLowestPricesByCategoryResponse(): LowestPricesByCategoryResponse {
    return LowestPricesByCategoryResponse(
        products = this,
        totalPrice = this.sumOf { it.price }
    )
}
