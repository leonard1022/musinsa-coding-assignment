package com.musinsa.musinsacodingassignment.product.entity

import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity
import com.musinsa.musinsacodingassignment.brand.entity.CategoryEntity
import com.musinsa.musinsacodingassignment.common.domain.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "products")
class ProductEntity(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    val brand: BrandEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    val category: CategoryEntity,

    @Column(nullable = false)
    val price: Int
) : BaseEntity() {
    constructor(id: Long,  brand: BrandEntity, category: CategoryEntity, price: Int) : this(
        brand,
        category,
        price
    ) {
        this.id = id
    }
}
