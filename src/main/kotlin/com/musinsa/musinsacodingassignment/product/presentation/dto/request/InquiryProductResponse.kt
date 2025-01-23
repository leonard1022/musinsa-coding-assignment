package com.musinsa.musinsacodingassignment.product.presentation.dto.request

import com.musinsa.musinsacodingassignment.product.domain.Product

data class InquiryProductResponse(
    val products: List<Product>
)

fun List<Product>.toInquiryProductResponse() = InquiryProductResponse(
    products = this
)
