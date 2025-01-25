package com.musinsa.musinsacodingassignment.product.application

import com.musinsa.musinsacodingassignment.brand.domain.toDomain
import com.musinsa.musinsacodingassignment.brand.repository.BrandRepository
import com.musinsa.musinsacodingassignment.category.domain.toDomain
import com.musinsa.musinsacodingassignment.category.repository.CategoryRepository
import com.musinsa.musinsacodingassignment.product.application.vo.BrandPrice
import com.musinsa.musinsacodingassignment.product.application.vo.CategoryPriceRange
import com.musinsa.musinsacodingassignment.product.application.vo.MinimumPriceProduct
import com.musinsa.musinsacodingassignment.product.code.ProductErrorCode
import com.musinsa.musinsacodingassignment.product.domain.Product
import com.musinsa.musinsacodingassignment.product.domain.toDomain
import com.musinsa.musinsacodingassignment.product.exception.ProductException
import com.musinsa.musinsacodingassignment.product.repository.ProductRepository
import org.springframework.stereotype.Service
import kotlin.math.min

@Service
class PriceService(
    private val brandRepository: BrandRepository,
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) {

    fun getMinimumPriceByCategory(): List<MinimumPriceProduct> {
        val categoryEntities = categoryRepository.findAll()
        val results = mutableListOf<MinimumPriceProduct>()

        categoryEntities.forEach { categoryEntity ->
            val products = productRepository.findAllByCategoryAndDeletedAtIsNull(categoryEntity)
            val minPriceProduct = products.minByOrNull { it.price }
            minPriceProduct?.let { product ->
                val brandEntity = brandRepository.findById(product.brand.id).orElseThrow {
                    ProductException(ProductErrorCode.BRAND_NOT_FOUND)
                }
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

    fun getMinimumAndMaximumBrandPrice(categoryName: String): CategoryPriceRange {
        val categoryEntity = categoryRepository.findByName(categoryName)
            ?: throw ProductException(ProductErrorCode.CATEGORY_NOT_FOUND)

        val productEntities = productRepository.findAllByCategoryAndDeletedAtIsNull(categoryEntity)

        val minPrice = productEntities.minOf { it.price }
        val maxPrice = productEntities.maxOf { it.price }
        val minPriceProductEntities = productEntities.filter { it.price == minPrice }
        val maxPriceProductEntities = productEntities.filter { it.price == maxPrice }

        return CategoryPriceRange(
            category = categoryEntity.toDomain(),
            minPriceProducts = minPriceProductEntities.map {
                BrandPrice(
                    brand = it.brand.toDomain(),
                    price = it.price
                )
            },
            maxPriceProducts = maxPriceProductEntities.map {
                BrandPrice(
                    brand = it.brand.toDomain(),
                    price = it.price
                )
            }
        )
    }
}
