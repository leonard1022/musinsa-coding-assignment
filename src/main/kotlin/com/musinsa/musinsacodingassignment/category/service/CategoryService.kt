package com.musinsa.musinsacodingassignment.category.service

import com.musinsa.musinsacodingassignment.category.entity.toVO
import com.musinsa.musinsacodingassignment.category.exception.CategoryErrorCode
import com.musinsa.musinsacodingassignment.category.repository.CategoryRepository
import com.musinsa.musinsacodingassignment.category.service.vo.CategoryVO
import com.musinsa.musinsacodingassignment.category.service.vo.CreateCategoryVO
import com.musinsa.musinsacodingassignment.category.service.vo.UpdateCategoryVO
import com.musinsa.musinsacodingassignment.category.service.vo.toEntity
import com.musinsa.musinsacodingassignment.common.exception.IllegalArgumentException
import org.springframework.data.repository.findByIdOrNull
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
        if (categoryRepository.existsByName(vo.name)) {
            throw IllegalArgumentException(CategoryErrorCode.CATEGORY_ALREADY_EXISTS)
        }
        val entity = vo.toEntity(0)
        return categoryRepository.save(entity).toVO()
    }

    @Transactional
    fun updateCategory(vo: UpdateCategoryVO): CategoryVO {
        categoryRepository.findByIdOrNull(vo.id)
            ?: throw IllegalArgumentException(CategoryErrorCode.CATEGORY_NOT_FOUND)
        val updatedEntity = vo.toEntity()
        return categoryRepository.save(updatedEntity).toVO()
    }
}
