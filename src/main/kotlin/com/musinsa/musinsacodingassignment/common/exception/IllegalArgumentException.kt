package com.musinsa.musinsacodingassignment.common.exception

class IllegalArgumentException(
    errorCode: BaseErrorCode,
) : RuntimeException(errorCode.message)

