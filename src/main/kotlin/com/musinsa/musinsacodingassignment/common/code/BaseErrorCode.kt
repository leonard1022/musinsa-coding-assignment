package com.musinsa.musinsacodingassignment.common.code

import org.springframework.http.HttpStatus

interface BaseErrorCode {
    val code: String
    val message: String
    val httpStatus: HttpStatus
}
