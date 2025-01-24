package com.musinsa.musinsacodingassignment.product.application

import com.musinsa.musinsacodingassignment.brand.domain.toDomain
import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity
import com.musinsa.musinsacodingassignment.brand.entity.CategoryEntity
import com.musinsa.musinsacodingassignment.brand.repository.BrandRepository
import com.musinsa.musinsacodingassignment.category.domain.toDomain
import com.musinsa.musinsacodingassignment.category.repository.CategoryRepository
import com.musinsa.musinsacodingassignment.product.application.vo.MinimumPriceProduct
import com.musinsa.musinsacodingassignment.product.entity.ProductEntity
import com.musinsa.musinsacodingassignment.product.repository.ProductRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.util.*

@ExtendWith(MockitoExtension::class)
class PriceServiceTest {
    private val brandRepository = mock(BrandRepository::class.java)
    private val categoryRepository = mock(CategoryRepository::class.java)
    private val productRepository = mock(ProductRepository::class.java)
    private val priceService = PriceService(brandRepository, categoryRepository, productRepository)

    @Test
    fun `getMinimumPriceByCategory should return minimum price products by category`() {
        // Given
        val category = CategoryEntity(id = 1L, name = "Category1")
        val brand = BrandEntity(id = 1L, name = "Brand1")
        val product = ProductEntity(id = 1L, price = 1000, brand = brand, category = category)

        whenever(categoryRepository.findAll()).thenReturn(listOf(category))
        whenever(productRepository.findAllByCategoryAndDeletedAtIsNull(category)).thenReturn(listOf(product))
        whenever(brandRepository.findById(brand.id)).thenReturn(Optional.of(brand))

        // When
        val result = priceService.getMinimumPriceByCategory()

        // Then
        val expected = listOf(
            MinimumPriceProduct(
                brand = brand.toDomain(),
                category = category.toDomain(),
                price = product.price
            )
        )
        assertEquals(expected, result)
    }
}
