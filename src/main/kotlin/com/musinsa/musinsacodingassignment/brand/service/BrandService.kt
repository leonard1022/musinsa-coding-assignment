package com.musinsa.musinsacodingassignment.brand.service

import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity
import com.musinsa.musinsacodingassignment.brand.entity.toVO
import com.musinsa.musinsacodingassignment.brand.exception.BrandErrorCode
import com.musinsa.musinsacodingassignment.brand.repository.BrandRepository
import com.musinsa.musinsacodingassignment.brand.service.vo.BrandVO
import com.musinsa.musinsacodingassignment.brand.service.vo.CreateBrandVO
import com.musinsa.musinsacodingassignment.brand.service.vo.UpdateBrandVO
import com.musinsa.musinsacodingassignment.brand.service.vo.toEntity
import com.musinsa.musinsacodingassignment.common.exception.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BrandService(
    private val brandRepository: BrandRepository
) {

    @Transactional(readOnly = true)
    fun getBrand(id: Long): BrandVO {
        return brandRepository.findByIdOrNull(id)?.toVO()
            ?: throw NotFoundException(BrandErrorCode.BRAND_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    fun getBrands(): List<BrandVO> {
        return brandRepository.findAllByDeletedAtIsNull().map { it.toVO() }
    }

    @Transactional
    fun createBrand(vo: CreateBrandVO): BrandVO {
        return saveBrand(vo.toEntity()).toVO()
    }

    @Transactional
    fun updateBrand(vo: UpdateBrandVO): BrandVO {
        findBrandById(vo.id)
        val brandEntity = vo.toEntity()
        return saveBrand(brandEntity).toVO()
    }

    @Transactional
    fun deleteBrand(id: Long) {
        val brandEntity = findBrandById(id)
        brandRepository.deleteBrandEntity(brandEntity.id)
    }

    private fun findBrandById(id: Long) = brandRepository.findByIdOrNull(id)
        ?: throw NotFoundException(BrandErrorCode.BRAND_NOT_FOUND)

    private fun saveBrand(brandEntity: BrandEntity): BrandEntity {
        return brandRepository.save(brandEntity)
    }
}
