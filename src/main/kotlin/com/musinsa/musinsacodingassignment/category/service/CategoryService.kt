package com.musinsa.musinsacodingassignment.category.service

import com.musinsa.musinsacodingassignment.category.repository.CategoryRepository
import com.musinsa.musinsacodingassignment.category.code.CategoryErrorCode
import com.musinsa.musinsacodingassignment.category.domain.Category
import com.musinsa.musinsacodingassignment.category.domain.toDomain
import com.musinsa.musinsacodingassignment.category.domain.toEntity
import com.musinsa.musinsacodingassignment.category.exception.CategoryException
import com.musinsa.musinsacodingassignment.category.presentation.dto.request.CreateCategoryRequest
import com.musinsa.musinsacodingassignment.category.presentation.dto.request.UpdateCategoryRequest
import com.musinsa.musinsacodingassignment.category.presentation.dto.request.toCategory
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    fun createCategory(request: CreateCategoryRequest): Category {
        if (request.name.isBlank()) {
            throw CategoryException(CategoryErrorCode.CATEGORY_NAME_IS_REQUIRED)
        }

        categoryRepository.findByName(request.name)?.let {
            throw CategoryException(CategoryErrorCode.CATEGORY_ALREADY_EXISTS)
        }

        val category = request.toCategory()
        try {
            val entity = categoryRepository.save(category.toEntity())
            return entity.toDomain()
        } catch (ex: DataIntegrityViolationException) {
            throw CategoryException(CategoryErrorCode.CATEGORY_DUPLICATED)
        }
    }

    fun getCategories(): List<Category> {
        return categoryRepository.findAll().map { it.toDomain() }
    }

    fun updateCategory(id: Long, request: UpdateCategoryRequest): Category {
        categoryRepository.findById(id)
            .orElseThrow { CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND) }

        request.toCategory(id).let {
            if (it.name.isBlank()) {
                throw CategoryException(CategoryErrorCode.CATEGORY_NAME_IS_REQUIRED)
            }

            categoryRepository.findByName(it.name)?.let {
                throw CategoryException(CategoryErrorCode.CATEGORY_ALREADY_EXISTS)
            }

            try {
                val updatedEntity = categoryRepository.save(it.toEntity())
                return updatedEntity.toDomain()
            } catch (ex: DataIntegrityViolationException) {
                throw CategoryException(CategoryErrorCode.CATEGORY_DUPLICATED)
            }
        }
    }
}
