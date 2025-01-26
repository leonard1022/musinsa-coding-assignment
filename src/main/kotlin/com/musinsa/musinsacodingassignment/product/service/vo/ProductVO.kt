package com.musinsa.musinsacodingassignment.product.service.vo

data class ProductVO(
    var id: Long,
    var brandId: Long,
    var brandName: String,
    var categoryId: Long,
    var categoryName: String,
    var price: Int
)
