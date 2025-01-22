package com.musinsa.musinsacodingassignment.common.exception

import com.musinsa.musinsacodingassignment.common.code.CommonErrorCode
import com.musinsa.musinsacodingassignment.common.presentation.dto.ExceptionResponseDto
import com.musinsa.musinsacodingassignment.common.presentation.dto.ServerExceptionResponseDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException


@RestControllerAdvice
class BaseExceptionHandler {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    protected fun handleMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ResponseEntity<ExceptionResponseDto> {
        val errorCode = CommonErrorCode.METHOD_NOT_ALLOWED
        logger.error(e.message, e)

        return ResponseEntity.status(errorCode.httpStatus).body(ExceptionResponseDto(errorCode.message, errorCode.code))
    }

    @ExceptionHandler(NoResourceFoundException::class)
    protected fun handleNoResourceFoundException(e: NoResourceFoundException): ResponseEntity<ExceptionResponseDto> {
        val errorCode = CommonErrorCode.NO_RESOURCE_FOUND
        logger.error(e.message, e)

        return ResponseEntity.status(errorCode.httpStatus).body(ExceptionResponseDto(errorCode.message, errorCode.code))
    }

    @ExceptionHandler(BaseException::class)
    protected fun handleBaseException(e: BaseException): ResponseEntity<ExceptionResponseDto> {
        logger.error(e.message, e)
        return ResponseEntity.status(e.httpStatus).body(ExceptionResponseDto(e.message, e.code))
    }

    @ExceptionHandler(Exception::class)
    protected fun handleException(e: Exception): ResponseEntity<ServerExceptionResponseDto> {
        val errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR
        logger.error(e.message, e)

        return ResponseEntity.status(errorCode.httpStatus)
            .body(ServerExceptionResponseDto(errorCode.message, errorCode.code))
    }
}
