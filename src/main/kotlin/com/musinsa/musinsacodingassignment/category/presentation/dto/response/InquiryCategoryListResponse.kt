package com.musinsa.musinsacodingassignment.category.presentation.dto.response

import com.musinsa.musinsacodingassignment.category.domain.Category

data class InquiryCategoryListResponse(
    val categories: List<Category>
)

fun List<Category>.toInquiryCategoryListResponse() = InquiryCategoryListResponse(
    categories = this
)
