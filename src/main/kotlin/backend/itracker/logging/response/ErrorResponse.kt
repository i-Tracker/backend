package backend.itracker.logging.response

import backend.itracker.exception.code.ErrorCode

data class ErrorResponse(
    val code: String,
    val message: String,
) {

    companion object {
        fun from(errorCode: ErrorCode): ErrorResponse {
            return ErrorResponse(
                code = errorCode.code,
                message = errorCode.message
            )
        }
    }
}
