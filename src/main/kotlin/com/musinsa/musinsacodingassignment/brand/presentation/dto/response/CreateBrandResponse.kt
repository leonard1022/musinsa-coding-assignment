package com.musinsa.musinsacodingassignment.brand.presentation.dto.response

import com.musinsa.musinsacodingassignment.brand.service.vo.BrandVO

data class CreateBrandResponse(
    val id: Long
)

fun BrandVO.toCreateBrandResponse() = CreateBrandResponse(
    id = this.id
)
