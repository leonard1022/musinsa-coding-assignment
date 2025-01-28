package com.musinsa.musinsacodingassignment.common.exception

class IllegalStateException(
    errorCode: BaseErrorCode,
) : RuntimeException(errorCode.message)
