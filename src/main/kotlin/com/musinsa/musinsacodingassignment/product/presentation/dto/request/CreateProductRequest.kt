package com.musinsa.musinsacodingassignment.product.presentation.dto.request

import com.musinsa.musinsacodingassignment.product.service.vo.CreateProductVO
import jakarta.validation.constraints.NotNull


data class CreateProductRequest(
    @field:NotNull val brandId: Long,
    @field:NotNull val categoryId: Long,
    @field:NotNull val price: Int
)

fun CreateProductRequest.toCreateProductVO() = CreateProductVO(
    brandId = this.brandId,
    categoryId = this.categoryId,
    price = this.price
)
