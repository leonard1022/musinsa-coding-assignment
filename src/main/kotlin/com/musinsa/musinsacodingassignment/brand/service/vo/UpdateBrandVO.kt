package com.musinsa.musinsacodingassignment.brand.service.vo

import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity

data class UpdateBrandVO(
    val id: Long,
    val name: String
)

fun UpdateBrandVO.toEntity() = BrandEntity(
    id = this.id,
    name = this.name
)
