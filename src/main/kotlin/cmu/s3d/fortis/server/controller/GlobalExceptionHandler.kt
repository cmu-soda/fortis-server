package cmu.s3d.fortis.server.controller

import cmu.s3d.fortis.server.service.LoggingService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler(
    private val loggingService: LoggingService
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<String> {
        logger.error(e.stackTraceToString())
        loggingService.forwardFortisLog(e.stackTraceToString())
        return ResponseEntity.internalServerError().body(e.message)
    }
}