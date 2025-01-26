package com.musinsa.musinsacodingassignment.product.presentation.dto.response

import com.musinsa.musinsacodingassignment.product.service.vo.ProductVO

data class CreateProductResponse(
    val id: Long
)

fun ProductVO.toCreateProductResponse() = CreateProductResponse(
    id = this.id
)
