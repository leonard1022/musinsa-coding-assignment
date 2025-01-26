package com.musinsa.musinsacodingassignment.brand.service

import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity
import com.musinsa.musinsacodingassignment.brand.entity.toVO
import com.musinsa.musinsacodingassignment.brand.repository.BrandRepository
import com.musinsa.musinsacodingassignment.brand.service.vo.CreateBrandVO
import com.musinsa.musinsacodingassignment.brand.service.vo.UpdateBrandVO
import com.musinsa.musinsacodingassignment.brand.service.vo.toEntity
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
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BrandServiceTest {
    @Mock
    private lateinit var brandRepository: BrandRepository

    @InjectMocks
    private lateinit var brandService: BrandService

    @Test
    fun `createBrand should save and return BrandVO`() {
        val createBrandVO = CreateBrandVO(name = "Test Brand")
        val brandEntity = createBrandVO.toEntity()
        val savedBrandEntity = BrandEntity(id = 1, name = brandEntity.name)
        whenever(brandRepository.save(any<BrandEntity>())).thenReturn(savedBrandEntity)

        val result = brandService.createBrand(createBrandVO)

        assertEquals(savedBrandEntity.toVO(), result)
    }

    @Test
    fun `updateBrand should update and return BrandVO`() {
        val updateBrandVO = UpdateBrandVO(id = 1, name = "Updated Brand")
        val brandEntity = updateBrandVO.toEntity()
        whenever(brandRepository.findById(updateBrandVO.id)).thenReturn(Optional.of(brandEntity))
        whenever(brandRepository.save(any<BrandEntity>())).thenReturn(brandEntity)

        val result = brandService.updateBrand(updateBrandVO)

        assertEquals(brandEntity.toVO(), result)
    }

    @Test
    fun `getBrands should return list of BrandVO`() {
        val brandEntities = listOf(
            BrandEntity(id = 1, name = "Brand 1"),
            BrandEntity(id = 2, name = "Brand 2")
        )
        whenever(brandRepository.findAllByDeletedAtIsNull()).thenReturn(brandEntities)

        val result = brandService.getBrands()

        assertEquals(brandEntities.map { it.toVO() }, result)
    }
}
