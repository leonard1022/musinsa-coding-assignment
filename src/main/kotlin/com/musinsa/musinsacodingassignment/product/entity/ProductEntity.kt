package com.musinsa.musinsacodingassignment.product.entity

import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity
import com.musinsa.musinsacodingassignment.category.entity.CategoryEntity
import com.musinsa.musinsacodingassignment.common.entity.BaseEntity
import com.musinsa.musinsacodingassignment.product.domain.Product
import com.musinsa.musinsacodingassignment.product.service.vo.ProductVO
import jakarta.persistence.*

@Entity
@Table(name = "products")
class ProductEntity(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    var brand: BrandEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    var category: CategoryEntity,

    @Column(nullable = false)
    val price: Int
) : BaseEntity() {
    constructor(id: Long, brandEntity: BrandEntity, categoryEntity: CategoryEntity, price: Int) : this(
        brandEntity,
        categoryEntity,
        price
    ) {
        this.id = id
    }
}

fun ProductEntity.toDomain() = Product(
    id = this.id,
    brandId = this.brand.id,
    brandName = this.brand.name,
    categoryId = this.category.id,
    categoryName = this.category.name,
    price = this.price
)

fun ProductEntity.toVO() = ProductVO(
    id = this.id,
    brandId = this.brand.id,
    brandName = this.brand.name,
    categoryId = this.category.id,
    categoryName = this.category.name,
    price = this.price
)
