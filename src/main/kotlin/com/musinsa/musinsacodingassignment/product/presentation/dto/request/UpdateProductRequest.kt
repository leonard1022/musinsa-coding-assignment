package com.musinsa.musinsacodingassignment.product.presentation.dto.request

import com.musinsa.musinsacodingassignment.product.service.vo.UpdateProductVO
import jakarta.validation.constraints.NotNull


data class UpdateProductRequest(
    @field:NotNull val brandId: Long,
    @field:NotNull val categoryId: Long,
    @field:NotNull val price: Int
)

fun UpdateProductRequest.toUpdateProductVO(id: Long) = UpdateProductVO(
    id = id,
    brandId = this.brandId,
    categoryId = this.categoryId,
    price = this.price
)
