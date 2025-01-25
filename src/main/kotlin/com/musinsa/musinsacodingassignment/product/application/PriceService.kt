package com.musinsa.musinsacodingassignment.product.application

import com.musinsa.musinsacodingassignment.brand.domain.toDomain
import com.musinsa.musinsacodingassignment.brand.repository.BrandRepository
import com.musinsa.musinsacodingassignment.category.domain.toDomain
import com.musinsa.musinsacodingassignment.category.repository.CategoryRepository
import com.musinsa.musinsacodingassignment.product.application.vo.*
import com.musinsa.musinsacodingassignment.product.code.ProductErrorCode
import com.musinsa.musinsacodingassignment.product.domain.Product
import com.musinsa.musinsacodingassignment.product.domain.toDomain
import com.musinsa.musinsacodingassignment.product.entity.ProductEntity
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

    fun getAllCategoryPriceByBrand(): AllCategoryPriceByBrand {
        val brandEntities = brandRepository.findAll()
        val categoryEntities = categoryRepository.findAll()


        var resultBrandEntity = brandEntities.first()
        var resultPrice = Int.MAX_VALUE
        var resultProductEntities = mutableListOf<ProductEntity>()

        brandEntities.forEach { brandEntity ->

            var totalCategoryPrice = 0
            val totalCategoryProductEntities = mutableListOf<ProductEntity>()
            categoryEntities.forEach { categoryEntity ->
                val products =
                    productRepository.findAllByBrandAndCategoryAndDeletedAtIsNull(brandEntity, categoryEntity)

                products.isEmpty() && return@forEach

                val minPrice = products.minOf { it.price }
                val minPriceProductEntities = products.filter { it.price == minPrice }

                totalCategoryPrice += minPrice
                totalCategoryProductEntities.add(minPriceProductEntities.first())
            }

            if (totalCategoryPrice != 0 && totalCategoryPrice < resultPrice) {
                resultBrandEntity = brandEntity
                resultPrice = totalCategoryPrice
                resultProductEntities = totalCategoryProductEntities
            }
        }

        return AllCategoryPriceByBrand(
            brand = resultBrandEntity.toDomain(),
            totalPrice = resultPrice,
            categories = resultProductEntities.map {
                CategoryPrice(
                    category = it.category.toDomain(),
                    price = it.price
                )
            }
        )
    }
}
