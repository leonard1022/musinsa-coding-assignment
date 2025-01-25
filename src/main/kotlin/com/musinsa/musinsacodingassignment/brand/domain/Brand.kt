package com.musinsa.musinsacodingassignment.brand.domain

import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity

data class Brand(
    var id: Long?,
    var name: String
) {
    fun updateName(name: String): Brand {
        this.name = name
        return this
    }
}

fun Brand.toEntity() = BrandEntity(
    id = this.id ?: 0,
    name = this.name
)

fun BrandEntity.toDomain() = Brand(
    id = this.id,
    name = this.name,
)
