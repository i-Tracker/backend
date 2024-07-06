package backend.itracker.exception.code

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val httpStatus: HttpStatus,
    val code: String,
    val message: String,
) {

    DUPLICATED_MEMBER_EXCEPTION(HttpStatus.BAD_REQUEST, "ERR_4000", "이미 가입된 회원입니다."),

    FIRST_LOGIN_EXCEPTION(HttpStatus.UNAUTHORIZED, "ERR_4010", "최초 회원가입 대상입니다."),
}
