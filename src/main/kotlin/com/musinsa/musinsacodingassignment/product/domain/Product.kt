package com.musinsa.musinsacodingassignment.product.domain

import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity
import com.musinsa.musinsacodingassignment.category.entity.CategoryEntity
import com.musinsa.musinsacodingassignment.product.entity.ProductEntity

class Product(
    var id: Long?,
    var brandId: Long,
    var categoryId: Long,
    var price: Int
) {
    fun updateBrandId(brandId: Long): Product {
        this.brandId = brandId
        return this
    }

    fun updateCategoryId(categoryId: Long): Product {
        this.categoryId = categoryId
        return this
    }

    fun updatePrice(price: Int): Product {
        this.price = price
        return this
    }

}

fun Product.toEntity(brandEntity: BrandEntity, categoryEntity: CategoryEntity) = ProductEntity(
    id = this.id ?: 0,
    brand = brandEntity,
    category = categoryEntity,
    price = this.price
)

fun ProductEntity.toDomain() = Product(
    id = this.id,
    brandId = this.brand.id,
    categoryId = this.category.id,
    price = this.price
)
