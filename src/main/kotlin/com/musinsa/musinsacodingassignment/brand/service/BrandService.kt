package com.musinsa.musinsacodingassignment.brand.service

import com.musinsa.musinsacodingassignment.brand.code.BrandErrorCode
import com.musinsa.musinsacodingassignment.brand.domain.Brand
import com.musinsa.musinsacodingassignment.brand.domain.toDomain
import com.musinsa.musinsacodingassignment.brand.domain.toEntity
import com.musinsa.musinsacodingassignment.brand.exception.BrandException
import com.musinsa.musinsacodingassignment.brand.presentation.dto.request.CreateBrandRequest
import com.musinsa.musinsacodingassignment.brand.presentation.dto.request.UpdateBrandRequest
import com.musinsa.musinsacodingassignment.brand.presentation.dto.request.toBrand
import com.musinsa.musinsacodingassignment.brand.repository.BrandRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class BrandService(
    private val brandRepository: BrandRepository
) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    fun createBrand(request: CreateBrandRequest): Brand {
        if (request.name.isBlank()) {
            throw BrandException(BrandErrorCode.BRAND_NAME_IS_REQUIRED)
        }

        brandRepository.findByName(request.name)?.let {
            throw BrandException(BrandErrorCode.BRAND_ALREADY_EXISTS)
        }

        val brand = request.toBrand()
        try {
            val entity = brandRepository.save(brand.toEntity())
            return entity.toDomain()
        } catch (ex: DataIntegrityViolationException) {
            throw BrandException(BrandErrorCode.BRAND_DUPLICATED)
        }
    }

    fun getBrands(): List<Brand> {
        return brandRepository.findAll().map { it.toDomain() }
    }

    fun updateBrand(id: Long, request: UpdateBrandRequest): Brand {
        brandRepository.findById(id)
            .orElseThrow { BrandException(BrandErrorCode.BRAND_NOT_FOUND) }

        request.toBrand(id).let {
            if (it.name.isBlank()) {
                throw BrandException(BrandErrorCode.BRAND_NAME_IS_REQUIRED)
            }

            brandRepository.findByName(it.name)?.let {
                throw BrandException(BrandErrorCode.BRAND_ALREADY_EXISTS)
            }

            try {
                val updatedEntity = brandRepository.save(it.toEntity())
                return updatedEntity.toDomain()
            } catch (ex: DataIntegrityViolationException) {
                throw BrandException(BrandErrorCode.BRAND_DUPLICATED)
            }
        }
    }
}
