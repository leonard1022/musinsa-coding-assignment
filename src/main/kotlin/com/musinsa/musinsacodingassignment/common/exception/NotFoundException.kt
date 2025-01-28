package com.musinsa.musinsacodingassignment.common.exception

class NotFoundException(
    errorCode: BaseErrorCode,
) : RuntimeException(errorCode.message)
