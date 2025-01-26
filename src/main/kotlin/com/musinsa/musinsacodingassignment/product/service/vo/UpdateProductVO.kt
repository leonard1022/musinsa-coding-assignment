package com.musinsa.musinsacodingassignment.product.service.vo

import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity
import com.musinsa.musinsacodingassignment.category.entity.CategoryEntity
import com.musinsa.musinsacodingassignment.product.entity.ProductEntity

data class UpdateProductVO(
    val id: Long,
    val price: Int,
    val brandId: Long,
    val categoryId: Long
)

fun UpdateProductVO.toEntity(brandEntity: BrandEntity, categoryEntity: CategoryEntity) = ProductEntity(
    id = this.id,
    price = this.price,
    brandEntity = brandEntity,
    categoryEntity = categoryEntity
)
