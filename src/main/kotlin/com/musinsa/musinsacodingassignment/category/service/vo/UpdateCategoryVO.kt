package com.musinsa.musinsacodingassignment.category.service.vo

import com.musinsa.musinsacodingassignment.category.entity.CategoryEntity

data class UpdateCategoryVO(
    val id: Long,
    val name: String
)

fun UpdateCategoryVO.toEntity() = CategoryEntity(
    id = this.id,
    name = this.name
)
