package com.musinsa.musinsacodingassignment.product.service.vo

data class MinMaxPriceByCategoryVO(
    val category: String,
    val minPriceProducts: List<BrandPrice>,
    val maxPriceProducts: List<BrandPrice>
)

data class BrandPrice(
    val brand: String,
    val price: Int
)
