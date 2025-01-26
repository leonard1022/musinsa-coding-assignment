package com.musinsa.musinsacodingassignment.product.presentation.dto.response

import com.musinsa.musinsacodingassignment.product.service.vo.ProductVO

data class UpdateProductResponse(
    val id: Long
)

fun ProductVO.toUpdateProductResponse() = UpdateProductResponse(
    id = this.id
)
