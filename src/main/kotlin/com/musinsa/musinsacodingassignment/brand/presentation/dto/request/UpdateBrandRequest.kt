package com.musinsa.musinsacodingassignment.brand.presentation.dto.request

import com.musinsa.musinsacodingassignment.brand.service.vo.UpdateBrandVO
import jakarta.validation.constraints.NotBlank

data class UpdateBrandRequest(
    @NotBlank val name: String
)

fun UpdateBrandRequest.toVO(id: Long) = UpdateBrandVO(
    id = id,
    name = this.name,
)
