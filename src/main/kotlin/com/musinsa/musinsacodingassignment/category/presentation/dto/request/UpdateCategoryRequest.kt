package com.musinsa.musinsacodingassignment.category.presentation.dto.request

import com.musinsa.musinsacodingassignment.category.service.vo.UpdateCategoryVO

data class UpdateCategoryRequest(
    val name: String
)

fun UpdateCategoryRequest.toVO(id: Long) = UpdateCategoryVO(
    id = id,
    name = this.name,
)
