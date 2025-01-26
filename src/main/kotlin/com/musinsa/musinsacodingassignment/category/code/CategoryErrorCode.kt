package com.musinsa.musinsacodingassignment.category.code

import com.musinsa.musinsacodingassignment.common.code.BaseErrorCode
import org.springframework.http.HttpStatus

enum class CategoryErrorCode(
    override val code: String, override val message: String, override val httpStatus: HttpStatus
) : BaseErrorCode {
    CATEGORY_NOT_FOUND("CATEGORY_001", "Category not found", HttpStatus.NOT_FOUND),
    CATEGORY_NAME_IS_REQUIRED("CATEGORY_002", "Category name is required", HttpStatus.BAD_REQUEST),
    CATEGORY_ALREADY_EXISTS("CATEGORY_003", "Category already exists", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("CATEGORY_004", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_DUPLICATED("CATEGORY_005", "Category Duplicated", HttpStatus.BAD_REQUEST)
    ;
}
