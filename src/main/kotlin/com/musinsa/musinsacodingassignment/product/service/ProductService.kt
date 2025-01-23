package com.musinsa.musinsacodingassignment.product.service

import com.musinsa.musinsacodingassignment.product.domain.Product
import com.musinsa.musinsacodingassignment.product.domain.toDomain
import com.musinsa.musinsacodingassignment.product.repository.ProductRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    fun getProducts(): List<Product> {
        return productRepository.findAllByDeletedAtIsNull().map {
            it.toDomain()
        }
    }

    fun deleteProduct(id: Long) {
        productRepository.deleteProductEntity(id, LocalDateTime.now())
    }
}
