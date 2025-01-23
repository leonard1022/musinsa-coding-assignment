package com.musinsa.musinsacodingassignment.product.application

import com.musinsa.musinsacodingassignment.brand.repository.BrandRepository
import com.musinsa.musinsacodingassignment.category.repository.CategoryRepository
import com.musinsa.musinsacodingassignment.product.code.ProductErrorCode
import com.musinsa.musinsacodingassignment.product.domain.Product
import com.musinsa.musinsacodingassignment.product.domain.toDomain
import com.musinsa.musinsacodingassignment.product.domain.toEntity
import com.musinsa.musinsacodingassignment.product.exception.ProductException
import com.musinsa.musinsacodingassignment.product.presentation.dto.request.CreateProductRequest
import com.musinsa.musinsacodingassignment.product.presentation.dto.request.toDomain
import com.musinsa.musinsacodingassignment.product.repository.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateProductService(
    private val brandRepository: BrandRepository,
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    @Transactional
    fun createProduct(request: CreateProductRequest): Product {
        val brandEntity = brandRepository.findById(request.brandId)
            .orElseThrow { throw ProductException(ProductErrorCode.BRAND_NOT_FOUND) }
        val categoryEntity = categoryRepository.findById(request.categoryId)
            .orElseThrow { throw ProductException(ProductErrorCode.CATEGORY_NOT_FOUND) }

        val product = request.toDomain()
        try {
            return productRepository.save(product.toEntity(brandEntity, categoryEntity)).toDomain()
        } catch (e: Exception) {
            throw ProductException(ProductErrorCode.INTERNAL_SERVER_ERROR)
        }
    }
}
