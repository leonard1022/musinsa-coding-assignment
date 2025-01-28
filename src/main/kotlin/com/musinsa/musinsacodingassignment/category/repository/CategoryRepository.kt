package com.musinsa.musinsacodingassignment.category.repository

import com.musinsa.musinsacodingassignment.category.entity.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<CategoryEntity, Long> {
    fun findByName(name: String): CategoryEntity?

    fun existsByName(name: String): Boolean
}
