package com.musinsa.musinsacodingassignment.brand.presentation.dto.request

import com.musinsa.musinsacodingassignment.brand.service.vo.CreateBrandVO

data class CreateBrandRequest(
    val name: String
)

fun CreateBrandRequest.toVO() = CreateBrandVO(
    name = this.name,
)
