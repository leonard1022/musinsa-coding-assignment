package com.musinsa.musinsacodingassignment.product.application

import com.musinsa.musinsacodingassignment.brand.domain.toDomain
import com.musinsa.musinsacodingassignment.brand.repository.BrandRepository
import com.musinsa.musinsacodingassignment.category.domain.toDomain
import com.musinsa.musinsacodingassignment.category.repository.CategoryRepository
import com.musinsa.musinsacodingassignment.product.application.vo.MinimumPriceProduct
import com.musinsa.musinsacodingassignment.product.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class PriceService(
    private val brandRepository: BrandRepository,
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) {

    fun getMinimumPriceByCategory(): MutableList<MinimumPriceProduct> {
        val categoryEntities = categoryRepository.findAll()
        val results = mutableListOf<MinimumPriceProduct>()


        categoryEntities.forEach { categoryEntity ->
            val products = productRepository.findALlByCategoryAndDeletedAtIsNull(categoryEntity)
            val minPriceProduct = products.minByOrNull { it.price }
            
            minPriceProduct?.let { product ->
                val brandEntity = brandRepository.findById(product.brand.id).get()
                results.add(
                    MinimumPriceProduct(
                        brand = brandEntity.toDomain(),
                        category = categoryEntity.toDomain(),
                        price = product.price
                    )
                )
            }
        }

        return results
    }
}
