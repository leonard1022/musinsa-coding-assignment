package com.musinsa.musinsacodingassignment.brand.repository

import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BrandRepository : JpaRepository<BrandEntity, Long> {
    fun findByName(name: String): BrandEntity?

    fun findAllByDeletedAtIsNull(): List<BrandEntity>
}
