package backend.itracker.logging

import org.springframework.beans.BeanInstantiationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalRestControllerAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(BeanInstantiationException::class)
    fun handleBeanInstantiationException(e: BeanInstantiationException): ResponseEntity<String> {
        return when (val ex = e.cause) {
            is IllegalArgumentException -> {
                logger.info("사용자 입력 예외입니다. message : ", ex)
                ResponseEntity.badRequest().body("message = ${ex.message}")
            }

            else -> {
                logger.error("예상치 못한 예외입니다. message : ", ex)
                ResponseEntity.internalServerError().body("message = ${ex?.message}")
            }
        }
    }

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
