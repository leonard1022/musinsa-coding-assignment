package com.musinsa.musinsacodingassignment.common.exception

import jakarta.validation.UnexpectedTypeException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ProblemDetail {
        val problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND)
        problemDetail.title = "리소스 없음"
        problemDetail.detail = ex.message

        return problemDetail
    }

    @ExceptionHandler(UnexpectedTypeException::class)
    fun handleUnexpectedTypeException(ex: UnexpectedTypeException): ProblemDetail {
        val problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)
        problemDetail.title = "잘못된 요청"
        problemDetail.detail = ex.message

        return problemDetail
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleConstraintViolationException(ex: DataIntegrityViolationException): ProblemDetail {
        val problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT)
        problemDetail.title = "데이터 무결성 위반"
        problemDetail.detail = ex.message

        return problemDetail
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ProblemDetail {
        val problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)
        problemDetail.title = "잘못된 요청"
        problemDetail.detail = ex.message

        return problemDetail
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ProblemDetail {
        val problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        problemDetail.title = "서버 에러"
        problemDetail.detail = ex.message

        return problemDetail
    }
}
