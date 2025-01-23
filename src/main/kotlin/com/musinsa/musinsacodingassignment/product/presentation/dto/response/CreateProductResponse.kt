package com.musinsa.musinsacodingassignment.product.presentation.dto.response

import com.musinsa.musinsacodingassignment.product.domain.Product

data class CreateProductResponse(
    val id: Long
)

fun Product.toCreateProductResponse() = CreateProductResponse(
    id = this.id!!
)
