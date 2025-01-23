package com.musinsa.musinsacodingassignment.brand.presentation.dto.response

import com.musinsa.musinsacodingassignment.brand.domain.Brand
import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity

data class CreateBrandResponse(
    val id: Long
)

fun Brand.toCreateBrandResponse() = CreateBrandResponse(
    id = this.id!!
)
