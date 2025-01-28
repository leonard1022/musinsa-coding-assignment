package com.musinsa.musinsacodingassignment.product.repository

import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity
import com.musinsa.musinsacodingassignment.category.entity.CategoryEntity
import com.musinsa.musinsacodingassignment.brand.repository.BrandRepository
import com.musinsa.musinsacodingassignment.category.repository.CategoryRepository
import com.musinsa.musinsacodingassignment.product.entity.ProductEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var brandRepository: BrandRepository

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @Test
    fun `findALlByCategoryAndDeletedAtIsNull should return products with given categoryId and null deletedAt`() {
        // given
        val categoryId = 0L
        val brand = BrandEntity(name = "Test Brand")
        val category = CategoryEntity(id = categoryId, name = "Test Category")

        // Save brand and category first
        val brandEntity = brandRepository.save(brand)
        val categoryEntity = categoryRepository.save(category)

        val product1 = ProductEntity(brand = brandEntity, category = categoryEntity, price = 1000)
        val product2 = ProductEntity(brand = brandEntity, category = categoryEntity, price = 2000)
        val product3 = ProductEntity(brand = brandEntity, category = categoryEntity, price = 3000)
        productRepository.saveAll(listOf(product1, product2, product3))

        // when
        val result = productRepository.findAllByCategoryAndDeletedAtIsNull(categoryEntity)

        // then
        assertEquals(3, result.size)
    }
}
