package com.musinsa.musinsacodingassignment.product.repository

import com.musinsa.musinsacodingassignment.product.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

interface ProductRepository : JpaRepository<ProductEntity, Long> {

    fun findAllByDeletedAtIsNull(): List<ProductEntity>

    @Modifying
    @Transactional
    @Query("UPDATE ProductEntity p SET p.deletedAt = :deletedAt WHERE p.id = :id")
    fun deleteProductEntity(id: Long, deletedAt: LocalDateTime? = LocalDateTime.now()): Int
}
