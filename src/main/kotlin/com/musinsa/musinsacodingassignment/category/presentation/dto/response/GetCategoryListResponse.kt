package com.musinsa.musinsacodingassignment.category.presentation.dto.response

import com.musinsa.musinsacodingassignment.category.service.vo.CategoryVO

data class GetCategoryListResponse(
    val categories: List<CategoryVO>
)

fun List<CategoryVO>.toGetCategoryListResponse() = GetCategoryListResponse(
    categories = this
)
