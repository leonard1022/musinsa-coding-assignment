package com.musinsa.musinsacodingassignment.product.repository

import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity
import com.musinsa.musinsacodingassignment.category.entity.CategoryEntity
import com.musinsa.musinsacodingassignment.product.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface ProductRepository : JpaRepository<ProductEntity, Long> {

    fun findAllByDeletedAtIsNull(): List<ProductEntity>

    @Modifying
    @Query("UPDATE ProductEntity p SET p.deletedAt = :deletedAt WHERE p.id = :id")
    fun deleteProductEntity(id: Long, deletedAt: LocalDateTime? = LocalDateTime.now()): Int

    fun findAllByCategoryAndDeletedAtIsNull(categoryEntity: CategoryEntity): List<ProductEntity>

    fun findAllByBrandAndCategoryAndDeletedAtIsNull(brandEntity: BrandEntity, categoryEntity: CategoryEntity): List<ProductEntity>

}
