package com.musinsa.musinsacodingassignment.brand.entity

import com.musinsa.musinsacodingassignment.brand.domain.Brand
import com.musinsa.musinsacodingassignment.brand.service.vo.BrandVO
import com.musinsa.musinsacodingassignment.common.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "brands")
class BrandEntity(
    @Column(nullable = false, unique = true)
    val name: String
) : BaseEntity() {
    constructor(id: Long, name: String) : this(name) {
        this.id = id
    }
}

fun BrandEntity.toDomain() = Brand(
    id = this.id,
    name = this.name,
)

fun BrandEntity.toVO() = BrandVO(
    id = this.id,
    name = this.name,
)
