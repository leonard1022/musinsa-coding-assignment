package com.musinsa.musinsacodingassignment.brand.code

import com.musinsa.musinsacodingassignment.common.code.BaseErrorCode
import org.springframework.http.HttpStatus

enum class BrandErrorCode(
    override val code: String, override val message: String, override val httpStatus: HttpStatus
) : BaseErrorCode {
    BRAND_NOT_FOUND("BRAND_001", "Brand not found", HttpStatus.NOT_FOUND),
    BRAND_NAME_IS_REQUIRED("BRAND_002", "Brand name is required", HttpStatus.BAD_REQUEST),
    BRAND_ALREADY_EXISTS("BRAND_003", "Brand already exists", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("BRAND_004", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    BRAND_DUPLICATED("BRAND_005", "Brand Duplicated", HttpStatus.BAD_REQUEST)
    ;
}
