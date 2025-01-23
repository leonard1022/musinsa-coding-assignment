package com.musinsa.musinsacodingassignment.brand.entity

import com.musinsa.musinsacodingassignment.common.domain.BaseEntity
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
