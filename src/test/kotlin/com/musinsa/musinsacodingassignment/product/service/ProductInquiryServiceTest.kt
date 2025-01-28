package com.musinsa.musinsacodingassignment.product.service

import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity
import com.musinsa.musinsacodingassignment.brand.repository.BrandRepository
import com.musinsa.musinsacodingassignment.category.entity.CategoryEntity
import com.musinsa.musinsacodingassignment.category.repository.CategoryRepository
import com.musinsa.musinsacodingassignment.product.entity.ProductEntity
import com.musinsa.musinsacodingassignment.product.exception.ProductErrorCode
import com.musinsa.musinsacodingassignment.product.repository.ProductRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import com.musinsa.musinsacodingassignment.common.exception.IllegalArgumentException
import com.musinsa.musinsacodingassignment.common.exception.IllegalStateException
import com.musinsa.musinsacodingassignment.common.exception.NotFoundException
import org.junit.jupiter.api.Assertions.*

@ExtendWith(MockitoExtension::class)
class ProductInquiryServiceTest {
    @Mock
    private lateinit var brandRepository: BrandRepository

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @Mock
    private lateinit var productRepository: ProductRepository

    @InjectMocks
    private lateinit var productInquiryService: ProductInquiryService

    // ------------------------
    // 테스트를 위해 간단한 엔티티/함수
    // ------------------------
    private fun createBrandEntity(id: Long, name: String): BrandEntity =
        BrandEntity(id, name)

    private fun createCategoryEntity(id: Long, name: String): CategoryEntity =
        CategoryEntity(id, name)

    private fun createProductEntity(
        id: Long = 0L,
        brand: BrandEntity,
        category: CategoryEntity,
        price: Int
    ): ProductEntity = ProductEntity(id, brand, category, price)

    // ---------------------------------------------------------------
    // 1) getLowestPricesByCategory() TEST
    // ---------------------------------------------------------------
    @Nested
    @DisplayName("getLowestPricesByCategory")
    inner class GetLowestPricesByCategoryTest {

        @Test
        @DisplayName("정상 케이스 - 카테고리별 최저가를 잘 찾는다")
        fun successCase() {
            // Given
            val brandA = createBrandEntity(1, "BrandA")
            val brandB = createBrandEntity(2, "BrandB")

            val catTop = createCategoryEntity(1, "상의")
            val catBottom = createCategoryEntity(2, "하의")

            val products = listOf(
                createProductEntity(1, brandA, catTop, 10000),
                createProductEntity(2, brandB, catTop, 9000),
                createProductEntity(3, brandA, catBottom, 8000),
                createProductEntity(4, brandB, catBottom, 12000)
            )

            // Mockito → when(...).thenReturn(...)
            whenever(categoryRepository.findAll()).thenReturn(listOf(catTop, catBottom))
            whenever(productRepository.findAllByDeletedAtIsNull()).thenReturn(products)

            // When
            val result = productInquiryService.getLowestPricesByCategory()

            // Then
            assertEquals(2, result.size)

            val topResult = result.find { it.categoryName == "상의" }
            assertNotNull(topResult)
            assertEquals("BrandB", topResult!!.brandName)
            assertEquals(9000, topResult.price)

            val bottomResult = result.find { it.categoryName == "하의" }
            assertNotNull(bottomResult)
            assertEquals("BrandA", bottomResult!!.brandName)
            assertEquals(8000, bottomResult.price)
        }

        @Test
        @DisplayName("상품이 없으면 - IllegalArgumentException(NO_PRODUCTS_AVAILABLE) 예외 발생")
        fun noProducts() {
            // Given
            val catTop = createCategoryEntity(1, "상의")
            val catBottom = createCategoryEntity(2, "하의")

            whenever(categoryRepository.findAll()).thenReturn(listOf(catTop, catBottom))
            whenever(productRepository.findAllByDeletedAtIsNull()).thenReturn(emptyList())

            // When
            val ex = assertThrows<IllegalArgumentException> {
                productInquiryService.getLowestPricesByCategory()
            }

            // Then
            assertEquals(ProductErrorCode.NO_PRODUCTS_AVAILABLE.message, ex.message)
        }

        @Test
        @DisplayName("특정 카테고리에 상품이 없는 경우에도 - IllegalArgumentException(NO_PRODUCT_FOR_CATEGORY)  예외 발생")
        fun partialMissingCategory() {
            // Given
            val brandA = createBrandEntity(1, "BrandA")
            val catTop = createCategoryEntity(1, "상의")
            val catBottom = createCategoryEntity(2, "하의")

            val products = listOf(
                createProductEntity(1, brandA, catTop, 10000) // 하의 상품 없음
            )

            val categories = listOf(
                catTop,
                catBottom
            )

            whenever(categoryRepository.findAll()).thenReturn(categories)
            whenever(productRepository.findAllByDeletedAtIsNull()).thenReturn(products)

            // When
            val ex = assertThrows<IllegalArgumentException> {
                productInquiryService.getLowestPricesByCategory()
            }

            // Then
            assertEquals(ProductErrorCode.NO_PRODUCT_FOR_CATEGORY.message, ex.message)
        }
    }

    // ---------------------------------------------------------------
    // 2) getLowestSingleBrand() TEST
    // ---------------------------------------------------------------
    @Nested
    @DisplayName("getLowestSingleBrand")
    inner class GetLowestSingleBrandTest {

        @Test
        @DisplayName("정상 케이스 - 가장 저렴한 브랜드 찾기")
        fun successCase() {
            // Given
            val brandA = createBrandEntity(1, "BrandA")
            val brandB = createBrandEntity(2, "BrandB")
            val catTop = createCategoryEntity(1, "상의")
            val catBottom = createCategoryEntity(2, "하의")

            whenever(brandRepository.findAll()).thenReturn(listOf(brandA, brandB))
            whenever(categoryRepository.findAll()).thenReturn(listOf(catTop, catBottom))

            val products = listOf(
                createProductEntity(1, brandA, catTop, 10000),
                createProductEntity(2, brandA, catBottom, 5000), // A 총합=15000
                createProductEntity(3, brandB, catTop, 8000),
                createProductEntity(4, brandB, catBottom, 4000)  // B 총합=12000
            )
            whenever(productRepository.findAllByDeletedAtIsNull()).thenReturn(products)

            // When
            val result = productInquiryService.getLowestSingleBrand()

            // Then
            assertEquals("BrandB", result.brandName)
            assertEquals(12000, result.totalPrice)
        }

        @Test
        @DisplayName("동률이면 먼저 발견된 브랜드 선택)")
        fun tieCase() {
            // Given
            val brandA = createBrandEntity(1, "BrandA")
            val brandB = createBrandEntity(2, "BrandB")
            val catTop = createCategoryEntity(1, "상의")

            whenever(brandRepository.findAll()).thenReturn(listOf(brandA, brandB))
            whenever(categoryRepository.findAll()).thenReturn(listOf(catTop))

            val products = listOf(
                createProductEntity(1, brandA, catTop, 5000),
                createProductEntity(2, brandB, catTop, 5000)
            )
            whenever(productRepository.findAllByDeletedAtIsNull()).thenReturn(products)

            // When
            val result = productInquiryService.getLowestSingleBrand()

            // Then
            assertTrue(result.brandName in listOf("BrandA", "BrandB"))
            assertEquals(5000, result.totalPrice)
        }

        @Test
        @DisplayName("제품이 없으면 IllegalStateException(NO_PRODUCTS_AVAILABLE)")
        fun noProducts() {
            // Given
            val brandA = createBrandEntity(1, "BrandA")
            val catTop = createCategoryEntity(1, "상의")

            whenever(brandRepository.findAll()).thenReturn(listOf(brandA))
            whenever(categoryRepository.findAll()).thenReturn(listOf(catTop))
            whenever(productRepository.findAllByDeletedAtIsNull()).thenReturn(emptyList())

            // When
            val ex = assertThrows<IllegalStateException> {
                productInquiryService.getLowestSingleBrand()
            }

            // Then
            assertEquals(ProductErrorCode.NO_PRODUCTS_AVAILABLE.message, ex.message)
        }

        @Test
        @DisplayName("브랜드가 아예 없는 경우도 IllegalStateException 발생")
        fun noBrands() {
            // Given
            whenever(brandRepository.findAll()).thenReturn(emptyList())

            // When
            val ex = assertThrows<IllegalStateException> {
                productInquiryService.getLowestSingleBrand()
            }

            // Then
            assertEquals(ProductErrorCode.NO_BRANDS.message, ex.message)
        }

        @Test
        @DisplayName("카테고리가 없는 경우 IllegalStateException 발생")
        fun noCategories() {
            // Given
            val brandA = createBrandEntity(1, "BrandA")

            whenever(brandRepository.findAll()).thenReturn(listOf(brandA))
            whenever(categoryRepository.findAll()).thenReturn(emptyList())

            // When
            val ex = assertThrows<IllegalStateException> {
                productInquiryService.getLowestSingleBrand()
            }

            // Then
            assertEquals(ProductErrorCode.NO_CATEGORIES.message, ex.message)
        }
    }

    // ---------------------------------------------------------------
    // 3) getMinMaxPriceByCategory() TEST
    // ---------------------------------------------------------------
    @Nested
    @DisplayName("getMinMaxPriceByCategory")
    inner class GetMinMaxPriceByCategoryTest {

        @Test
        @DisplayName("정상 케이스 - 최저가, 최고가 조회")
        fun successCase() {
            // Given
            val categoryA = createCategoryEntity(1, "상의")
            val brandA = createBrandEntity(1, "BrandA")
            val brandB = createBrandEntity(2, "BrandB")

            whenever(categoryRepository.findByName("상의")).thenReturn(categoryA)

            val products = listOf(
                createProductEntity(1, brandA, categoryA, 10000),
                createProductEntity(2, brandB, categoryA, 8000),  // min=8000
                createProductEntity(3, brandB, categoryA, 12000)   // max=12000
            )
            whenever(productRepository.findAllByCategoryAndDeletedAtIsNull(categoryA)).thenReturn(products)

            // When
            val result = productInquiryService.getMinMaxPriceByCategory("상의")

            // Then
            assertEquals("상의", result.category)

            // 최저가
            assertEquals(1, result.minPriceProducts.size)
            assertEquals("BrandB", result.minPriceProducts[0].brand)
            assertEquals(8000, result.minPriceProducts[0].price)

            // 최고가
            assertEquals(1, result.maxPriceProducts.size)
            assertEquals("BrandB", result.maxPriceProducts[0].brand)
            assertEquals(12000, result.maxPriceProducts[0].price)
        }

        @Test
        @DisplayName("카테고리가 없으면 NotFoundException(CATEGORY_NOT_FOUND)")
        fun categoryNotFound() {
            // Given
            whenever(categoryRepository.findByName("상의")).thenReturn(null)

            // When
            val ex = assertThrows<NotFoundException> {
                productInquiryService.getMinMaxPriceByCategory("상의")
            }

            // Then
            assertEquals(ProductErrorCode.CATEGORY_NOT_FOUND.message, ex.message)
        }

        @Test
        @DisplayName("카테고리는 있으나 상품이 없으면 NoSuchElementException 발생")
        fun emptyProducts() {
            // Given
            val categoryA = createCategoryEntity(1, "상의")
            whenever(categoryRepository.findByName("상의")).thenReturn(categoryA)
            whenever(productRepository.findAllByCategoryAndDeletedAtIsNull(categoryA)).thenReturn(emptyList())

            // When / Then
            assertThrows<NoSuchElementException> {
                productInquiryService.getMinMaxPriceByCategory("상의")
            }
        }

        @Test
        @DisplayName("최저가, 최고가가 여러 개 존재하면 모두 반환")
        fun multipleMinMax() {
            // Given
            val categoryA = createCategoryEntity(1, "상의")
            val brandA = createBrandEntity(1, "BrandA")
            val brandB = createBrandEntity(2, "BrandB")
            val brandC = createBrandEntity(3, "BrandC")

            whenever(categoryRepository.findByName("상의")).thenReturn(categoryA)

            val products = listOf(
                createProductEntity(1, brandA, categoryA, 8000),
                createProductEntity(2, brandB, categoryA, 8000),  // min=8000, tie
                createProductEntity(3, brandC, categoryA, 12000),
                createProductEntity(4, brandA, categoryA, 12000)   // max=12000, tie
            )
            whenever(productRepository.findAllByCategoryAndDeletedAtIsNull(categoryA)).thenReturn(products)

            // When
            val result = productInquiryService.getMinMaxPriceByCategory("상의")

            // Then
            // 최소가 = 8000 -> BrandA, BrandB
            assertEquals(2, result.minPriceProducts.size)
            val minBrands = result.minPriceProducts.map { it.brand }.toSet()
            assertEquals(setOf("BrandA", "BrandB"), minBrands)

            // 최대가 = 12000 -> BrandC, BrandA
            assertEquals(2, result.maxPriceProducts.size)
            val maxBrands = result.maxPriceProducts.map { it.brand }.toSet()
            assertEquals(setOf("BrandA", "BrandC"), maxBrands)
        }
    }
}
