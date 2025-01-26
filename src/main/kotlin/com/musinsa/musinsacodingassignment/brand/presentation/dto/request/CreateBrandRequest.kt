package com.musinsa.musinsacodingassignment.brand.presentation.dto.request

import com.musinsa.musinsacodingassignment.brand.service.vo.CreateBrandVO
import jakarta.validation.constraints.NotBlank

data class CreateBrandRequest(
    @NotBlank val name: String
)

fun CreateBrandRequest.toVO() = CreateBrandVO(
    name = this.name,
)
