package com.musinsa.musinsacodingassignment.category.entity

import com.musinsa.musinsacodingassignment.category.domain.Category
import com.musinsa.musinsacodingassignment.common.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "Categories")
class CategoryEntity(
    @Column(nullable = false, unique = true)
    val name: String
) : BaseEntity() {
    constructor(id: Long, name: String) : this(name) {
        this.id = id
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
