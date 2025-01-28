package com.musinsa.musinsacodingassignment.product.service

import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity
import com.musinsa.musinsacodingassignment.brand.repository.BrandRepository
import com.musinsa.musinsacodingassignment.category.entity.CategoryEntity
import com.musinsa.musinsacodingassignment.category.repository.CategoryRepository
import com.musinsa.musinsacodingassignment.product.entity.ProductEntity
import com.musinsa.musinsacodingassignment.product.entity.toVO
import com.musinsa.musinsacodingassignment.product.repository.ProductRepository
import com.musinsa.musinsacodingassignment.product.service.vo.CreateProductVO
import com.musinsa.musinsacodingassignment.product.service.vo.UpdateProductVO
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
class ProductServiceTest {

    @Mock
    private lateinit var brandRepository: BrandRepository

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @Mock
    private lateinit var productRepository: ProductRepository

    @InjectMocks
    private lateinit var productService: ProductService

    @Test
    fun `createProduct should save and return ProductVO`() {
        val createProductVO = CreateProductVO(price = 1000, brandId = 1, categoryId = 1)
        val brandEntity = BrandEntity(id = 1, name = "Test Brand")
        val categoryEntity = CategoryEntity(id = 1, name = "Test Category")
        val savedProductEntity = ProductEntity(
            brand = brandEntity,
            category = categoryEntity,
            price = 1000
        )
        whenever(brandRepository.findById(createProductVO.brandId)).thenReturn(Optional.of(brandEntity))
        whenever(categoryRepository.findById(createProductVO.categoryId)).thenReturn(Optional.of(categoryEntity))
        whenever(productRepository.save(any<ProductEntity>())).thenReturn(savedProductEntity)

        val result = productService.createProduct(createProductVO)

        assertEquals(savedProductEntity.toVO(), result)
    }

    @Test
    fun `updateProduct should update and return ProductVO`() {
        val updateProductVO = UpdateProductVO(id = 1, price = 1500, brandId = 1, categoryId = 1)
        val brandEntity = BrandEntity(id = 1, name = "Test Brand")
        val categoryEntity = CategoryEntity(id = 1, name = "Test Category")
        val productEntity = ProductEntity( brand = brandEntity, category = categoryEntity, price = 1000)
        whenever(productRepository.findById(updateProductVO.id)).thenReturn(Optional.of(productEntity))
        whenever(brandRepository.findById(updateProductVO.brandId)).thenReturn(Optional.of(brandEntity))
        whenever(categoryRepository.findById(updateProductVO.categoryId)).thenReturn(Optional.of(categoryEntity))
        whenever(productRepository.save(any<ProductEntity>())).thenReturn(productEntity)

        val result = productService.updateProduct(updateProductVO)

        assertEquals(productEntity.toVO(), result)
    }

    @Test
    fun `getProducts should return list of ProductVO`() {
        val productEntities = listOf(
            ProductEntity(
                brand = BrandEntity(id = 1, name = "Test Brand"),
                category = CategoryEntity(id = 1, name = "Test Category"),
                price = 1000
            ),
            ProductEntity(
                brand = BrandEntity(id = 2, name = "Test Brand 2"),
                category = CategoryEntity(id = 2, name = "Test Category 2"),
                price = 2000
            )
        )
        whenever(productRepository.findAllByDeletedAtIsNull()).thenReturn(productEntities)

        val result = productService.getProducts()

        assertEquals(productEntities.map { it.toVO() }, result)
    }
}
