package com.musinsa.musinsacodingassignment.brand.presentation.dto.response

import com.musinsa.musinsacodingassignment.brand.domain.Brand

data class UpdateBrandResponse(
    val id: Long,
    val name: String
)

fun Brand.toUpdateBrandResponse() = UpdateBrandResponse(
    id = this.id!!,
    name = this.name
)
