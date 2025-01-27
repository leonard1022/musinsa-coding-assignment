package com.musinsa.musinsacodingassignment.brand.exception

import com.musinsa.musinsacodingassignment.common.code.BaseErrorCode
import org.springframework.http.HttpStatus

enum class BrandErrorCode(
    override val code: String, override val message: String, override val httpStatus: HttpStatus
) : BaseErrorCode {
    BRAND_NOT_FOUND("BRAND_001", "Brand not found", HttpStatus.NOT_FOUND),
}
