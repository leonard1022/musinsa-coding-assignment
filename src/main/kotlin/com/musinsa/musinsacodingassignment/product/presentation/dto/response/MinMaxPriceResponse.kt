package com.musinsa.musinsacodingassignment.product.presentation.dto.response

import com.musinsa.musinsacodingassignment.product.service.vo.BrandPrice
import com.musinsa.musinsacodingassignment.product.service.vo.MinMaxPriceByCategoryVO

data class MinMaxPriceResponse(
    val category: String,
    val minPrice: List<BrandPrice>,
    val maxPrice: List<BrandPrice>
)

fun MinMaxPriceByCategoryVO.toMinMaxPriceResponse(): MinMaxPriceResponse {
    return MinMaxPriceResponse(
        category = this.category,
        minPrice = this.minPriceProducts.map {
            BrandPrice(
                brand = it.brand,
                price = it.price
            )
        },
        maxPrice = this.maxPriceProducts.map {
            BrandPrice(
                brand = it.brand,
                price = it.price
            )
        }
    )
}
