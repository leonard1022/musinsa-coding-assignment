package com.musinsa.musinsacodingassignment.product.code

import com.musinsa.musinsacodingassignment.common.code.BaseErrorCode
import org.springframework.http.HttpStatus

enum class ProductErrorCode(
    override val code: String, override val message: String, override val httpStatus: HttpStatus
) : BaseErrorCode {
    PRODUCT_NOT_FOUND("PRODUCT_001", "Product not found", HttpStatus.NOT_FOUND),
    BRAND_NOT_FOUND("PRODUCT_002", "Brand not found", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND("PRODUCT_003", "Category not found", HttpStatus.NOT_FOUND),
    PRODUCT_NAME_IS_REQUIRED("PRODUCT_004", "Product name is required", HttpStatus.BAD_REQUEST),
    PRODUCT_ALREADY_EXISTS("PRODUCT_005", "Product already exists", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("PRODUCT_006", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    ;
}
