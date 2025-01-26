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

    fun createCategory(request: CreateCategoryRequest): Category {
        validateCategoryName(request.name)
        checkCategoryExistsByName(request.name)

        val category = request.toCategory()
        return saveCategory(category)
    }

    fun getCategories(): List<Category> {
        return categoryRepository.findAll().map { it.toDomain() }
    }

    fun updateCategory(id: Long, request: UpdateCategoryRequest): Category {
        categoryRepository.findById(id)
            .orElseThrow { CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND) }

        validateCategoryName(request.name)
        checkCategoryExistsByName(request.name)

        val updatedCategory = request.toCategory(id)
        return saveCategory(updatedCategory)
    }

    private fun validateCategoryName(name: String) {
        if (name.isBlank()) {
            throw CategoryException(CategoryErrorCode.CATEGORY_NAME_IS_REQUIRED)
        }
    }

    private fun checkCategoryExistsByName(name: String) {
        categoryRepository.findByName(name)?.let {
            throw CategoryException(CategoryErrorCode.CATEGORY_ALREADY_EXISTS)
        }
    }

    private fun saveCategory(category: Category): Category {
        return try {
            val entity = categoryRepository.save(category.toEntity())
            entity.toDomain()
        } catch (ex: DataIntegrityViolationException) {
            throw CategoryException(CategoryErrorCode.CATEGORY_DUPLICATED)
        }
    }
}
