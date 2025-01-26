package com.musinsa.musinsacodingassignment.brand.presentation.dto.response

import com.musinsa.musinsacodingassignment.brand.service.vo.BrandVO

data class UpdateBrandResponse(
    val id: Long,
    val name: String
)

fun BrandVO.toUpdateBrandResponse() = UpdateBrandResponse(
    id = this.id,
    name = this.name
)
