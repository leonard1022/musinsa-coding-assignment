package com.musinsa.musinsacodingassignment.product.presentation.dto.response

import com.musinsa.musinsacodingassignment.product.service.vo.ProductVO

data class GetProductListResponse(
    val products: List<ProductVO>
)

fun List<ProductVO>.toGetProductListResponse() = GetProductListResponse(
    products = this
)
