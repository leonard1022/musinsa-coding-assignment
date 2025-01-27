package com.musinsa.musinsacodingassignment.product.service

import com.musinsa.musinsacodingassignment.brand.repository.BrandRepository
import com.musinsa.musinsacodingassignment.category.repository.CategoryRepository
import com.musinsa.musinsacodingassignment.common.exception.NotFoundException
import com.musinsa.musinsacodingassignment.product.entity.toVO
import com.musinsa.musinsacodingassignment.product.exception.ProductErrorCode
import com.musinsa.musinsacodingassignment.product.repository.ProductRepository
import com.musinsa.musinsacodingassignment.product.service.vo.CreateProductVO
import com.musinsa.musinsacodingassignment.product.service.vo.ProductVO
import com.musinsa.musinsacodingassignment.product.service.vo.UpdateProductVO
import com.musinsa.musinsacodingassignment.product.service.vo.toEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ProductService(
    private val brandRepository: BrandRepository,
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) {

    fun getProducts(): List<ProductVO> {
        return productRepository.findAllByDeletedAtIsNull().map {
            it.toVO()
        }
    }

    @Transactional
    fun createProduct(vo: CreateProductVO): ProductVO {
        val brandEntity = findBrandById(vo.brandId)
        val categoryEntity = findCategoryById(vo.categoryId)

        return productRepository.save(vo.toEntity(brandEntity, categoryEntity)).toVO()
    }

    @Transactional
    fun updateProduct(vo: UpdateProductVO): ProductVO {
        val productEntity = findProductById(vo.id)
        val brandEntity = findBrandById(vo.brandId)
        val categoryEntity = findCategoryById(vo.categoryId)

        productEntity.brand = brandEntity
        productEntity.category = categoryEntity

        return productRepository.save(productEntity).toVO()
    }

    @Transactional
    fun deleteProduct(id: Long) {
        productRepository.deleteProductEntity(id, LocalDateTime.now())
    }

    private fun findProductById(id: Long) = productRepository.findByIdOrNull(id)
        ?: throw NotFoundException(ProductErrorCode.PRODUCT_NOT_FOUND)

    private fun findBrandById(brandId: Long) = brandRepository.findByIdOrNull(brandId)
        ?: throw NotFoundException(ProductErrorCode.BRAND_NOT_FOUND)

    private fun findCategoryById(categoryId: Long) = categoryRepository.findByIdOrNull(categoryId)
        ?: throw NotFoundException(ProductErrorCode.CATEGORY_NOT_FOUND)

}
