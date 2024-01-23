package cmu.s3d.fortis.server.controller

import cmu.s3d.fortis.server.service.LoggingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler(
    private val loggingService: LoggingService
) {
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<String> {
        loggingService.forwardFortisLog(e.stackTraceToString())
        return ResponseEntity.internalServerError().body(e.message)
    }
}