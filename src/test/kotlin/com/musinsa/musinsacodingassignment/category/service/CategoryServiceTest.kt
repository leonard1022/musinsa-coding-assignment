package com.musinsa.musinsacodingassignment.category.service

import com.musinsa.musinsacodingassignment.brand.entity.CategoryEntity
import com.musinsa.musinsacodingassignment.category.code.CategoryErrorCode
import com.musinsa.musinsacodingassignment.category.domain.Category
import com.musinsa.musinsacodingassignment.category.domain.toEntity
import com.musinsa.musinsacodingassignment.category.exception.CategoryException
import com.musinsa.musinsacodingassignment.category.presentation.dto.request.CreateCategoryRequest
import com.musinsa.musinsacodingassignment.category.presentation.dto.request.UpdateCategoryRequest
import com.musinsa.musinsacodingassignment.category.presentation.dto.request.toCategory
import com.musinsa.musinsacodingassignment.category.repository.CategoryRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.mockito.quality.Strictness
import java.util.*

@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CategoryServiceTest {

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @InjectMocks
    private lateinit var categoryService: CategoryService

    @Test
    fun `createCategory should create and return category`() {
        // Given
        val request = CreateCategoryRequest(name = "New Category")
        val category = request.toCategory()

        val categoryEntity = category.toEntity()
        whenever(categoryRepository.findByName(request.name)).thenReturn(null)
        whenever(categoryRepository.save(any<CategoryEntity>())).thenReturn(categoryEntity)

        // When
        val result = categoryService.createCategory(request)

        // Then
        assertEquals("New Category", result.name)
    }

    @Test
    fun `createCategory should throw exception when name is blank`() {
        // Given
        val request = CreateCategoryRequest(name = "")

        // When & Then
        val exception = assertThrows<CategoryException> {
            categoryService.createCategory(request)
        }
        assertEquals(CategoryErrorCode.CATEGORY_NAME_IS_REQUIRED.code, exception.code)
    }

    @Test
    fun `createCategory should throw exception when category already exists`() {
        // Given
        val request = CreateCategoryRequest(name = "Existing Category")
        val existingCategoryEntity = Category(
            id = 0,
            name = "Existing Category"
        ).toEntity()
        whenever(categoryRepository.findByName(request.name)).thenReturn(existingCategoryEntity)

        // When & Then
        val exception = assertThrows<CategoryException> {
            categoryService.createCategory(request)
        }
        assertEquals(CategoryErrorCode.CATEGORY_ALREADY_EXISTS.code, exception.code)
    }

    @Test
    fun `getCategories should return list of categories`() {
        // Given
        val categoryEntities = listOf(
            Category(
                id = 0,
                name = "Category 1"
            ).toEntity(),
            Category(
                id = 0,
                name = "Category 2"
            ).toEntity()
        )
        whenever(categoryRepository.findAll()).thenReturn(categoryEntities)

        // When
        val result = categoryService.getCategories()

        // Then
        assertEquals(2, result.size)
        assertEquals("Category 1", result[0].name)
        assertEquals("Category 2", result[1].name)
    }

    @Test
    fun `updateCategory should update and return category`() {
        // Given
        val id = 1L
        val request = UpdateCategoryRequest(name = "Updated Category")
        val category = request.toCategory(id)
        val existingCategoryEntity = category.toEntity().apply { this.id = id }
        val updatedCategoryEntity = category.toEntity().apply { this.id = id }
        whenever(categoryRepository.findById(id)).thenReturn(Optional.of(existingCategoryEntity))
        whenever(categoryRepository.save(any<CategoryEntity>())).thenReturn(updatedCategoryEntity)

        // When
        val result = categoryService.updateCategory(id, request)

        // Then
        assertEquals("Updated Category", result.name)
    }

    @Test
    fun `updateCategory should throw exception when category not found`() {
        // Given
        val id = 1L
        val request = UpdateCategoryRequest(name = "Updated Category")
        whenever(categoryRepository.findById(id)).thenReturn(Optional.empty())

        // When
        val exception = assertThrows<CategoryException> {
            categoryService.updateCategory(id, request)
        }

        // Then
        assertEquals(CategoryErrorCode.CATEGORY_NOT_FOUND.code, exception.code)
    }

    @Test
    fun `updateCategory should throw exception when name is blank`() {
        // Given
        val id = 1L
        val request = UpdateCategoryRequest(name = "")
        val category = request.toCategory(id)
        val existingCategoryEntity = category.toEntity().apply { this.id = id }
        whenever(categoryRepository.findById(id)).thenReturn(Optional.of(existingCategoryEntity))

        // When
        val exception = assertThrows<CategoryException> {
            categoryService.updateCategory(id, request)
        }

        // Then
        assertEquals(CategoryErrorCode.CATEGORY_NAME_IS_REQUIRED.code, exception.code)
    }

    @Test
    fun `updateCategory should throw exception when category alr ddddddeady exists`() {
        // Given
        val id = 1L
        val request = UpdateCategoryRequest(name = "Existing Category")
        val category = request.toCategory(id)
        val existingCategoryEntity = category.toEntity().apply { this.id = id }
        val duplicateCategoryEntity = category.toEntity()
        whenever(categoryRepository.findById(id)).thenReturn(Optional.of(existingCategoryEntity))
        whenever(categoryRepository.findByName(request.name)).thenReturn(duplicateCategoryEntity)

        // When
        val exception = assertThrows<CategoryException> {
            categoryService.updateCategory(id, request)
        }

        // Then
        assertEquals(CategoryErrorCode.CATEGORY_ALREADY_EXISTS.code, exception.code)
    }
}
