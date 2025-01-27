package com.musinsa.musinsacodingassignment.common.exception

import com.musinsa.musinsacodingassignment.common.code.BaseErrorCode

class IllegalStateException(
    errorCode: BaseErrorCode,
) : RuntimeException(errorCode.message)
