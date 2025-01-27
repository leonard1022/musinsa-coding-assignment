package com.musinsa.musinsacodingassignment.common.exception

import com.musinsa.musinsacodingassignment.common.code.BaseErrorCode

class NotFoundException(
    errorCode: BaseErrorCode,
) : RuntimeException(errorCode.message)
