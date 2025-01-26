package com.musinsa.musinsacodingassignment.brand.presentation.dto.response

import com.musinsa.musinsacodingassignment.brand.service.vo.BrandVO

data class GetBrandListResponse(
    val brands: List<BrandVO>
)

fun List<BrandVO>.toGetBrandListResponse() = GetBrandListResponse(
    brands = this
)
