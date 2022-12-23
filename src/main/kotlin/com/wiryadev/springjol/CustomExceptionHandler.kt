package com.wiryadev.springjol

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(CustomException::class)
    fun handleThrowable(throwable: CustomException): ResponseEntity<BaseResponse<Nothing>> {
        return ResponseEntity(
            BaseResponse.failure(throwable.message ?: "Unknown Failure"),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}