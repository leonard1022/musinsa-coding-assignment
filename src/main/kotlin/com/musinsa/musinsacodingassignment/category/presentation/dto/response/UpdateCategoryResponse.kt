package com.musinsa.musinsacodingassignment.category.presentation.dto.response

import com.musinsa.musinsacodingassignment.category.service.vo.CategoryVO

data class UpdateCategoryResponse(
    val id: Long,
    val name: String
)

fun CategoryVO.toUpdateCategoryResponse() = UpdateCategoryResponse(
    id = this.id,
    name = this.name
)
