package com.musinsa.musinsacodingassignment.brand.presentation.dto.request

import com.musinsa.musinsacodingassignment.brand.domain.Brand

data class CreateBrandRequest(
    val name: String
)

fun CreateBrandRequest.toDomain() = Brand(
    id = 0,
    name = this.name,
)
