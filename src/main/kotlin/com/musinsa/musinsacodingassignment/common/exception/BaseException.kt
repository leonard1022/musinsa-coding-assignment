package com.musinsa.musinsacodingassignment.common.exception

import org.springframework.http.HttpStatus

abstract class BaseException(val code: String, override val message: String, val httpStatus: HttpStatus) :
    RuntimeException()

