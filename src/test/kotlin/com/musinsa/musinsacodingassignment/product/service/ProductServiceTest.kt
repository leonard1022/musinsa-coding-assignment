package com.musinsa.musinsacodingassignment.product.service

import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity
import com.musinsa.musinsacodingassignment.category.entity.CategoryEntity
import com.musinsa.musinsacodingassignment.product.entity.ProductEntity
import com.musinsa.musinsacodingassignment.product.repository.ProductRepository
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.kotlin.whenever
import org.mockito.quality.Strictness
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProductServiceTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @InjectMocks
    private lateinit var productService: ProductService

    @Test
    fun `getProducts should return list of products`() {
        // Given
        val productEntities = listOf(
            ProductEntity(
                id = 1L,
                brand = BrandEntity(
                    id = 1L,
                    name = "Brand 1"
                ),
                category = CategoryEntity(
                    id = 1L,
                    name = "Category 1"
                ),
                price = 100,
            ),
            ProductEntity(
                id = 2L,
                brand = BrandEntity(
                    id = 2L,
                    name = "Brand 2"
                ),
                category = CategoryEntity(
                    id = 2L,
                    name = "Category 2"
                ),
                price = 200,
            )
        )
        whenever(productRepository.findAllByDeletedAtIsNull()).thenReturn(productEntities)

        // When
        val result = productService.getProducts()

        // Then
        assertEquals(2, result.size)
        assertEquals(1L, result[0].id)
        assertEquals(2L, result[1].id)
    }

    @Test
    fun `deleteProduct should update deletedAt field`() {
        // Given
        val productId = 1L
        val now = LocalDateTime.now()
        whenever(productRepository.deleteProductEntity(productId, now)).thenReturn(1)

        // When
        assertDoesNotThrow {
            productService.deleteProduct(productId)
        }

        // Then
        val captor = ArgumentCaptor.forClass(LocalDateTime::class.java)
        verify(productRepository, times(1)).deleteProductEntity(eq(productId), captor.capture())
        assertEquals(now.toLocalDate(), captor.value.toLocalDate())
    }
}
