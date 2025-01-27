package com.musinsa.musinsacodingassignment.product.presentation

import com.musinsa.musinsacodingassignment.common.exception.NotFoundException
import com.musinsa.musinsacodingassignment.product.service.ProductInquiryService
import com.musinsa.musinsacodingassignment.product.service.ProductInquiryServiceTest
import com.musinsa.musinsacodingassignment.product.service.ProductService
import com.musinsa.musinsacodingassignment.product.service.vo.CreateProductVO
import com.musinsa.musinsacodingassignment.product.service.vo.UpdateProductVO
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ProductControllerIntegrationTest(
    @Autowired
    private val productService: ProductService,

    @Autowired
    private val productInquiryService: ProductInquiryService
) {

    @Test
    @Order(1)
    @DisplayName("1) createProduct - 상품을 생성하면 DB에 저장된다.")
    @Rollback(false)
    @Transactional
    fun testCreateProduct() {
        // Given
        val createProductVO = CreateProductVO(brandId = 1, categoryId = 1, price = 1000)

        // When
        val created = productService.createProduct(createProductVO)

        // Then
        assertNotNull(created.id)
        assertEquals(1L, created.brandId)
        assertEquals(1L, created.categoryId)
        assertEquals(1000, created.price)

        val allProducts = productService.getProducts()
        assertTrue(allProducts.any { it.brandId == 1L && it.categoryId == 1L && it.price == 1000 })
    }

    @Test
    @Order(2)
    @DisplayName("2) getProducts - 생성된 상품 목록을 조회할 수 있다.")
    @Transactional
    fun testGetProducts() {
        // When
        val products = productService.getProducts()

        // Then
        assertTrue(products.isNotEmpty(), "상품 목록이 비어있지 않아야 함")
        assertTrue(products.any { it.brandId == 1L && it.categoryId == 1L && it.price == 1000 })
    }

    @Test
    @Order(3)
    @DisplayName("3) updateProduct - 상품을 수정하면 변경 사항이 DB에 반영된다.")
    @Rollback(false)
    @Transactional
    fun testUpdateProduct() {
        // Given
        val existingProduct =
            productService.getProducts().first { it.brandId == 1L && it.categoryId == 1L && it.price == 1000 }
        val updateVO = UpdateProductVO(
            id = existingProduct.id,
            brandId = 2,
            categoryId = 2,
            price = 2000
        )

        // When
        val updated = productService.updateProduct(updateVO)

        // Then
        assertEquals(2L, updated.brandId)
        assertEquals(2L, updated.categoryId)
        assertEquals(2000, updated.price)
    }

    @Test
    @Order(4)
    @DisplayName("5) getProduct - 상품을 조회할 수 있다.")
    @Transactional
    fun testGetProduct() {
        // Given
        val existingProduct =
            productService.getProducts().first { it.brandId == 2L && it.categoryId == 2L && it.price == 2000 }

        // When
        val product = productService.getProduct(existingProduct.id)

        // Then
        assertEquals(2L, product.brandId)
        assertEquals(2L, product.categoryId)
        assertEquals(2000, product.price)
    }

    @Test
    @Order(5)
    @DisplayName("4) deleteProduct - 상품을 삭제하면 더 이상 조회되지 않는다.")
    @Rollback(false)
    @Transactional
    fun testDeleteProduct() {
        // Given
        val existingProduct =
            productService.getProducts().first { it.brandId == 2L && it.categoryId == 2L && it.price == 2000 }

        // When
        productService.deleteProduct(existingProduct.id)

        // Then
        assertThrows<NotFoundException> {
            productService.getProduct(existingProduct.id)
        }
    }

    @Test
    @Order(6)
    @DisplayName("6) getProduct - 존재하지 않는 상품을 조회하면 NotFoundException이 발생한다.")
    @Transactional
    fun testGetProductWithNonExistingProduct() {
        // When & Then
        assertThrows<NotFoundException> {
            productService.getProduct(9999)
        }
    }

    @Test
    @Order(7)
    @DisplayName("7) updateProduct - 존재하지 않는 상품을 수정하면 NotFoundException이 발생한다.")
    @Transactional
    fun testUpdateProductWithNonExistingProduct() {
        // Given
        val updateVO = UpdateProductVO(
            id = 9999,
            brandId = 2,
            categoryId = 2,
            price = 2000
        )

        // When & Then
        assertThrows<NotFoundException> {
            productService.updateProduct(updateVO)
        }
    }

    @Test
    @DisplayName("8) getLowestPricesByCategory - 카테고리별 최저가 상품을 조회할 수 있다.")
    @Transactional
    fun testGetLowestPricesByCategory() {
        // Given
        val createProductVO1 = CreateProductVO(brandId = 1, categoryId = 1, price = 1000)
        val createProductVO2 = CreateProductVO(brandId = 1, categoryId = 1, price = 2000)
        val createProductVO3 = CreateProductVO(brandId = 1, categoryId = 2, price = 3000)
        val createProductVO4 = CreateProductVO(brandId = 1, categoryId = 2, price = 4000)
        productService.createProduct(createProductVO1)
        productService.createProduct(createProductVO2)
        productService.createProduct(createProductVO3)
        productService.createProduct(createProductVO4)

        // When
        val lowestPricesByCategory = productInquiryService.getLowestPricesByCategory()

        // Then
        assertEquals("상의", lowestPricesByCategory[0].categoryName)
        assertEquals("A", lowestPricesByCategory[0].brandName)
        assertEquals(1000, lowestPricesByCategory[0].price)
    }

    @Test
    @DisplayName("9) getLowestSingleBrand - 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회할 수 있다.")
    @Transactional
    fun testGetLowestSingleBrand() {
        // Given
        val createProductVO1 = CreateProductVO(brandId = 1, categoryId = 1, price = 100)
        val createProductVO2 = CreateProductVO(brandId = 1, categoryId = 2, price = 200)
        val createProductVO3 = CreateProductVO(brandId = 1, categoryId = 3, price = 300)
        val createProductVO4 = CreateProductVO(brandId = 1, categoryId = 4, price = 400)
        val createProductVO5 = CreateProductVO(brandId = 1, categoryId = 5, price = 100)
        val createProductVO6 = CreateProductVO(brandId = 1, categoryId = 6, price = 200)
        val createProductVO7 = CreateProductVO(brandId = 1, categoryId = 7, price = 300)
        val createProductVO8 = CreateProductVO(brandId = 1, categoryId = 8, price = 400)
        productService.createProduct(createProductVO1)
        productService.createProduct(createProductVO2)
        productService.createProduct(createProductVO3)
        productService.createProduct(createProductVO4)
        productService.createProduct(createProductVO5)
        productService.createProduct(createProductVO6)
        productService.createProduct(createProductVO7)
        productService.createProduct(createProductVO8)

        // When
        val lowestSingleBrand = productInquiryService.getLowestSingleBrand()

        // Then
        assertEquals("A", lowestSingleBrand.brandName)
        assertEquals(2000, lowestSingleBrand.totalPrice)
    }

    @Test
    @DisplayName("10) getMinMaxPriceByCategory - 카테고리별 최저가, 최고가 상품을 조회할 수 있다.")
    @Transactional
    fun testGetMinMaxPriceByCategory() {
        // Given
        val createProductVO1 = CreateProductVO(brandId = 1, categoryId = 1, price = 100)
        val createProductVO2 = CreateProductVO(brandId = 2, categoryId = 1, price = 200)
        val createProductVO3 = CreateProductVO(brandId = 3, categoryId = 1, price = 300)
        val createProductVO4 = CreateProductVO(brandId = 4, categoryId = 1, price = 400)
        val createProductVO5 = CreateProductVO(brandId = 5, categoryId = 1, price = 500)
        val createProductVO6 = CreateProductVO(brandId = 6, categoryId = 1, price = 600)
        val createProductVO7 = CreateProductVO(brandId = 7, categoryId = 1, price = 70000)
        val createProductVO8 = CreateProductVO(brandId = 8, categoryId = 1, price = 80000)
        productService.createProduct(createProductVO1)
        productService.createProduct(createProductVO2)
        productService.createProduct(createProductVO3)
        productService.createProduct(createProductVO4)
        productService.createProduct(createProductVO5)
        productService.createProduct(createProductVO6)
        productService.createProduct(createProductVO7)
        productService.createProduct(createProductVO8)

        // When
        val minMaxPriceByCategory = productInquiryService.getMinMaxPriceByCategory("상의")

        // Then
        assertEquals("상의", minMaxPriceByCategory.category)

        assertEquals("A", minMaxPriceByCategory.minPriceProducts[0].brand)
        assertEquals(100, minMaxPriceByCategory.minPriceProducts[0].price)

        assertEquals("H", minMaxPriceByCategory.maxPriceProducts[0].brand)
        assertEquals(80000, minMaxPriceByCategory.maxPriceProducts[0].price)
    }

}
