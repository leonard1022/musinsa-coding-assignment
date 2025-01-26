package com.musinsa.musinsacodingassignment.product.presentation.dto.request

import com.musinsa.musinsacodingassignment.product.service.vo.CreateProductVO


data class CreateProductRequest(
    val brandId: Long,
    val categoryId: Long,
    val price: Int
)


fun CreateProductRequest.toCreateProductVO() = CreateProductVO(
    brandId = this.brandId,
    categoryId = this.categoryId,
    price = this.price
)
