package com.musinsa.musinsacodingassignment.category.service

import com.musinsa.musinsacodingassignment.category.entity.toDomain
import com.musinsa.musinsacodingassignment.category.entity.toEntity
import com.musinsa.musinsacodingassignment.category.code.CategoryErrorCode
import com.musinsa.musinsacodingassignment.category.domain.Category
import com.musinsa.musinsacodingassignment.category.exception.CategoryException
import com.musinsa.musinsacodingassignment.category.presentation.dto.request.CreateCategoryRequest
import com.musinsa.musinsacodingassignment.category.presentation.dto.request.UpdateCategoryRequest
import com.musinsa.musinsacodingassignment.category.presentation.dto.request.toDomain
import com.musinsa.musinsacodingassignment.category.repository.CategoryRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {

    fun getCategories(): List<Category> {
        return categoryRepository.findAll().map { it.toDomain() }
    }

    @Transactional
    fun createCategory(request: CreateCategoryRequest): Category {
        validateCategoryName(request.name)
        checkCategoryExistsByName(request.name)

        val category = request.toDomain()
        return saveCategory(category)
    }

    @Transactional
    fun updateCategory(id: Long, request: UpdateCategoryRequest): Category {
        val categoryEntity = categoryRepository.findByIdOrNull(id)
            ?: throw CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND)

        validateCategoryName(request.name)
        checkCategoryExistsByName(request.name)

        categoryEntity.toDomain().updateName(request.name).let {
            return saveCategory(it)
        }
    }

    private fun validateCategoryName(name: String) {
        if (name.isBlank()) {
            throw CategoryException(CategoryErrorCode.CATEGORY_NAME_IS_REQUIRED)
        }
    }

    private fun checkCategoryExistsByName(name: String) {
        categoryRepository.existsByName(name).let {
            if (it) {
                throw CategoryException(CategoryErrorCode.CATEGORY_ALREADY_EXISTS)
            }
        }
    }

    private fun saveCategory(category: Category): Category {
        return try {
            val entity = categoryRepository.save(category.toEntity())
            entity.toDomain()
        } catch (ex: DataIntegrityViolationException) {
            throw CategoryException(CategoryErrorCode.CATEGORY_DUPLICATED)
        } catch (e: Exception) {
            throw CategoryException(CategoryErrorCode.INTERNAL_SERVER_ERROR)
        }
    }
}
