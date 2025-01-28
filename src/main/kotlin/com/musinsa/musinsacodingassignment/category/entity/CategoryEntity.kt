package com.musinsa.musinsacodingassignment.category.entity

import com.musinsa.musinsacodingassignment.category.domain.Category
import com.musinsa.musinsacodingassignment.category.service.vo.CategoryVO
import com.musinsa.musinsacodingassignment.common.entity.BaseEntity
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

    constructor(categoryVO: CategoryVO) : this(categoryVO.name)
}

fun CategoryEntity.toDomain() = Category(
    id = this.id,
    name = this.name,
)

fun CategoryEntity.toVO() = CategoryVO(
    id = this.id,
    name = this.name,
)

fun Category.toEntity() = CategoryEntity(
    id = this.id,
    name = this.name
)
