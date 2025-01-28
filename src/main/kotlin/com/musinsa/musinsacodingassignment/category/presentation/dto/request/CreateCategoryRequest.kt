package com.musinsa.musinsacodingassignment.category.presentation.dto.request

import com.musinsa.musinsacodingassignment.category.service.vo.CreateCategoryVO
import jakarta.validation.constraints.NotBlank

data class CreateCategoryRequest(
    @NotBlank val name: String
)


fun CreateCategoryRequest.toVO() = CreateCategoryVO(
    name = this.name
)

