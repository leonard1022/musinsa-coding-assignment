package com.musinsa.musinsacodingassignment.product.application

import com.musinsa.musinsacodingassignment.brand.domain.toDomain
import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity
import com.musinsa.musinsacodingassignment.brand.repository.BrandRepository
import com.musinsa.musinsacodingassignment.category.entity.CategoryEntity
import com.musinsa.musinsacodingassignment.category.entity.toDomain
import com.musinsa.musinsacodingassignment.category.repository.CategoryRepository
import com.musinsa.musinsacodingassignment.product.application.vo.*
import com.musinsa.musinsacodingassignment.product.code.ProductErrorCode
import com.musinsa.musinsacodingassignment.product.entity.ProductEntity
import com.musinsa.musinsacodingassignment.product.exception.ProductException
import com.musinsa.musinsacodingassignment.product.repository.ProductRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class PriceServiceTest {
    private val brandRepository = mock(BrandRepository::class.java)
    private val categoryRepository = mock(CategoryRepository::class.java)
    private val productRepository = mock(ProductRepository::class.java)
    private val priceService = PriceService(brandRepository, categoryRepository, productRepository)

    @Test
    fun `getMinimumPriceByCategory should return minimum price products by category`() {
        // Given
        val category1 = CategoryEntity(id = 1L, name = "Category1")
        val category2 = CategoryEntity(id = 2L, name = "Category2")
        val brand1 = BrandEntity(id = 1L, name = "Brand1")
        val brand2 = BrandEntity(id = 2L, name = "Brand2")
        val product1 = ProductEntity(id = 1L, price = 1000, brand = brand1, category = category1)
        val product2 = ProductEntity(id = 2L, price = 2000, brand = brand2, category = category1)
        val product3 = ProductEntity(id = 3L, price = 1500, brand = brand1, category = category2)
        val product4 = ProductEntity(id = 4L, price = 2500, brand = brand2, category = category2)

        whenever(categoryRepository.findAll()).thenReturn(listOf(category1, category2))
        whenever(productRepository.findAllByDeletedAtIsNull()).thenReturn(
            listOf(
                product1,
                product2,
                product3,
                product4
            )
        )

        // When
        val result = priceService.getMinimumPriceByCategory()

        // Then
        val expected = listOf(
            MinimumPriceProduct(
                brand = brand1.toDomain(),
                category = category1.toDomain(),
                price = product1.price
            ),
            MinimumPriceProduct(
                brand = brand1.toDomain(),
                category = category2.toDomain(),
                price = product3.price
            )
        )
        assertEquals(expected.sumOf { it.price }, result.sumOf { it.price })
    }

    @Test
    fun `getMinimumAndMaximumBrandPriced should return minimum and maximum price products by category`() {
        // Given
        val category = CategoryEntity(id = 1L, name = "Category1")
        val brand1 = BrandEntity(id = 1L, name = "Brand1")
        val brand2 = BrandEntity(id = 2L, name = "Brand2")
        val product1 = ProductEntity(id = 1L, price = 1000, brand = brand1, category = category)
        val product2 = ProductEntity(id = 2L, price = 2000, brand = brand2, category = category)

        whenever(categoryRepository.findByName("Category1")).thenReturn(category)
        whenever(productRepository.findAllByCategoryAndDeletedAtIsNull(category)).thenReturn(listOf(product1, product2))

        // When
        val result = priceService.getMinimumAndMaximumBrandPrice("Category1")

        // Then
        val expected = CategoryPriceRange(
            category = category.toDomain(),
            minPriceProducts = listOf(BrandPrice(brand = brand1.toDomain(), price = 1000)),
            maxPriceProducts = listOf(BrandPrice(brand = brand2.toDomain(), price = 2000))
        )
        assertEquals(expected, result)
    }

    @Test
    fun `getMinimumAndMaximumBrandPriced should throw ProductException when category not found`() {
        // Given
        whenever(categoryRepository.findByName("InvalidCategory")).thenReturn(null)

        // When & Then
        val exception = assertThrows<ProductException> {
            priceService.getMinimumAndMaximumBrandPrice("InvalidCategory")
        }
        assertEquals(ProductErrorCode.CATEGORY_NOT_FOUND.code, exception.code)
    }

    @Test
    fun `getAllCategoryPriceByBrand should return correct response`() {
        // Given
        val brand = BrandEntity(id = 1L, name = "Brand1")
        val category1 = CategoryEntity(id = 1L, name = "Category1")
        val category2 = CategoryEntity(id = 2L, name = "Category2")
        val product1 = ProductEntity(id = 1L, price = 1000, brand = brand, category = category1)
        val product2 = ProductEntity(id = 2L, price = 2000, brand = brand, category = category2)

        whenever(brandRepository.findAll()).thenReturn(listOf(brand))
        whenever(categoryRepository.findAll()).thenReturn(listOf(category1, category2))
        whenever(productRepository.findAllByDeletedAtIsNull()).thenReturn(listOf(product1, product2))

        // When
        val result = priceService.getAllCategoryPriceByBrand()

        // Then
        val expected = AllCategoryPriceByBrand(
            brand = brand.toDomain(),
            totalPrice = 3000,
            categories = listOf(
                CategoryPrice(category = category1.toDomain(), price = 1000),
                CategoryPrice(category = category2.toDomain(), price = 2000)
            )
        )
        assertEquals(expected, result)
    }

    @Test
    fun `getAllCategoryPriceByBrand should return correct response with multiple data`() {
        // Given
        val brand1 = BrandEntity(id = 1L, name = "Brand1")
        val brand2 = BrandEntity(id = 2L, name = "Brand2")
        val category1 = CategoryEntity(id = 1L, name = "Category1")
        val category2 = CategoryEntity(id = 2L, name = "Category2")
        val category3 = CategoryEntity(id = 3L, name = "Category3")
        val product1 = ProductEntity(id = 1L, price = 1000, brand = brand1, category = category1)
        val product2 = ProductEntity(id = 2L, price = 2000, brand = brand1, category = category2)
        val product3 = ProductEntity(id = 3L, price = 1500, brand = brand2, category = category1)
        val product4 = ProductEntity(id = 4L, price = 2500, brand = brand2, category = category2)
        val product5 = ProductEntity(id = 5L, price = 500, brand = brand1, category = category3)
        val product6 = ProductEntity(id = 6L, price = 3000, brand = brand2, category = category3)

        whenever(brandRepository.findAll()).thenReturn(listOf(brand1, brand2))
        whenever(categoryRepository.findAll()).thenReturn(listOf(category1, category2, category3))
        whenever(productRepository.findAllByDeletedAtIsNull()).thenReturn(
            listOf(
                product1,
                product2,
                product3,
                product4,
                product5,
                product6
            )
        )

        // When
        val result = priceService.getAllCategoryPriceByBrand()

        // Then
        val expected = AllCategoryPriceByBrand(
            brand = brand1.toDomain(),
            totalPrice = 3500,
            categories = listOf(
                CategoryPrice(category = category1.toDomain(), price = 1000),
                CategoryPrice(category = category2.toDomain(), price = 2000),
                CategoryPrice(category = category3.toDomain(), price = 500)
            )
        )
        assertEquals(expected, result)
    }
}
