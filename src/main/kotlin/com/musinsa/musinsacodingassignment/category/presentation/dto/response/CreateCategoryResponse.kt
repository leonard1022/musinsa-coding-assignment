package com.musinsa.musinsacodingassignment.category.presentation.dto.response

import com.musinsa.musinsacodingassignment.category.domain.Category

data class CreateCategoryResponse(
    val id: Long
)

fun Category.toCreateCategoryResponse() = CreateCategoryResponse(
    id = this.id!!
)
