package com.musinsa.musinsacodingassignment.category.presentation.dto.request

import com.musinsa.musinsacodingassignment.category.domain.Category

data class UpdateCategoryRequest(
    val name: String
)

fun UpdateCategoryRequest.toCategory(id: Long) = Category(
    id = id,
    name = this.name,
)
