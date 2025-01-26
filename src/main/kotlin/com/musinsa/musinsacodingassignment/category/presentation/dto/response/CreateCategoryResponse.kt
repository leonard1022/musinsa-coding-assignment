package com.musinsa.musinsacodingassignment.category.presentation.dto.response

import com.musinsa.musinsacodingassignment.category.service.vo.CategoryVO

data class CreateCategoryResponse(
    val id: Long
)

fun CategoryVO.toCreateCategoryResponse() = CreateCategoryResponse(
    id = this.id
)
