package com.musinsa.musinsacodingassignment.brand.service.vo

import com.musinsa.musinsacodingassignment.brand.entity.BrandEntity
import jakarta.validation.constraints.NotBlank

data class CreateBrandVO(
    @NotBlank val name: String
)

fun CreateBrandVO.toEntity() = BrandEntity(
    name = this.name
)
