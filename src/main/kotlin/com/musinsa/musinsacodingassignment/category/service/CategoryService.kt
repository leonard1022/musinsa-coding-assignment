package com.musinsa.musinsacodingassignment.category.service

import com.musinsa.musinsacodingassignment.category.entity.toVO
import com.musinsa.musinsacodingassignment.category.repository.CategoryRepository
import com.musinsa.musinsacodingassignment.category.service.vo.CategoryVO
import com.musinsa.musinsacodingassignment.category.service.vo.CreateCategoryVO
import com.musinsa.musinsacodingassignment.category.service.vo.UpdateCategoryVO
import com.musinsa.musinsacodingassignment.category.service.vo.toEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {

    fun getCategories(): List<CategoryVO> {
        return categoryRepository.findAll().map { it.toVO() }
    }

    @Transactional
    fun createCategory(vo: CreateCategoryVO): CategoryVO {
        vo.toEntity(0).let {
            return categoryRepository.save(it).toVO()
        }
    }

    @Transactional
    fun updateCategory(vo: UpdateCategoryVO): CategoryVO {
        vo.toEntity().let {
            return categoryRepository.save(it).toVO()
        }
    }
}
