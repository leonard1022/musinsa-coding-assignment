package com.musinsa.musinsacodingassignment.category.domain

data class Category(
    var id: Long?,
    var name: String
) {
    fun updateName(name: String): Category {
        this.name = name
        return this
    }
}
