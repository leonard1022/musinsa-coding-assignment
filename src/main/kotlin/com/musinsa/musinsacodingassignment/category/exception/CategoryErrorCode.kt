package com.musinsa.musinsacodingassignment.category.exception

import com.musinsa.musinsacodingassignment.common.code.BaseErrorCode
import org.springframework.http.HttpStatus

enum class CategoryErrorCode(
    override val code: String, override val message: String, override val httpStatus: HttpStatus
) : BaseErrorCode {
    CATEGORY_NOT_FOUND("CATEGORY_001", "Category not found", HttpStatus.NOT_FOUND),
    CATEGORY_ALREADY_EXISTS("CATEGORY_003", "Category already exists", HttpStatus.BAD_REQUEST),
}
