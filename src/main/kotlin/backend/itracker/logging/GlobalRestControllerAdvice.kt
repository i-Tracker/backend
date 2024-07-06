package backend.itracker.logging

import backend.itracker.exception.ClientException
import backend.itracker.logging.response.ErrorResponse
import backend.itracker.tracker.infra.oauth.exception.OauthRequestException
import backend.itracker.tracker.oauth.exception.DuplicatedMemberException
import backend.itracker.tracker.oauth.exception.FirstLoginException
import org.springframework.beans.BeanInstantiationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalRestControllerAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(FirstLoginException::class, DuplicatedMemberException::class)
    fun handleFirstLoginException(e: ClientException): ResponseEntity<ErrorResponse> {
        logger.info("message : ", e)

        return ResponseEntity.status(e.errorCode.httpStatus)
            .body(ErrorResponse.from(e.errorCode))
    }

    @ExceptionHandler(BeanInstantiationException::class)
    fun handleBeanInstantiationException(e: BeanInstantiationException): ResponseEntity<String> {
        return when (val ex = e.cause) {
            is IllegalArgumentException -> {
                logger.info("사용자 입력 예외입니다. message : ", ex)
                ResponseEntity.badRequest().body("사용자 입력 예외입니다.")
            }

            else -> {
                logger.error("예상치 못한 예외입니다. message : ", ex)
                ResponseEntity.internalServerError().body("일시적인 네트워크 오류입니다. 오류가 지속될 경우 관리자에게 문의해주세요.")
            }
        }
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllealArgumentException(e: IllegalArgumentException): ResponseEntity<String> {
        logger.info("사용자 입력 예외입니다. message : ", e)

        return ResponseEntity.badRequest().body("사용자 입력 예외입니다.")
    }

    @ExceptionHandler(OauthRequestException::class)
    fun handleIllealArgumentException(e: OauthRequestException): ResponseEntity<String> {
        logger.info("잘못된 로그인 방식입니다. message : ", e)

        return ResponseEntity.badRequest().body("잘못된 로그인 방식입니다.")
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<String> {
        logger.error("예상치 못한 예외입니다. message : ", e)

        return ResponseEntity.internalServerError().body("일시적인 네트워크 오류입니다. 오류가 지속될 경우 관리자에게 문의해주세요.")
    }

}
