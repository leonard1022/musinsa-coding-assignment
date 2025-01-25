package com.musinsa.musinsacodingassignment.product.presentation.product.dto.response

import com.musinsa.musinsacodingassignment.product.domain.Product

data class CreateProductResponse(
    val id: Long
)

fun Product.toCreateProductResponse() = CreateProductResponse(
    id = this.id!!
)
