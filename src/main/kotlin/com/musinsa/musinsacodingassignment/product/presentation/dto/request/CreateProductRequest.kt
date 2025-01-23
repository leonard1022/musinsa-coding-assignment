package com.musinsa.musinsacodingassignment.product.presentation.dto.request

import com.musinsa.musinsacodingassignment.product.domain.Product

data class CreateProductRequest(
    val id: Long?,
    val brandId: Long,
    val categoryId: Long,
    val price: Int
)

fun CreateProductRequest.toDomain() = Product(
    id = 0,
    brandId = this.brandId,
    categoryId = this.categoryId,
    price = this.price
)
