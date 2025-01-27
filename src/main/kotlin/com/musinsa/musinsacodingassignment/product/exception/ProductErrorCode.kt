package com.musinsa.musinsacodingassignment.product.exception

import com.musinsa.musinsacodingassignment.common.exception.BaseErrorCode
import org.springframework.http.HttpStatus

enum class ProductErrorCode(
    override val code: String, override val message: String, override val httpStatus: HttpStatus
) : BaseErrorCode {
    PRODUCT_NOT_FOUND("PRODUCT_001", "Product not found", HttpStatus.NOT_FOUND),
    BRAND_NOT_FOUND("PRODUCT_002", "Brand not found", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND("PRODUCT_003", "Category not found", HttpStatus.NOT_FOUND),
    NO_PRODUCT_FOR_CATEGORY("PRODUCT_04", "No Product for category", HttpStatus.INTERNAL_SERVER_ERROR),
    ;
}
