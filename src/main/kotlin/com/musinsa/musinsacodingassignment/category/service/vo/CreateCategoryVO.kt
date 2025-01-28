package com.musinsa.musinsacodingassignment.category.service.vo

import com.musinsa.musinsacodingassignment.category.entity.CategoryEntity

data class CreateCategoryVO(
    val name: String
)

fun CreateCategoryVO.toEntity(id: Long) = CategoryEntity(
    id = id,
    name = this.name
)
