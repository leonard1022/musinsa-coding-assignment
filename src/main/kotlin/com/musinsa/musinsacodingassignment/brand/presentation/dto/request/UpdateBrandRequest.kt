package com.musinsa.musinsacodingassignment.brand.presentation.dto.request

import com.musinsa.musinsacodingassignment.brand.domain.Brand

data class UpdateBrandRequest(
    val name: String
)

fun UpdateBrandRequest.toDomain(id: Long) = Brand(
    id = id,
    name = this.name,
)
