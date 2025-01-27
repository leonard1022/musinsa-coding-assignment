package com.musinsa.musinsacodingassignment.category.service

import com.musinsa.musinsacodingassignment.category.entity.CategoryEntity
import com.musinsa.musinsacodingassignment.category.entity.toVO
import com.musinsa.musinsacodingassignment.category.repository.CategoryRepository
import com.musinsa.musinsacodingassignment.category.service.vo.CreateCategoryVO
import com.musinsa.musinsacodingassignment.category.service.vo.UpdateCategoryVO
import com.musinsa.musinsacodingassignment.category.service.vo.toEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
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
    fun `createCategory should save and return CategoryVO`() {
        val createCategoryVO = CreateCategoryVO(name = "Test Category")
        val categoryEntity = createCategoryVO.toEntity(0)
        val savedCategoryEntity = CategoryEntity(id = 1, name = categoryEntity.name)
        whenever(categoryRepository.save(any<CategoryEntity>())).thenReturn(savedCategoryEntity)

        val result = categoryService.createCategory(createCategoryVO)

        assertEquals(savedCategoryEntity.toVO(), result)
    }

    @Test
    fun `updateCategory should update and return CategoryVO`() {
        val updateCategoryVO = UpdateCategoryVO(id = 1, name = "Updated Category")
        val categoryEntity = updateCategoryVO.toEntity()
        whenever(categoryRepository.findById(updateCategoryVO.id)).thenReturn(Optional.of(categoryEntity))
        whenever(categoryRepository.save(any<CategoryEntity>())).thenReturn(categoryEntity)

        val result = categoryService.updateCategory(updateCategoryVO)

        assertEquals(categoryEntity.toVO(), result)
    }
}
