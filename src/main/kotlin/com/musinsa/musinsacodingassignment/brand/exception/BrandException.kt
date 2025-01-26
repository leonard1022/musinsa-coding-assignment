package com.musinsa.musinsacodingassignment.brand.exception

import com.musinsa.musinsacodingassignment.common.exception.BaseException
import org.springframework.http.HttpStatus

class BrandException(code: String, message: String, httpStatus: HttpStatus) : BaseException(
    code, message, httpStatus
) {
    constructor(errorCode: BrandErrorCode) : this(errorCode.code, errorCode.message, errorCode.httpStatus)
}
