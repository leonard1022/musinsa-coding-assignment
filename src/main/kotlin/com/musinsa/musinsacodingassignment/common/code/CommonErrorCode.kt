package com.musinsa.musinsacodingassignment.common.code

import org.springframework.http.HttpStatus

enum class CommonErrorCode(
    override val code: String,
    override val httpStatus: HttpStatus,
    override val message: String
) :
    BaseErrorCode {
    METHOD_NOT_ALLOWED("METHOD_NOT_ALLOWED", HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allow"),
    NO_RESOURCE_FOUND("NO_RESOURCE_FOUND", HttpStatus.NOT_FOUND, "No Resource Found"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "Server Error");
}
