package com.musinsa.musinsacodingassignment.product.service

import com.musinsa.musinsacodingassignment.brand.entity.toDomain
import com.musinsa.musinsacodingassignment.brand.repository.BrandRepository
import com.musinsa.musinsacodingassignment.category.entity.toDomain
import com.musinsa.musinsacodingassignment.category.repository.CategoryRepository
import com.musinsa.musinsacodingassignment.product.entity.toDomain
import com.musinsa.musinsacodingassignment.product.exception.ProductErrorCode
import com.musinsa.musinsacodingassignment.product.exception.ProductException
import com.musinsa.musinsacodingassignment.product.presentation.dto.response.BrandCategoryPrice
import com.musinsa.musinsacodingassignment.product.repository.ProductRepository
import com.musinsa.musinsacodingassignment.product.service.vo.*
import org.springframework.stereotype.Service

@Service
class ProductInquiryService(
    private val brandRepository: BrandRepository,
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) {

    fun getLowestPricesByCategory(): List<LowestPricesByCategoryVO> {
        return productRepository.findAllByDeletedAtIsNull().map { it.toDomain() }.groupBy { it.categoryId }
            .map { (_, products) ->
                val minProduct =
                    products.minByOrNull { it.price } ?: throw IllegalStateException("No products found for a category")

                LowestPricesByCategoryVO(
                    categoryName = minProduct.categoryName, brandName = minProduct.brandName, price = minProduct.price
                )
            }
    }

    fun getLowestSingleBrand(): LowestSingleBrandVO {
        val brands = brandRepository.findAll().map { it.toDomain() }
        val categories = categoryRepository.findAll().map { it.toDomain() }
        val allProducts = productRepository.findAllByDeletedAtIsNull().map { it.toDomain() }

        val productsByBrandAndCategory = allProducts.groupBy { it.brandName to it.categoryName }

        val brandTotals = brands.map { brand ->
            val categoryPrices = categories.map { category ->
                val productsForPair = productsByBrandAndCategory[brand.name to category.name].orEmpty()
                val minPrice = productsForPair.minOfOrNull { it.price } ?: 0
                CategoryPrice(category = category.name, price = minPrice)
            }

            val totalPrice = categoryPrices.sumOf { it.price }

            BrandCategoryPrice(
                brandName = brand.name, categories = categoryPrices, totalPrice = totalPrice
            )
        }

        val lowestSingleBrand =
            brandTotals.minByOrNull { it.totalPrice } ?: throw IllegalStateException("No products found")

        return LowestSingleBrandVO(
            brandName = lowestSingleBrand.brandName,
            categories = lowestSingleBrand.categories,
            totalPrice = lowestSingleBrand.totalPrice
        )
    }

    fun getMinMaxPriceByCategory(categoryName: String): MinMaxPriceByCategoryVO {
        val categoryEntity = categoryRepository.findByName(categoryName)
            ?: throw ProductException(ProductErrorCode.CATEGORY_NOT_FOUND)

        val products = productRepository.findAllByCategoryAndDeletedAtIsNull(categoryEntity).map { it.toDomain() }
        val category = categoryEntity.toDomain()

        val minPrice = products.minOf { it.price }
        val maxPrice = products.maxOf { it.price }

        val minPriceProducts = products
            .filter {
                it.price == minPrice
            }.map {
                BrandPrice(
                    brand = it.brandName,
                    price = it.price
                )
            }

        val maxPriceProducts = products
            .filter {
                it.price == maxPrice
            }
            .map {
                BrandPrice(
                    brand = it.brandName,
                    price = it.price
                )
            }

        return MinMaxPriceByCategoryVO(
            category = category.name,
            minPriceProducts = minPriceProducts,
            maxPriceProducts = maxPriceProducts
        )
    }
}
