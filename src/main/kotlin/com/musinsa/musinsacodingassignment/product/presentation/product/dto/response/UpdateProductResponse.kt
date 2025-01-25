package com.musinsa.musinsacodingassignment.product.presentation.product.dto.response

import com.musinsa.musinsacodingassignment.product.domain.Product

data class UpdateProductResponse(
    val id: Long
)

fun Product.toUpdateProductResponse() = UpdateProductResponse(
    id = this.id!!
)
