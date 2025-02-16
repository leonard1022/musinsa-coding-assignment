package com.musinsa.musinsacodingassignment.brand.presentation

import com.musinsa.musinsacodingassignment.brand.service.BrandService
import com.musinsa.musinsacodingassignment.brand.service.vo.CreateBrandVO
import com.musinsa.musinsacodingassignment.brand.service.vo.UpdateBrandVO
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class BrandControllerIntegrationTest(
    @Autowired
    private val brandService: BrandService,
) {
    @Test
    @DisplayName("1) createBrand - 브랜드를 생성하면 DB에 저장된다.")
    fun testCreateBrand() {
        // Given
        val createBrandVO = CreateBrandVO(name = "TestBrand")

        // When
        val created = brandService.createBrand(createBrandVO)

        // Then
        assertNotNull(created.id)
        assertEquals("TestBrand", created.name)

        val allBrands = brandService.getBrands()
        assertTrue(allBrands.any { it.name == "TestBrand" })
    }

    @Test
    @DisplayName("2) getBrands - 생성된 브랜드 목록을 조회할 수 있다.")
    fun testGetBrands() {
        // When
        val brands = brandService.getBrands()

        // Then
        assertTrue(brands.isNotEmpty(), "브랜드 목록이 비어있지 않아야 함")
        assertTrue(brands.any { it.name == "TestBrand" })
    }

    @Test
    @DisplayName("3) updateBrand - 브랜드를 수정하면 변경 사항이 DB에 반영된다.")
    fun testUpdateBrand() {
        // Given
        val existingBrand = brandService.getBrand(1L)
        val updateVO = UpdateBrandVO(
            id = existingBrand.id,
            name = "UpdatedBrand"
        )

        // When
        val updated = updateVO.let { brandService.updateBrand(it) }

        // Then
        assertEquals(existingBrand.id, updated.id)
        assertEquals("UpdatedBrand", updated.name)

        val found = brandService.getBrands().firstOrNull { it.id == existingBrand.id }
        assertNotNull(found)
        assertEquals("UpdatedBrand", found!!.name)
    }

    @Test
    @DisplayName("4) deleteBrand - 브랜드를 삭제하면 더 이상 조회되지 않는다.")
    fun testDeleteBrand() {
        // Given
        val existingBrand = brandService.getBrand(1L)

        // When
        brandService.deleteBrand(existingBrand.id)

        // Then
        val afterDelete = brandService.getBrands()
        assertFalse(afterDelete.any { it.id == existingBrand.id })
    }
}
