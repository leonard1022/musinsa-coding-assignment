package com.musinsa.musinsacodingassignment.product.presentation.dto.response

import com.musinsa.musinsacodingassignment.product.domain.Product

data class UpdateProductResponse(
    val id: Long
)

fun Product.toUpdateProductResponse() = UpdateProductResponse(
    id = this.id!!
)
