package com.musinsa.musinsacodingassignment.category.presentation.dto.request

import com.musinsa.musinsacodingassignment.category.domain.Category

data class CreateCategoryRequest(
    val name: String
)

fun CreateCategoryRequest.toDomain() = Category(
    id = 0,
    name = this.name,
)
