package com.musinsa.musinsacodingassignment.brand.service

import com.musinsa.musinsacodingassignment.brand.code.BrandErrorCode
import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity
import com.musinsa.musinsacodingassignment.brand.exception.BrandException
import com.musinsa.musinsacodingassignment.brand.presentation.dto.request.CreateBrandRequest
import com.musinsa.musinsacodingassignment.brand.presentation.dto.request.UpdateBrandRequest
import com.musinsa.musinsacodingassignment.brand.presentation.dto.request.toDomain
import com.musinsa.musinsacodingassignment.brand.repository.BrandRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
class BrandServiceTest {
    @Mock
    private lateinit var brandRepository: BrandRepository

    @InjectMocks
    private lateinit var brandService: BrandService

    @Test
    fun `createBrand should save and return brand`() {
        // Given
        val request = CreateBrandRequest(name = "Test Brand")
        val brand = request.toDomain()
        val brandEntity = BrandEntity(name = "Test Brand")
        whenever(brandRepository.save(any<BrandEntity>())).thenReturn(brandEntity)

        // When
        val result = brandService.createBrand(request)

        // Then
        assertEquals(brand, result)
    }

    @Test
    fun `createBrand should throw exception for duplicate name`() {
        // Given
        val request = CreateBrandRequest(name = "Duplicate Brand")
        whenever(brandRepository.save(any<BrandEntity>()))
            .thenThrow(BrandException(BrandErrorCode.BRAND_DUPLICATED))

        // When
        val exception = assertThrows<BrandException> {
            brandService.createBrand(request)
        }

        // Then
        assertEquals(BrandErrorCode.BRAND_DUPLICATED.message, exception.message)
    }

    @Test
    fun `createBrand should throw exception for empty name`() {
        // Given
        val request = CreateBrandRequest(name = "")

        // When
        val exception = assertThrows<BrandException> {
            brandService.createBrand(request)
        }

        // Then
        assertEquals(BrandErrorCode.BRAND_NAME_IS_REQUIRED.message, exception.message)
    }

    @Test
    fun `getBrands should return list of brands`() {
        // Given
        val brandEntities = listOf(
            BrandEntity(name = "Brand 1"),
            BrandEntity(name = "Brand 2")
        )
        whenever(brandRepository.findAllByDeletedAtIsNull()).thenReturn(brandEntities)

        // When
        val result = brandService.getBrands()

        // Then
        assertEquals(2, result.size)
        assertEquals("Brand 1", result[0].name)
        assertEquals("Brand 2", result[1].name)
    }

    @Test
    fun `updateBrand should update and return brand`() {
        // Given
        val id = 1L
        val request = UpdateBrandRequest(name = "Updated Brand")
        val existingBrandEntity = BrandEntity(name = "Old Brand").apply { this.id = id }
        val updatedBrandEntity = BrandEntity(name = "Updated Brand").apply { this.id = id }
        whenever(brandRepository.findById(id)).thenReturn(Optional.of(existingBrandEntity))
        whenever(brandRepository.save(any<BrandEntity>())).thenReturn(updatedBrandEntity)

        // When
        val result = brandService.updateBrand(id, request)

        // Then
        assertEquals("Updated Brand", result.name)
    }
}
