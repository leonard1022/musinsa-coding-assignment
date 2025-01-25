package com.musinsa.musinsacodingassignment.brand.repository

import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface BrandRepository : JpaRepository<BrandEntity, Long> {
    fun findByName(name: String): BrandEntity?

    fun findAllByDeletedAtIsNull(): List<BrandEntity>

    @Modifying
    @Query("UPDATE BrandEntity p SET p.deletedAt = :deletedAt WHERE p.id = :id")
    fun deleteBrandEntity(id: Long, deletedAt: LocalDateTime? = LocalDateTime.now()): Int
}
