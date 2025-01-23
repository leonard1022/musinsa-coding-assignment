package com.musinsa.musinsacodingassignment.product.presentation.product.dto.request

import com.musinsa.musinsacodingassignment.product.domain.Product

data class UpdateProductRequest(
    val brandId: Long,
    val categoryId: Long,
    val price: Int
)

fun UpdateProductRequest.toDomain() = Product(
    id = 0,
    brandId = this.brandId,
    categoryId = this.categoryId,
    price = this.price
)
