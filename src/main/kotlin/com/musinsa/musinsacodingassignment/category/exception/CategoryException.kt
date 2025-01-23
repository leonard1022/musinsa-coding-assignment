package com.musinsa.musinsacodingassignment.category.exception

import com.musinsa.musinsacodingassignment.category.code.CategoryErrorCode
import com.musinsa.musinsacodingassignment.common.exception.BaseException
import org.springframework.http.HttpStatus as HttpStatus1

class CategoryException(code: String, message: String, httpStatus: HttpStatus1) : BaseException(
    code, message, httpStatus
) {
    constructor(errorCode: CategoryErrorCode) : this(errorCode.code, errorCode.message, errorCode.httpStatus)
}
