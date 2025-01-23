package com.musinsa.musinsacodingassignment.brand.presentation.dto.response

import com.musinsa.musinsacodingassignment.brand.domain.Brand

data class InquiryBrandListResponse(
    val brands: List<Brand>
)

fun List<Brand>.toResponse() = InquiryBrandListResponse(
    brands = this
)
