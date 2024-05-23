package backend.itracker.logging

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalRestControllerAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllealArgumentException(e: IllegalArgumentException): ResponseEntity<String> {
        logger.info("사용자 입력 예외입니다. message : ", e)

        return ResponseEntity.badRequest().body("message = ${e.message}")
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<String> {
        logger.error("예상치 못한 예외입니다. message : ", e)

        return ResponseEntity.internalServerError().body("message = ${e.message}")
    }

}
