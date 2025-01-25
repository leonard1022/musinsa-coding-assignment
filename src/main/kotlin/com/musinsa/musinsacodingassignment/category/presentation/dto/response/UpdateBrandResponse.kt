package com.musinsa.musinsacodingassignment.category.presentation.dto.response

import com.musinsa.musinsacodingassignment.category.domain.Category

data class UpdateCategoryResponse(
    val id: Long,
    val name: String
)

fun Category.toUpdateCategoryResponse() = UpdateCategoryResponse(
    id = this.id!!,
    name = this.name
)
