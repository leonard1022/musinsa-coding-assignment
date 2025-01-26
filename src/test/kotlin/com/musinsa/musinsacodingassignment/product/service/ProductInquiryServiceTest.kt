package com.musinsa.musinsacodingassignment.product.service

import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity
import com.musinsa.musinsacodingassignment.brand.repository.BrandRepository
import com.musinsa.musinsacodingassignment.category.entity.CategoryEntity
import com.musinsa.musinsacodingassignment.category.repository.CategoryRepository
import com.musinsa.musinsacodingassignment.product.entity.ProductEntity
import com.musinsa.musinsacodingassignment.product.exception.ProductErrorCode
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
class ProductInquiryServiceTest {
    private val brandRepository = mock(BrandRepository::class.java)
    private val categoryRepository = mock(CategoryRepository::class.java)
    private val productRepository = mock(ProductRepository::class.java)
    private val productInquiryService = ProductInquiryService(brandRepository, categoryRepository, productRepository)

    @Test
    fun `getLowestPricesByCategory should return lowest prices for each category`() {
        val brand = BrandEntity(id = 1, name = "BrandA")
        val category = CategoryEntity(id = 1, name = "CategoryA")
        val products = listOf(
            ProductEntity(brand = brand, category = category, price = 1000),
            ProductEntity(brand = brand, category = category, price = 500)
        )

        whenever(productRepository.findAllByDeletedAtIsNull()).thenReturn(products)

        val result = productInquiryService.getLowestPricesByCategory()

        assertEquals(1, result.size)
        assertEquals("CategoryA", result[0].categoryName)
        assertEquals("BrandA", result[0].brandName)
        assertEquals(500, result[0].price)
    }

    @Test
    fun `getLowestSingleBrand should return brand with lowest total price`() {
        val brandA = BrandEntity(id = 1, name = "BrandA")
        val brandB = BrandEntity(id = 2, name = "BrandB")
        val category = CategoryEntity(id = 1, name = "CategoryA")
        val products = listOf(
            ProductEntity(brand = brandA, category = category, price = 1000),
            ProductEntity(brand = brandB, category = category, price = 500)
        )

        whenever(brandRepository.findAll()).thenReturn(listOf(brandA, brandB))
        whenever(categoryRepository.findAll()).thenReturn(listOf(category))
        whenever(productRepository.findAllByDeletedAtIsNull()).thenReturn(products)

        val result = productInquiryService.getLowestSingleBrand()

        assertEquals("BrandB", result.brandName)
        assertEquals(500, result.totalPrice)
    }

    @Test
    fun `getMinMaxPriceByCategory should return min and max prices for a category`() {
        val category = CategoryEntity(id = 1, name = "CategoryA")
        val brandA = BrandEntity(id = 1, name = "BrandA")
        val brandB = BrandEntity(id = 2, name = "BrandB")
        val products = listOf(
            ProductEntity(brand = brandA, category = category, price = 1000),
            ProductEntity(brand = brandB, category = category, price = 500)
        )

        whenever(categoryRepository.findByName("CategoryA")).thenReturn(category)
        whenever(productRepository.findAllByCategoryAndDeletedAtIsNull(category)).thenReturn(products)

        val result = productInquiryService.getMinMaxPriceByCategory("CategoryA")

        assertEquals("CategoryA", result.category)
        assertEquals(1, result.minPriceProducts.size)
        assertEquals("BrandB", result.minPriceProducts[0].brand)
        assertEquals(500, result.minPriceProducts[0].price)
        assertEquals(1, result.maxPriceProducts.size)
        assertEquals("BrandA", result.maxPriceProducts[0].brand)
        assertEquals(1000, result.maxPriceProducts[0].price)
    }


}
