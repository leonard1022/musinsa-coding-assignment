package com.musinsa.musinsacodingassignment.product.service.vo

import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity
import com.musinsa.musinsacodingassignment.category.entity.CategoryEntity
import com.musinsa.musinsacodingassignment.product.entity.ProductEntity


data class CreateProductVO(
    val price: Int,
    val brandId: Long,
    val categoryId: Long
)

fun CreateProductVO.toEntity(brandEntity: BrandEntity, categoryEntity: CategoryEntity) = ProductEntity(
    brand = brandEntity,
    category = categoryEntity,
    price = this.price
)
