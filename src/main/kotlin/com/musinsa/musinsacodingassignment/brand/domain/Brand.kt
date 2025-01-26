package com.musinsa.musinsacodingassignment.brand.domain

data class Brand(
    var id: Long?,
    var name: String
) {
    fun updateName(name: String): Brand {
        this.name = name
        return this
    }
}
