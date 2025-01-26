package com.musinsa.musinsacodingassignment.product.domain

class Product(
    var id: Long,
    var brandId: Long,
    var brandName: String,
    var categoryId: Long,
    var categoryName: String,
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
