package com.musinsa.musinsacodingassignment.category.presentation

import com.musinsa.musinsacodingassignment.category.service.CategoryService
import com.musinsa.musinsacodingassignment.category.service.vo.CreateCategoryVO
import com.musinsa.musinsacodingassignment.category.service.vo.UpdateCategoryVO
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class CategoryControllerIntegrationTest(
    @Autowired
    private val categoryService: CategoryService,
) {
    @Test
    @Order(1)
    fun testCreateCategory() {
        // Given
        val createCategoryVO = CreateCategoryVO(name = "TestCategory")

        // When
        val created = categoryService.createCategory(createCategoryVO)

        // Then
        assertNotNull(created.id)
        assertEquals("TestCategory", created.name)

        val allCategories = categoryService.getCategories()
        assertTrue(allCategories.any { it.name == "TestCategory" })
    }

    @Test
    @Order(2)
    fun testGetCategories() {
        // When
        val categories = categoryService.getCategories()

        // Then
        assertTrue(categories.isNotEmpty(), "카테고리 목록이 비어있지 않아야 함")
        assertTrue(categories.any { it.name == "TestCategory" })
    }

    @Test
    @Order(3)
    fun testUpdateCategory() {
        // Given
        val existingCategory = categoryService.getCategories().first() { it.name == "TestCategory" }
        val updateVO = UpdateCategoryVO(
            id = existingCategory.id,
            name = "UpdatedCategory"
        )

        // When
        val updated = categoryService.updateCategory(updateVO)

        // Then
        assertEquals("UpdatedCategory", updated.name)
    }
}
