package com.musinsa.musinsacodingassignment.brand.presentation.dto.request

import com.musinsa.musinsacodingassignment.brand.service.vo.UpdateBrandVO

data class UpdateBrandRequest(
    val name: String
)

fun UpdateBrandRequest.toVO(id: Long) = UpdateBrandVO(
    id = id,
    name = this.name,
)
