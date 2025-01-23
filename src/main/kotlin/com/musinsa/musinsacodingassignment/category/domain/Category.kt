package com.musinsa.musinsacodingassignment.category.domain

import com.musinsa.musinsacodingassignment.brand.entity.CategoryEntity

data class Category(
    var id: Long?,
    var name: String
) {
    fun updateName(name: String): Category {
        this.name = name
        return this
    }
}

fun Category.toEntity() = CategoryEntity(
    id = this.id ?: 0,
    name = this.name
)

fun CategoryEntity.toDomain() = Category(
    id = this.id,
    name = this.name,
)
