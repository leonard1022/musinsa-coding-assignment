package com.musinsa.musinsacodingassignment.product.exception

import com.musinsa.musinsacodingassignment.category.code.CategoryErrorCode
import com.musinsa.musinsacodingassignment.common.exception.BaseException
import com.musinsa.musinsacodingassignment.product.code.ProductErrorCode
import org.springframework.http.HttpStatus

class ProductException(code: String, message: String, httpStatus: HttpStatus) : BaseException(
    code, message, httpStatus
) {
    constructor(errorCode: ProductErrorCode) : this(errorCode.code, errorCode.message, errorCode.httpStatus)
}
