package com.musinsa.musinsacodingassignment.brand.service

import com.musinsa.musinsacodingassignment.brand.code.BrandErrorCode
import com.musinsa.musinsacodingassignment.brand.domain.Brand
import com.musinsa.musinsacodingassignment.brand.entity.toDomain
import com.musinsa.musinsacodingassignment.brand.entity.toEntity
import com.musinsa.musinsacodingassignment.brand.exception.BrandException
import com.musinsa.musinsacodingassignment.brand.presentation.dto.request.CreateBrandRequest
import com.musinsa.musinsacodingassignment.brand.presentation.dto.request.UpdateBrandRequest
import com.musinsa.musinsacodingassignment.brand.presentation.dto.request.toDomain
import com.musinsa.musinsacodingassignment.brand.repository.BrandRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BrandService(
    private val brandRepository: BrandRepository
) {

    fun getBrands(): List<Brand> {
        return brandRepository.findAllByDeletedAtIsNull().map { it.toDomain() }
    }

    @Transactional
    fun createBrand(request: CreateBrandRequest): Brand {
        validateBrandName(request.name)
        checkBrandExistsByName(request.name)

        val brand = request.toDomain()
        return saveBrand(brand)
    }

    @Transactional
    fun updateBrand(id: Long, request: UpdateBrandRequest): Brand {
        val brandEntity = brandRepository.findById(id)
            .orElseThrow { BrandException(BrandErrorCode.BRAND_NOT_FOUND) }

        validateBrandName(request.name)
        checkBrandExistsByName(request.name)

        brandEntity.toDomain().updateName(request.name).let {
            return saveBrand(it)
        }
    }

    @Transactional
    fun deleteBrand(id: Long) {
        val brand = brandRepository.findByIdOrNull(id)
            ?: throw BrandException(BrandErrorCode.BRAND_NOT_FOUND)
        brandRepository.deleteBrandEntity(brand.id)
    }

    private fun validateBrandName(name: String) {
        if (name.isBlank()) {
            throw BrandException(BrandErrorCode.BRAND_NAME_IS_REQUIRED)
        }
    }

    private fun checkBrandExistsByName(name: String) {
        brandRepository.findByName(name)?.let {
            throw BrandException(BrandErrorCode.BRAND_ALREADY_EXISTS)
        }
    }

    private fun saveBrand(brand: Brand): Brand {
        return try {
            val entity = brandRepository.save(brand.toEntity())
            entity.toDomain()
        } catch (ex: DataIntegrityViolationException) {
            throw BrandException(BrandErrorCode.BRAND_DUPLICATED)
        }
    }
}
