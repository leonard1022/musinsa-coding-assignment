package com.musinsa.musinsacodingassignment.product.presentation.dto.request

import com.musinsa.musinsacodingassignment.product.service.vo.UpdateProductVO


data class UpdateProductRequest(
    val brandId: Long,
    val categoryId: Long,
    val price: Int
)

fun UpdateProductRequest.toUpdateProductVO(id: Long) = UpdateProductVO(
    id = id,
    brandId = this.brandId,
    categoryId = this.categoryId,
    price = this.price
)
